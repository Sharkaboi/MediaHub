package com.sharkaboi.mediahub.modules.anime_search.ui

import android.app.Activity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import androidx.core.view.isVisible
import androidx.core.widget.doOnTextChanged
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.paging.CombinedLoadStates
import androidx.paging.LoadState
import androidx.paging.PagingData
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.GridLayoutManager
import com.sharkaboi.mediahub.BottomNavGraphDirections
import com.sharkaboi.mediahub.R
import com.sharkaboi.mediahub.common.constants.UIConstants
import com.sharkaboi.mediahub.common.extensions.debounce
import com.sharkaboi.mediahub.common.extensions.observe
import com.sharkaboi.mediahub.common.extensions.showToast
import com.sharkaboi.mediahub.databinding.FragmentAnimeSearchBinding
import com.sharkaboi.mediahub.modules.anime_search.adapters.AnimeSearchListAdapter
import com.sharkaboi.mediahub.modules.anime_search.adapters.AnimeSearchLoadStateAdapter
import com.sharkaboi.mediahub.modules.anime_search.vm.AnimeSearchViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class AnimeSearchFragment : Fragment() {
    private var _binding: FragmentAnimeSearchBinding? = null
    private val binding get() = _binding!!
    private lateinit var animeSearchListAdapter: AnimeSearchListAdapter
    private val animeSearchViewModel by viewModels<AnimeSearchViewModel>()
    private val navController by lazy { findNavController() }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentAnimeSearchBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onDestroyView() {
        animeSearchListAdapter.removeLoadStateListener(loadStateListener)
        binding.rvSearchResults.adapter = null
        _binding = null
        super.onDestroyView()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setUpRecyclerView()
        setObservers()
    }

    private fun setUpRecyclerView() {
        binding.rvSearchResults.apply {
            animeSearchListAdapter = AnimeSearchListAdapter { animeId ->
                val action = BottomNavGraphDirections.openAnimeById(animeId)
                navController.navigate(action)
            }
            layoutManager = GridLayoutManager(context, UIConstants.AnimeAndMangaGridSpanCount)
            itemAnimator = DefaultItemAnimator()
            adapter = animeSearchListAdapter.withLoadStateFooter(
                footer = AnimeSearchLoadStateAdapter()
            )
        }
    }

    private val loadStateListener = { loadStates: CombinedLoadStates ->
        if (loadStates.source.refresh is LoadState.Error) {
            showToast((loadStates.source.refresh as LoadState.Error).error.message)
        }
        binding.progress.isShowing = loadStates.refresh is LoadState.Loading
        binding.searchEmptyView.root.isVisible =
            loadStates.refresh is LoadState.NotLoading && animeSearchListAdapter.itemCount == 0
        binding.searchEmptyView.tvHint.text =
            getString(R.string.anime_search_no_result_hint)
    }

    private fun setObservers() {
        animeSearchListAdapter.addLoadStateListener(loadStateListener)
        val debounce = debounce<CharSequence>(scope = lifecycleScope) {
            searchAnime(it)
        }
        binding.svSearch.doOnTextChanged { query, _, _, _ ->
            debounce(query)
        }
        observe(animeSearchViewModel.pagedSearchResult) { pagingData ->
            lifecycleScope.launch { animeSearchListAdapter.submitData(pagingData) }
            binding.rvSearchResults.scrollToPosition(0)
        }
    }

    private fun searchAnime(query: CharSequence?) {
        val trimmedText = query?.toString()?.trim() ?: return
        if (trimmedText.length < 3) {
            binding.searchEmptyView.root.isVisible = true
            binding.searchEmptyView.tvHint.text = getString(R.string.anime_search_hint)
            lifecycleScope.launch { animeSearchListAdapter.submitData(PagingData.empty()) }
            return
        }
        hideKeyboard()
        animeSearchViewModel.getAnime(trimmedText)
    }

    private fun hideKeyboard() {
        val imm: InputMethodManager? =
            context?.getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager?
        imm?.hideSoftInputFromWindow(binding.svSearch.windowToken, 0)
    }
}
