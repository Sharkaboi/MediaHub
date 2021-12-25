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
import androidx.paging.CombinedLoadStates
import androidx.paging.LoadState
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.GridLayoutManager
import com.sharkaboi.mediahub.BottomNavGraphDirections
import com.sharkaboi.mediahub.common.constants.UIConstants
import com.sharkaboi.mediahub.common.extensions.capitalizeFirst
import com.sharkaboi.mediahub.common.extensions.observe
import com.sharkaboi.mediahub.common.extensions.showToast
import com.sharkaboi.mediahub.databinding.FragmentAnimeSeasonalBinding
import com.sharkaboi.mediahub.modules.anime_seasonal.adapters.AnimeSeasonalAdapter
import com.sharkaboi.mediahub.modules.anime_seasonal.adapters.AnimeSeasonalLoadStateAdapter
import com.sharkaboi.mediahub.modules.anime_seasonal.vm.AnimeSeasonalViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class AnimeSeasonalFragment : Fragment() {
    private var _binding: FragmentAnimeSeasonalBinding? = null
    private val binding get() = _binding!!
    private val navController by lazy { findNavController() }
    private lateinit var animeSeasonalAdapter: AnimeSeasonalAdapter
    private val animeSeasonalViewModel by viewModels<AnimeSeasonalViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentAnimeSeasonalBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onDestroyView() {
        animeSeasonalAdapter.removeLoadStateListener(loadStateListener)
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

    private fun setupSeasonButtons() {
        binding.btnPrevSeason.setOnClickListener {
            animeSeasonalViewModel.previousSeason()
        }
        binding.btnNextSeason.setOnClickListener {
            animeSeasonalViewModel.nextSeason()
        }
    }

    private fun setSeasonText() {
        val selectedSeason = animeSeasonalViewModel.animeSeason
        binding.tvSeason.text =
            ("${selectedSeason.animeSeason.name.capitalizeFirst()} ${selectedSeason.year}")
    }

    private fun setUpRecyclerView() {
        binding.rvAnimeSeasonal.apply {
            animeSeasonalAdapter = AnimeSeasonalAdapter { animeId ->
                val action = BottomNavGraphDirections.openAnimeById(animeId)
                navController.navigate(action)
            }
            layoutManager = GridLayoutManager(context, UIConstants.AnimeAndMangaGridSpanCount)
            itemAnimator = DefaultItemAnimator()
            adapter = animeSeasonalAdapter.withLoadStateFooter(
                footer = AnimeSeasonalLoadStateAdapter()
            )
        }
    }

    private val loadStateListener
        get() = { loadStates: CombinedLoadStates ->
            if (loadStates.source.refresh is LoadState.Error) {
                showToast((loadStates.source.refresh as LoadState.Error).error.message)
            }
            binding.progressBar.isShowing = loadStates.refresh is LoadState.Loading
            binding.tvEmptyHint.isVisible =
                loadStates.refresh is LoadState.NotLoading && animeSeasonalAdapter.itemCount == 0
        }

    private fun setObservers() {
        animeSeasonalAdapter.addLoadStateListener(loadStateListener)
        observe(animeSeasonalViewModel.result) { pagingData ->
            setSeasonText()
            lifecycleScope.launch { animeSeasonalAdapter.submitData(pagingData) }
            binding.rvAnimeSeasonal.smoothScrollToPosition(0)
        }
    }
}
