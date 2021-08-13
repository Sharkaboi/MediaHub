package com.sharkaboi.mediahub.modules.discover.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.LinearLayoutManager
import com.sharkaboi.mediahub.BottomNavGraphDirections
import com.sharkaboi.mediahub.common.extensions.addFooter
import com.sharkaboi.mediahub.common.extensions.observe
import com.sharkaboi.mediahub.common.extensions.showToast
import com.sharkaboi.mediahub.data.api.models.anime.AnimeRankingResponse
import com.sharkaboi.mediahub.data.api.models.anime.AnimeSeasonalResponse
import com.sharkaboi.mediahub.data.api.models.anime.AnimeSuggestionsResponse
import com.sharkaboi.mediahub.databinding.FragmentDiscoverBinding
import com.sharkaboi.mediahub.modules.discover.adapters.AiringAnimeAdapter
import com.sharkaboi.mediahub.modules.discover.adapters.AnimeRankingAdapter
import com.sharkaboi.mediahub.modules.discover.adapters.AnimeSuggestionsAdapter
import com.sharkaboi.mediahub.modules.discover.adapters.LoadMoreAdapter
import com.sharkaboi.mediahub.modules.discover.util.DiscoverAnimeListWrapper
import com.sharkaboi.mediahub.modules.discover.vm.DiscoverState
import com.sharkaboi.mediahub.modules.discover.vm.DiscoverViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class DiscoverFragment : Fragment() {
    private var _binding: FragmentDiscoverBinding? = null
    private val binding get() = _binding!!
    private val navController by lazy { findNavController() }
    private val discoverDetailsViewModel by viewModels<DiscoverViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentDiscoverBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onDestroyView() {
        binding.rvAnimeRecommendations.adapter = null
        _binding = null
        super.onDestroyView()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupListeners()
        setupObservers()
    }

    private fun setupListeners() {
        binding.apply {
            btnAnimeRanking.setOnClickListener {
                navController.navigate(DiscoverFragmentDirections.openAnimeRankings(null))
            }
            btnAnimeSeasonal.setOnClickListener {
                navController.navigate(DiscoverFragmentDirections.openAnimeSeasonals(null, 0))
            }
            btnAnimeSuggestion.setOnClickListener {
                navController.navigate(DiscoverFragmentDirections.openAnimeSuggestions())
            }
            btnMangaRanking.setOnClickListener {
                navController.navigate(DiscoverFragmentDirections.openMangaRankings(null))
            }
            btnAnimeSearch.setOnClickListener {
                navController.navigate(DiscoverFragmentDirections.openAnimeSearch())
            }
            btnMangaSearch.setOnClickListener {
                navController.navigate(DiscoverFragmentDirections.openMangaSearch())
            }
        }
    }

    private fun setupObservers() {
        observe(discoverDetailsViewModel.uiState) { uiState ->
            binding.progress.isVisible = uiState is DiscoverState.Loading
            when (uiState) {
                is DiscoverState.AnimeDetailsFailure -> showToast(uiState.message)
                is DiscoverState.AnimeDetailsSuccess -> setupRecyclerViews(uiState.data)
                else -> Unit
            }
        }
    }

    private fun setupRecyclerViews(discoverAnimeListWrapper: DiscoverAnimeListWrapper) {
        setupAnimeRecommendationsList(discoverAnimeListWrapper.animeSuggestions)
        setupAnimeAiringList(discoverAnimeListWrapper.animeOfCurrentSeason)
        setupAnimeRankingList(discoverAnimeListWrapper.animeRankings)
    }

    private fun setupAnimeRankingList(animeRankings: List<AnimeRankingResponse.Data>) {
        binding.tvAnimeRankingEmpty.isVisible = animeRankings.isEmpty()
        binding.rvAnimeRanking.adapter = AnimeRankingAdapter { animeId ->
            openAnimeWithId(animeId)
        }.apply {
            submitList(animeRankings)
        }.addFooter {
            LoadMoreAdapter {
                navController.navigate(DiscoverFragmentDirections.openAnimeRankings(null))
            }
        }
        binding.rvAnimeRanking.layoutManager =
            LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
        binding.rvAnimeRanking.setHasFixedSize(true)
        binding.rvAnimeRanking.itemAnimator = DefaultItemAnimator()
    }

    private fun setupAnimeAiringList(animeOfCurrentSeason: List<AnimeSeasonalResponse.Data>) {
        binding.tvAnimeAiringEmpty.isVisible = animeOfCurrentSeason.isEmpty()
        binding.rvAnimeAiring.adapter = AiringAnimeAdapter { animeId ->
            openAnimeWithId(animeId)
        }.apply {
            submitList(animeOfCurrentSeason)
        }.addFooter {
            LoadMoreAdapter {
                navController.navigate(DiscoverFragmentDirections.openAnimeSeasonals(null, 0))
            }
        }
        binding.rvAnimeAiring.layoutManager =
            LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
        binding.rvAnimeAiring.setHasFixedSize(true)
        binding.rvAnimeAiring.itemAnimator = DefaultItemAnimator()
    }

    private fun setupAnimeRecommendationsList(animeSuggestions: List<AnimeSuggestionsResponse.Data>) {
        binding.tvAnimeRecommendationsEmpty.isVisible = animeSuggestions.isEmpty()
        binding.rvAnimeRecommendations.adapter = AnimeSuggestionsAdapter { animeId ->
            openAnimeWithId(animeId)
        }.apply {
            submitList(animeSuggestions)
        }.addFooter {
            LoadMoreAdapter {
                navController.navigate(DiscoverFragmentDirections.openAnimeSuggestions())
            }
        }
        binding.rvAnimeRecommendations.layoutManager =
            LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
        binding.rvAnimeRecommendations.setHasFixedSize(true)
        binding.rvAnimeRecommendations.itemAnimator = DefaultItemAnimator()
    }

    private fun openAnimeWithId(animeId: Int) {
        val action = BottomNavGraphDirections.openAnimeById(animeId)
        navController.navigate(action)
    }
}
