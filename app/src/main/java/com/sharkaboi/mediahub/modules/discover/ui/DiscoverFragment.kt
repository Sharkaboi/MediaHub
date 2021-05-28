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
import com.sharkaboi.mediahub.common.extensions.addFooter
import com.sharkaboi.mediahub.common.extensions.showToast
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
        inflater: LayoutInflater, container: ViewGroup?,
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
                navController.navigate(DiscoverFragmentDirections.openAnimeRankings())
            }
            btnAnimeSeasonal.setOnClickListener {
                navController.navigate(DiscoverFragmentDirections.openAnimeSeasonals())
            }
            btnAnimeSuggestion.setOnClickListener {
                navController.navigate(DiscoverFragmentDirections.openAnimeSuggestions())
            }
            btnMangaRanking.setOnClickListener {
                navController.navigate(DiscoverFragmentDirections.openMangaRankings())
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
        discoverDetailsViewModel.uiState.observe(viewLifecycleOwner) { uiState ->
            binding.progress.isVisible = uiState is DiscoverState.Loading
            when (uiState) {
                is DiscoverState.AnimeDetailsFailure -> showToast(uiState.message)
                is DiscoverState.AnimeDetailsSuccess -> setupRecyclerViews(uiState.data)
                else -> Unit
            }
        }
    }

    private fun setupRecyclerViews(discoverAnimeListWrapper: DiscoverAnimeListWrapper) {
        binding.tvAnimeRecommendationsEmpty.isVisible =
            discoverAnimeListWrapper.animeSuggestions.isEmpty()
        binding.rvAnimeRecommendations.apply {
            adapter = AnimeSuggestionsAdapter { animeId ->
                val action = DiscoverFragmentDirections.openAnimeById(animeId)
                navController.navigate(action)
            }.apply {
                submitList(discoverAnimeListWrapper.animeSuggestions)
            }.addFooter {
                LoadMoreAdapter {
                    navController.navigate(DiscoverFragmentDirections.openAnimeSuggestions())
                }
            }
            layoutManager =
                LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
            setHasFixedSize(true)
            itemAnimator = DefaultItemAnimator()
        }
        binding.tvAnimeAiringEmpty.isVisible =
            discoverAnimeListWrapper.animeOfCurrentSeason.isEmpty()
        binding.rvAnimeAiring.apply {
            adapter = AiringAnimeAdapter { animeId ->
                val action = DiscoverFragmentDirections.openAnimeById(animeId)
                navController.navigate(action)
            }.apply {
                submitList(discoverAnimeListWrapper.animeOfCurrentSeason)
            }.addFooter {
                LoadMoreAdapter {
                    navController.navigate(DiscoverFragmentDirections.openAnimeSeasonals())
                }
            }
            layoutManager =
                LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
            setHasFixedSize(true)
            itemAnimator = DefaultItemAnimator()
        }
        binding.tvAnimeRankingEmpty.isVisible =
            discoverAnimeListWrapper.animeRankings.isEmpty()
        binding.rvAnimeRanking.apply {
            adapter = AnimeRankingAdapter { animeId ->
                val action = DiscoverFragmentDirections.openAnimeById(animeId)
                navController.navigate(action)
            }.apply {
                submitList(discoverAnimeListWrapper.animeRankings)
            }.addFooter {
                LoadMoreAdapter {
                    navController.navigate(DiscoverFragmentDirections.openAnimeRankings())
                }
            }
            layoutManager =
                LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
            setHasFixedSize(true)
            itemAnimator = DefaultItemAnimator()
        }
    }

    companion object {
        private const val TAG = "DiscoverFragment"
    }
}