package com.sharkaboi.mediahub.modules.anime_suggestions.ui

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
import com.sharkaboi.mediahub.BottomNavGraphDirections
import com.sharkaboi.mediahub.common.constants.UIConstants.AnimeAndMangaGridSpanCount
import com.sharkaboi.mediahub.common.extensions.observe
import com.sharkaboi.mediahub.common.extensions.showToast
import com.sharkaboi.mediahub.databinding.FragmentAnimeSuggestionsBinding
import com.sharkaboi.mediahub.modules.anime_suggestions.adapters.AnimeSuggestionsAdapter
import com.sharkaboi.mediahub.modules.anime_suggestions.adapters.AnimeSuggestionsLoadStateAdapter
import com.sharkaboi.mediahub.modules.anime_suggestions.vm.AnimeSuggestionsViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class AnimeSuggestionsFragment : Fragment() {
    private var _binding: FragmentAnimeSuggestionsBinding? = null
    private val binding get() = _binding!!
    private val navController by lazy { findNavController() }
    private lateinit var animeSuggestionsAdapter: AnimeSuggestionsAdapter
    private val animeSuggestionsViewModel by viewModels<AnimeSuggestionsViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentAnimeSuggestionsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onDestroyView() {
        binding.rvAnimeSuggestions.adapter = null
        _binding = null
        super.onDestroyView()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.toolbar.setNavigationOnClickListener { navController.navigateUp() }
        setUpRecyclerView()
        setObservers()
    }

    private fun setUpRecyclerView() {
        binding.rvAnimeSuggestions.apply {
            animeSuggestionsAdapter = AnimeSuggestionsAdapter { animeId ->
                val action = BottomNavGraphDirections.openAnimeById(animeId)
                navController.navigate(action)
            }
            layoutManager = GridLayoutManager(context, AnimeAndMangaGridSpanCount)
            itemAnimator = DefaultItemAnimator()
            adapter = animeSuggestionsAdapter.withLoadStateFooter(
                footer = AnimeSuggestionsLoadStateAdapter()
            )
        }
    }

    private fun setObservers() {
        animeSuggestionsAdapter.addLoadStateListener { loadStates ->
            if (loadStates.source.refresh is LoadState.Error) {
                showToast((loadStates.source.refresh as LoadState.Error).error.message)
            }
            binding.progressBar.isShowing = loadStates.refresh is LoadState.Loading
            binding.tvEmptyHint.isVisible =
                loadStates.refresh is LoadState.NotLoading && animeSuggestionsAdapter.itemCount == 0
        }
        observe(animeSuggestionsViewModel.result) { pagingData ->
            lifecycleScope.launch { animeSuggestionsAdapter.submitData(pagingData) }
        }
    }
}
