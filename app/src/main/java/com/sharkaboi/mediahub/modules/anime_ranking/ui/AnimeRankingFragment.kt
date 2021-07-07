package com.sharkaboi.mediahub.modules.anime_ranking.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.paging.LoadState
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.GridLayoutManager
import com.google.android.material.chip.Chip
import com.google.android.material.shape.ShapeAppearanceModel
import com.sharkaboi.mediahub.common.extensions.showToast
import com.sharkaboi.mediahub.data.api.enums.AnimeRankingType
import com.sharkaboi.mediahub.databinding.FragmentAnimeRankingBinding
import com.sharkaboi.mediahub.modules.anime_ranking.adapters.AnimeRankingDetailedAdapter
import com.sharkaboi.mediahub.modules.anime_ranking.adapters.AnimeRankingLoadStateAdapter
import com.sharkaboi.mediahub.modules.anime_ranking.vm.AnimeRankingViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@AndroidEntryPoint
class AnimeRankingFragment : Fragment() {
    private var _binding: FragmentAnimeRankingBinding? = null
    private val binding get() = _binding!!
    private val navController by lazy { findNavController() }
    private lateinit var animeRankingDetailedAdapter: AnimeRankingDetailedAdapter
    private val animeRankingViewModel by viewModels<AnimeRankingViewModel>()
    private val args: AnimeRankingFragmentArgs by navArgs()
    private var resultsJob: Job? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentAnimeRankingBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onDestroyView() {
        resultsJob?.cancel()
        resultsJob = null
        binding.rvAnimeRanking.adapter = null
        _binding = null
        super.onDestroyView()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.toolbar.setNavigationOnClickListener { navController.navigateUp() }
        initRanking()
        setupFilterChips()
        setUpRecyclerView()
        setObservers()
    }

    private fun initRanking() {
        animeRankingViewModel.setRankingType(
            if (args.animeRankingType == null) {
                AnimeRankingType.all
            } else {
                runCatching {
                    AnimeRankingType.valueOf(
                        args.animeRankingType?.lowercase()
                            ?: AnimeRankingType.all.name
                    )
                }.getOrElse { AnimeRankingType.all }
            }
        )
    }

    override fun onResume() {
        super.onResume()
        collectPagedList()
    }

    private fun setupFilterChips() {
        binding.rankTypeChipGroup.removeAllViews()
        AnimeRankingType.values().forEach { rankingType ->
            binding.rankTypeChipGroup.addView(
                Chip(context).apply {
                    text = rankingType.getAnimeRanking()
                    setEnsureMinTouchTargetSize(false)
                    isCheckable = true
                    isChecked = rankingType == animeRankingViewModel.selectedRankingType
                    shapeAppearanceModel = ShapeAppearanceModel().withCornerSize(8f)
                    setOnClickListener {
                        animeRankingViewModel.setRankingType(rankingType)
                        collectPagedList()
                    }
                }
            )
        }
    }

    private fun setUpRecyclerView() {
        binding.rvAnimeRanking.apply {
            animeRankingDetailedAdapter = AnimeRankingDetailedAdapter { animeId ->
                val action = AnimeRankingFragmentDirections.openAnimeDetailsWithId(animeId)
                navController.navigate(action)
            }
            layoutManager = GridLayoutManager(context, 3)
            itemAnimator = DefaultItemAnimator()
            adapter = animeRankingDetailedAdapter.withLoadStateFooter(
                footer = AnimeRankingLoadStateAdapter()
            )
        }
    }

    private fun setObservers() {
        lifecycleScope.launch {
            animeRankingDetailedAdapter.addLoadStateListener { loadStates ->
                if (loadStates.source.refresh is LoadState.Error) {
                    showToast((loadStates.source.refresh as LoadState.Error).error.message)
                }
                binding.progressBar.isShowing = loadStates.refresh is LoadState.Loading
                binding.tvEmptyHint.isVisible =
                    loadStates.refresh is LoadState.NotLoading && animeRankingDetailedAdapter.itemCount == 0
            }
        }
    }

    private fun collectPagedList() {
        resultsJob?.cancel()
        resultsJob = lifecycleScope.launch {
            animeRankingViewModel.getAnimeForRankingType()
                .collectLatest { pagingData ->
                    animeRankingDetailedAdapter.submitData(pagingData)
                    binding.rvAnimeRanking.smoothScrollToPosition(0)
                }
        }
    }
}
