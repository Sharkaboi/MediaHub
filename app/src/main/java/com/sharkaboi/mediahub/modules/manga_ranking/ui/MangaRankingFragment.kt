package com.sharkaboi.mediahub.modules.manga_ranking.ui

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
import com.sharkaboi.mediahub.data.api.enums.MangaRankingType
import com.sharkaboi.mediahub.databinding.FragmentMangaRankingBinding
import com.sharkaboi.mediahub.modules.manga_ranking.adapters.MangaRankingDetailedAdapter
import com.sharkaboi.mediahub.modules.manga_ranking.adapters.MangaRankingLoadStateAdapter
import com.sharkaboi.mediahub.modules.manga_ranking.vm.MangaRankingViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@AndroidEntryPoint
class MangaRankingFragment : Fragment() {
    private var _binding: FragmentMangaRankingBinding? = null
    private val binding get() = _binding!!
    private val navController by lazy { findNavController() }
    private lateinit var mangaRankingDetailedAdapter: MangaRankingDetailedAdapter
    private val mangaRankingViewModel by viewModels<MangaRankingViewModel>()
    private val args: MangaRankingFragmentArgs by navArgs()
    private var resultsJob: Job? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentMangaRankingBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onDestroyView() {
        resultsJob?.cancel()
        resultsJob = null
        binding.rvMangaRanking.adapter = null
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

    override fun onResume() {
        super.onResume()
        getMangaRankingList()
    }

    private fun initRanking() {
        mangaRankingViewModel.setRankingType(
            if (args.mangaRankingType == null) {
                MangaRankingType.all
            } else {
                runCatching {
                    MangaRankingType.valueOf(
                        args.mangaRankingType?.lowercase()
                            ?: MangaRankingType.all.name
                    )
                }.getOrElse { MangaRankingType.all }
            }
        )
    }

    private fun setupFilterChips() {
        binding.rankTypeChipGroup.removeAllViews()
        MangaRankingType.values().forEach { rankingType ->
            binding.rankTypeChipGroup.addView(
                Chip(context).apply {
                    text = rankingType.getFormattedString()
                    setEnsureMinTouchTargetSize(false)
                    isCheckable = true
                    isChecked = rankingType == mangaRankingViewModel.selectedRankingType
                    shapeAppearanceModel = ShapeAppearanceModel().withCornerSize(8f)
                    setOnClickListener {
                        mangaRankingViewModel.setRankingType(rankingType)
                        getMangaRankingList()
                    }
                }
            )
        }
    }

    private fun setUpRecyclerView() {
        binding.rvMangaRanking.apply {
            mangaRankingDetailedAdapter = MangaRankingDetailedAdapter { mangaId ->
                val action = MangaRankingFragmentDirections.openMangaDetailsWithId(mangaId)
                navController.navigate(action)
            }
            layoutManager = GridLayoutManager(context, 3)
            itemAnimator = DefaultItemAnimator()
            adapter = mangaRankingDetailedAdapter.withLoadStateFooter(
                footer = MangaRankingLoadStateAdapter()
            )
        }
    }

    private fun setObservers() {
        lifecycleScope.launch {
            mangaRankingDetailedAdapter.addLoadStateListener { loadStates ->
                if (loadStates.source.refresh is LoadState.Error) {
                    showToast((loadStates.source.refresh as LoadState.Error).error.message)
                }
                binding.progressBar.isShowing = loadStates.refresh is LoadState.Loading
                binding.tvEmptyHint.isVisible =
                    loadStates.refresh is LoadState.NotLoading && mangaRankingDetailedAdapter.itemCount == 0
            }
        }
    }

    private fun getMangaRankingList() {
        resultsJob?.cancel()
        resultsJob = lifecycleScope.launch {
            mangaRankingViewModel.getMangaRankingOfFilter()
                .collectLatest { pagingData ->
                    mangaRankingDetailedAdapter.submitData(pagingData)
                    binding.rvMangaRanking.smoothScrollToPosition(0)
                }
        }
    }
}
