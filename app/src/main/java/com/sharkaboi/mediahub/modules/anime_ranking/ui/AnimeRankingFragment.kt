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
import androidx.paging.CombinedLoadStates
import androidx.paging.LoadState
import androidx.recyclerview.widget.DefaultItemAnimator
import com.google.android.material.chip.Chip
import com.sharkaboi.mediahub.common.constants.UIConstants
import com.sharkaboi.mediahub.common.constants.UIConstants.setMediaHubChipStyle
import com.sharkaboi.mediahub.common.extensions.observe
import com.sharkaboi.mediahub.common.extensions.showToast
import com.sharkaboi.mediahub.data.api.enums.AnimeRankingType
import com.sharkaboi.mediahub.databinding.FragmentAnimeRankingBinding
import com.sharkaboi.mediahub.modules.anime_ranking.adapters.AnimeRankingDetailedAdapter
import com.sharkaboi.mediahub.modules.anime_ranking.adapters.AnimeRankingLoadStateAdapter
import com.sharkaboi.mediahub.modules.anime_ranking.vm.AnimeRankingViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class AnimeRankingFragment : Fragment() {
    private var _binding: FragmentAnimeRankingBinding? = null
    private val binding get() = _binding!!
    private val navController by lazy { findNavController() }
    private lateinit var animeRankingDetailedAdapter: AnimeRankingDetailedAdapter
    private val animeRankingViewModel by viewModels<AnimeRankingViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentAnimeRankingBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onDestroyView() {
        animeRankingDetailedAdapter.removeLoadStateListener(loadStateListener)
        binding.rvAnimeRanking.adapter = null
        _binding = null
        super.onDestroyView()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.toolbar.setNavigationOnClickListener { navController.navigateUp() }
        setupFilterChips()
        setUpRecyclerView()
        setObservers()
    }

    private fun setupFilterChips() {
        binding.rankTypeChipGroup.removeAllViews()
        AnimeRankingType.values().forEach { rankingType ->
            val rankChip = Chip(context)
            rankChip.text = rankingType.getFormattedString(rankChip.context)
            rankChip.setMediaHubChipStyle()
            rankChip.isCheckable = true
            rankChip.isChecked = rankingType == animeRankingViewModel.rankingType
            rankChip.setOnClickListener {
                animeRankingViewModel.setRankingType(rankingType)
            }
            binding.rankTypeChipGroup.addView(rankChip)
        }
    }

    private fun setUpRecyclerView() {
        binding.rvAnimeRanking.apply {
            animeRankingDetailedAdapter = AnimeRankingDetailedAdapter { animeId ->
                val action = AnimeRankingFragmentDirections.openAnimeById(animeId)
                navController.navigate(action)
            }
            layoutManager = UIConstants.getGridLayoutManager(context)
            itemAnimator = DefaultItemAnimator()
            adapter = animeRankingDetailedAdapter.withLoadStateFooter(
                footer = AnimeRankingLoadStateAdapter()
            )
        }
    }

    private val loadStateListener = { loadStates: CombinedLoadStates ->
        if (loadStates.source.refresh is LoadState.Error) {
            showToast((loadStates.source.refresh as LoadState.Error).error.message)
        }
        binding.progressBar.isShowing = loadStates.refresh is LoadState.Loading
        binding.tvEmptyHint.isVisible =
            loadStates.refresh is LoadState.NotLoading && animeRankingDetailedAdapter.itemCount == 0
    }

    private fun setObservers() {
        animeRankingDetailedAdapter.addLoadStateListener(loadStateListener)
        observe(animeRankingViewModel.result) { pagingData ->
            lifecycleScope.launch { animeRankingDetailedAdapter.submitData(pagingData) }
            binding.rvAnimeRanking.smoothScrollToPosition(0)
        }
    }
}
