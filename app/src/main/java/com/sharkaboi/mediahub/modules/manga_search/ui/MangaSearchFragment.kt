package com.sharkaboi.mediahub.modules.manga_search.ui

import android.app.Activity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
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
import com.sharkaboi.mediahub.databinding.FragmentMangaSearchBinding
import com.sharkaboi.mediahub.modules.manga_search.adapters.MangaSearchListAdapter
import com.sharkaboi.mediahub.modules.manga_search.adapters.MangaSearchLoadStateAdapter
import com.sharkaboi.mediahub.modules.manga_search.vm.MangaSearchViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class MangaSearchFragment : Fragment() {
    private var _binding: FragmentMangaSearchBinding? = null
    private val binding get() = _binding!!
    private lateinit var mangaSearchListAdapter: MangaSearchListAdapter
    private val mangaSearchViewModel by viewModels<MangaSearchViewModel>()
    private val navController by lazy { findNavController() }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentMangaSearchBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onDestroyView() {
        mangaSearchListAdapter.removeLoadStateListener(loadStateListener)
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
            mangaSearchListAdapter = MangaSearchListAdapter { mangaId ->
                val action = BottomNavGraphDirections.openMangaById(mangaId)
                navController.navigate(action)
            }
            layoutManager = GridLayoutManager(context, UIConstants.AnimeAndMangaGridSpanCount)
            setHasFixedSize(true)
            itemAnimator = DefaultItemAnimator()
            adapter = mangaSearchListAdapter.withLoadStateFooter(
                footer = MangaSearchLoadStateAdapter()
            )
        }
    }

    private val loadStateListener = { loadStates: CombinedLoadStates ->
        if (loadStates.source.refresh is LoadState.Error) {
            showToast((loadStates.source.refresh as LoadState.Error).error.message)
        }
        binding.progress.isShowing = loadStates.refresh is LoadState.Loading
        binding.searchEmptyView.root.isVisible =
            loadStates.refresh is LoadState.NotLoading && mangaSearchListAdapter.itemCount == 0
        binding.searchEmptyView.tvHint.text =
            getString(R.string.manga_search_no_result_hint)
    }

    private fun setObservers() {
        mangaSearchListAdapter.addLoadStateListener(loadStateListener)
        val debounce = debounce<CharSequence>(scope = lifecycleScope) {
            searchAnime(it)
        }
        binding.svSearch.doOnTextChanged { query, _, _, _ ->
            debounce(query)
        }
        observe(mangaSearchViewModel.pagedSearchResult) { pagingData ->
            lifecycleScope.launch { mangaSearchListAdapter.submitData(pagingData) }
            binding.rvSearchResults.scrollToPosition(0)
        }
    }

    private fun searchAnime(query: CharSequence?) {
        val trimmedText = query?.toString()?.trim() ?: return
        if (trimmedText.length < 3) {
            binding.searchEmptyView.root.isVisible = true
            binding.searchEmptyView.tvHint.text = getString(R.string.manga_search_hint)
            lifecycleScope.launch { mangaSearchListAdapter.submitData(PagingData.empty()) }
            return
        }
        hideKeyboard()
        mangaSearchViewModel.getManga(trimmedText)
    }

    private fun hideKeyboard() {
        val imm: InputMethodManager? =
            context?.getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager?
        imm?.hideSoftInputFromWindow(binding.svSearch.windowToken, 0)
    }
}
