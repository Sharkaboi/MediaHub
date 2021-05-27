package com.sharkaboi.mediahub.modules.anime_seasonal.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.paging.LoadState
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.GridLayoutManager
import com.sharkaboi.mediahub.common.data.api.enums.AnimeSeason
import com.sharkaboi.mediahub.common.data.api.enums.getAnimeSeason
import com.sharkaboi.mediahub.common.data.api.enums.next
import com.sharkaboi.mediahub.common.data.api.enums.previous
import com.sharkaboi.mediahub.common.extensions.capitalizeFirst
import com.sharkaboi.mediahub.common.extensions.showToast
import com.sharkaboi.mediahub.databinding.FragmentAnimeSeasonalBinding
import com.sharkaboi.mediahub.modules.anime_seasonal.adapters.AnimeSeasonalAdapter
import com.sharkaboi.mediahub.modules.anime_seasonal.adapters.AnimeSeasonalLoadStateAdapter
import com.sharkaboi.mediahub.modules.anime_seasonal.vm.AnimeSeasonalViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import java.time.LocalDate

@AndroidEntryPoint
class AnimeSeasonalFragment : Fragment() {
    private var _binding: FragmentAnimeSeasonalBinding? = null
    private val binding get() = _binding!!
    private val navController by lazy { findNavController() }
    private lateinit var animeSeasonalAdapter: AnimeSeasonalAdapter
    private val animeSeasonalViewModel by viewModels<AnimeSeasonalViewModel>()
    private var selectedSeason: AnimeSeason = LocalDate.now().getAnimeSeason()
    private var selectedYear: Int = LocalDate.now().year

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentAnimeSeasonalBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onDestroyView() {
        binding.rvAnimeSeasonal.adapter = null
        _binding = null
        super.onDestroyView()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.toolbar.setNavigationOnClickListener { navController.navigateUp() }
        setupSeasonButtons()
        setUpRecyclerView()
        setObservers()
    }

    override fun onResume() {
        super.onResume()
        collectPagedList(selectedSeason, selectedYear)
    }

    private fun setupSeasonButtons() {
        binding.apply {
            btnPrevSeason.setOnClickListener {
                selectedSeason = selectedSeason.previous()
                selectedYear = LocalDate.of(selectedYear, 1, 1).minusYears(1L).year
                tvSeason.text = ("${selectedSeason.name.capitalizeFirst()} $selectedYear")
                collectPagedList(selectedSeason, selectedYear)
            }
            btnNextSeason.setOnClickListener {
                selectedSeason = selectedSeason.next()
                selectedYear = LocalDate.of(selectedYear, 1, 1).plusYears(1L).year
                tvSeason.text = ("${selectedSeason.name.capitalizeFirst()} $selectedYear")
                collectPagedList(selectedSeason, selectedYear)
            }
            tvSeason.text = ("${selectedSeason.name.capitalizeFirst()} $selectedYear")
        }
    }

    private fun setUpRecyclerView() {
        binding.rvAnimeSeasonal.apply {
            animeSeasonalAdapter = AnimeSeasonalAdapter { animeId ->
                val action = AnimeSeasonalFragmentDirections.openAnimeDetailsWithId(animeId)
                navController.navigate(action)
            }
            layoutManager = GridLayoutManager(context, 3)
            itemAnimator = DefaultItemAnimator()
            adapter = animeSeasonalAdapter.withLoadStateFooter(
                footer = AnimeSeasonalLoadStateAdapter()
            )
        }
    }

    private fun setObservers() {
        viewLifecycleOwner.lifecycleScope.launch {
            animeSeasonalAdapter.addLoadStateListener { loadStates ->
                if (loadStates.source.refresh is LoadState.Error) {
                    showToast((loadStates.source.refresh as LoadState.Error).error.message)
                }
                binding.progressBar.isShowing = loadStates.refresh is LoadState.Loading
                binding.tvEmptyHint.isVisible =
                    loadStates.refresh is LoadState.NotLoading && animeSeasonalAdapter.itemCount == 0
            }
        }
    }

    private fun collectPagedList(animeSeason: AnimeSeason, year: Int) {
        binding.rvAnimeSeasonal.smoothScrollToPosition(0)
        viewLifecycleOwner.lifecycleScope.launch {
            animeSeasonalViewModel.setSeason(animeSeason, year)
                .collectLatest { pagingData ->
                    animeSeasonalAdapter.submitData(pagingData)
                }
        }
    }

}