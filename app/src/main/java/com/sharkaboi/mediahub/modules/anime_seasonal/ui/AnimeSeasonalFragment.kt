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
import androidx.navigation.fragment.navArgs
import androidx.paging.LoadState
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.GridLayoutManager
import com.sharkaboi.mediahub.common.extensions.capitalizeFirst
import com.sharkaboi.mediahub.common.extensions.showToast
import com.sharkaboi.mediahub.data.api.enums.getAnimeSeason
import com.sharkaboi.mediahub.databinding.FragmentAnimeSeasonalBinding
import com.sharkaboi.mediahub.modules.anime_seasonal.adapters.AnimeSeasonalAdapter
import com.sharkaboi.mediahub.modules.anime_seasonal.adapters.AnimeSeasonalLoadStateAdapter
import com.sharkaboi.mediahub.modules.anime_seasonal.util.AnimeSeasonWrapper
import com.sharkaboi.mediahub.modules.anime_seasonal.vm.AnimeSeasonalViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@AndroidEntryPoint
class AnimeSeasonalFragment : Fragment() {
    private var _binding: FragmentAnimeSeasonalBinding? = null
    private val binding get() = _binding!!
    private val navController by lazy { findNavController() }
    private lateinit var animeSeasonalAdapter: AnimeSeasonalAdapter
    private val animeSeasonalViewModel by viewModels<AnimeSeasonalViewModel>()
    private val args: AnimeSeasonalFragmentArgs by navArgs()
    private var resultsJob: Job? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentAnimeSeasonalBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onDestroyView() {
        resultsJob?.cancel()
        resultsJob = null
        binding.rvAnimeSeasonal.adapter = null
        _binding = null
        super.onDestroyView()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.toolbar.setNavigationOnClickListener { navController.navigateUp() }
        initSeason()
        setupSeasonButtons()
        setUpRecyclerView()
        setObservers()
    }

    private fun initSeason() {
        val season = args.season.getAnimeSeason()
        animeSeasonalViewModel.setAnimeSeason(
            if (season == null || args.year == 0) {
                AnimeSeasonWrapper.currentSeason()
            } else {
                AnimeSeasonWrapper(
                    animeSeason = season,
                    year = args.year
                )
            }
        )
    }

    override fun onResume() {
        super.onResume()
        collectPagedList()
    }

    private fun setupSeasonButtons() {
        binding.apply {
            btnPrevSeason.setOnClickListener {
                animeSeasonalViewModel.previousSeason()
                setSeasonText()
                collectPagedList()
            }
            btnNextSeason.setOnClickListener {
                animeSeasonalViewModel.nextSeason()
                setSeasonText()
                collectPagedList()
            }
            setSeasonText()
        }
    }

    private fun setSeasonText() {
        binding.tvSeason.text =
            animeSeasonalViewModel.animeSeasonWrapper.let {
                "${it.animeSeason.name.capitalizeFirst()} ${it.year}"
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
        lifecycleScope.launch {
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

    private fun collectPagedList() {
        resultsJob?.cancel()
        resultsJob = lifecycleScope.launch {
            animeSeasonalViewModel.getAnimeOfSeason()
                .collectLatest { pagingData ->
                    animeSeasonalAdapter.submitData(pagingData)
                    binding.rvAnimeSeasonal.smoothScrollToPosition(0)
                }
        }
    }

}