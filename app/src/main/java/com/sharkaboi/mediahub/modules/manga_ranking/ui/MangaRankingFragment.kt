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
import androidx.paging.CombinedLoadStates
import androidx.paging.LoadState
import androidx.recyclerview.widget.DefaultItemAnimator
import com.google.android.material.chip.Chip
import com.sharkaboi.mediahub.BottomNavGraphDirections
import com.sharkaboi.mediahub.common.constants.UIConstants
import com.sharkaboi.mediahub.common.constants.UIConstants.setMediaHubChipStyle
import com.sharkaboi.mediahub.common.extensions.observe
import com.sharkaboi.mediahub.common.extensions.showToast
import com.sharkaboi.mediahub.data.api.enums.MangaRankingType
import com.sharkaboi.mediahub.databinding.FragmentMangaRankingBinding
import com.sharkaboi.mediahub.modules.manga_ranking.adapters.MangaRankingDetailedAdapter
import com.sharkaboi.mediahub.modules.manga_ranking.adapters.MangaRankingLoadStateAdapter
import com.sharkaboi.mediahub.modules.manga_ranking.vm.MangaRankingViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class MangaRankingFragment : Fragment() {
    private var _binding: FragmentMangaRankingBinding? = null
    private val binding get() = _binding!!
    private val navController by lazy { findNavController() }
    private lateinit var mangaRankingDetailedAdapter: MangaRankingDetailedAdapter
    private val mangaRankingViewModel by viewModels<MangaRankingViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentMangaRankingBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onDestroyView() {
        mangaRankingDetailedAdapter.removeLoadStateListener(loadStateListener)
        binding.rvMangaRanking.adapter = null
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
        MangaRankingType.values().forEach { rankingType ->
            val rankChip = Chip(context)
            rankChip.text = rankingType.getFormattedString(rankChip.context)
            rankChip.setMediaHubChipStyle()
            rankChip.isCheckable = true
            rankChip.isChecked = rankingType == mangaRankingViewModel.rankingType
            rankChip.setOnClickListener {
                mangaRankingViewModel.setRankingType(rankingType)
            }
            binding.rankTypeChipGroup.addView(rankChip)
        }
    }

    private fun setUpRecyclerView() {
        binding.rvMangaRanking.apply {
            mangaRankingDetailedAdapter = MangaRankingDetailedAdapter { mangaId ->
                val action = BottomNavGraphDirections.openMangaById(mangaId)
                navController.navigate(action)
            }
            layoutManager = UIConstants.getGridLayoutManager(context)
            itemAnimator = DefaultItemAnimator()
            adapter = mangaRankingDetailedAdapter.withLoadStateFooter(
                footer = MangaRankingLoadStateAdapter()
            )
        }
    }

    private val loadStateListener = { loadStates: CombinedLoadStates ->
        if (loadStates.source.refresh is LoadState.Error) {
            showToast((loadStates.source.refresh as LoadState.Error).error.message)
        }
        binding.progressBar.isShowing = loadStates.refresh is LoadState.Loading
        binding.tvEmptyHint.isVisible =
            loadStates.refresh is LoadState.NotLoading && mangaRankingDetailedAdapter.itemCount == 0
    }

    private fun setObservers() {
        mangaRankingDetailedAdapter.addLoadStateListener(loadStateListener)
        observe(mangaRankingViewModel.result) { pagingData ->
            lifecycleScope.launch { mangaRankingDetailedAdapter.submitData(pagingData) }
            binding.rvMangaRanking.smoothScrollToPosition(0)
        }
    }
}
