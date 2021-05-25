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
import com.sharkaboi.mediahub.common.extensions.showToast
import com.sharkaboi.mediahub.databinding.FragmentDiscoverBinding
import com.sharkaboi.mediahub.modules.discover.adapters.AnimeSuggestionsAdapter
import com.sharkaboi.mediahub.modules.discover.vm.DiscoverState
import com.sharkaboi.mediahub.modules.discover.vm.DiscoverViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class DiscoverFragment : Fragment() {
    private var _binding: FragmentDiscoverBinding? = null
    private val binding get() = _binding!!
    private val navController by lazy { findNavController() }
    private val discoverDetailsViewModel by viewModels<DiscoverViewModel>()
    private lateinit var animeSuggestionsAdapter: AnimeSuggestionsAdapter

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
        setupRecyclerViews()
        setupObservers()
    }

    private fun setupListeners() {
        binding.apply {
            btnAnimeRanking.setOnClickListener {
                navController.navigate(DiscoverFragmentDirections.openAnimeRankings())
            }
            tvAnimeRecommendationsMore.setOnClickListener {
                navController.navigate(DiscoverFragmentDirections.openAnimeRankings())
            }
            // FIXME: 25-05-2021 change destinations after adding
            btnAnimeSeasonal.setOnClickListener {
                navController.navigate(DiscoverFragmentDirections.openAnimeRankings())
            }
            btnAnimeSuggestion.setOnClickListener {
                navController.navigate(DiscoverFragmentDirections.openAnimeRankings())
            }
            btnMangaRanking.setOnClickListener {
                navController.navigate(DiscoverFragmentDirections.openAnimeRankings())
            }
        }
    }

    private fun setupRecyclerViews() {
        binding.rvAnimeRecommendations.apply {
            animeSuggestionsAdapter = AnimeSuggestionsAdapter { animeId ->
                val action = DiscoverFragmentDirections.openAnimeById(animeId)
                navController.navigate(action)
            }
            adapter = animeSuggestionsAdapter
            layoutManager =
                LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
            setHasFixedSize(true)
            itemAnimator = DefaultItemAnimator()
        }
    }

    private fun setupObservers() {
        discoverDetailsViewModel.uiState.observe(viewLifecycleOwner) { uiState ->
            binding.progress.isVisible = uiState is DiscoverState.Loading
            when (uiState) {
                is DiscoverState.AnimeDetailsFailure -> {
                    showToast(uiState.message)
                }
                is DiscoverState.AnimeDetailsSuccess -> {
                    animeSuggestionsAdapter.submitList(uiState.data)
                    binding.tvAnimeRecommendationsEmpty.isVisible = uiState.data.isEmpty()
                }
                else -> Unit
            }
        }
    }
}