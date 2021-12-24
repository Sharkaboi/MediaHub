package com.sharkaboi.mediahub.modules.anime_list.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.paging.CombinedLoadStates
import androidx.paging.LoadState
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.GridLayoutManager
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.sharkaboi.mediahub.BottomNavGraphDirections
import com.sharkaboi.mediahub.R
import com.sharkaboi.mediahub.common.constants.UIConstants
import com.sharkaboi.mediahub.common.extensions.observe
import com.sharkaboi.mediahub.common.extensions.showToast
import com.sharkaboi.mediahub.data.api.enums.UserAnimeSortType
import com.sharkaboi.mediahub.databinding.FragmentAnimeListByStatusBinding
import com.sharkaboi.mediahub.modules.anime_list.adapters.AnimeListAdapter
import com.sharkaboi.mediahub.modules.anime_list.adapters.AnimeLoadStateAdapter
import com.sharkaboi.mediahub.modules.anime_list.vm.AnimeViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class AnimeListByStatusFragment : Fragment() {
    private var _binding: FragmentAnimeListByStatusBinding? = null
    private val binding get() = _binding!!
    private val animeViewModel by activityViewModels<AnimeViewModel>()
    private lateinit var animeListAdapter: AnimeListAdapter
    private val navController by lazy { findNavController() }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentAnimeListByStatusBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onDestroyView() {
        animeListAdapter.removeLoadStateListener(loadStateListener)
        binding.rvAnimeByStatus.adapter = null
        _binding = null
        super.onDestroyView()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setUpRecyclerView()
        setListeners()
    }

    override fun onResume() {
        super.onResume()
        animeViewModel.refresh()
    }

    private fun setUpRecyclerView() {
        binding.rvAnimeByStatus.apply {
            animeListAdapter = AnimeListAdapter { animeId ->
                val action = BottomNavGraphDirections.openAnimeById(animeId)
                navController.navigate(action)
            }
            layoutManager = GridLayoutManager(context, UIConstants.AnimeAndMangaGridSpanCount)
            itemAnimator = DefaultItemAnimator()
            adapter = animeListAdapter.withLoadStateFooter(
                footer = AnimeLoadStateAdapter()
            )
        }
    }

    private val loadStateListener = { loadStates: CombinedLoadStates ->
        if (loadStates.source.refresh is LoadState.Error) {
            showToast((loadStates.source.refresh as LoadState.Error).error.message)
        }
        binding.progressBar.isShowing = loadStates.refresh is LoadState.Loading
        binding.tvEmptyHint.isVisible =
            loadStates.refresh is LoadState.NotLoading && animeListAdapter.itemCount == 0
    }

    private fun setListeners() {
        animeListAdapter.addLoadStateListener(loadStateListener)
        binding.swipeRefresh.setOnRefreshListener {
            animeViewModel.refresh()
            binding.swipeRefresh.isRefreshing = false
        }
        binding.ibFilter.setOnClickListener {
            openSortMenu()
        }
        observe(animeViewModel.animeList) { pagingData ->
            lifecycleScope.launch { animeListAdapter.submitData(pagingData) }
            scrollRecyclerView()
        }
    }

    private fun openSortMenu() {
        val singleItems = UserAnimeSortType.getFormattedArray(requireContext())
        val checkedItem = UserAnimeSortType.values().indexOf(animeViewModel.currentChosenSortType)
        MaterialAlertDialogBuilder(requireContext())
            .setTitle(R.string.sort_anime_by_hint)
            .setSingleChoiceItems(singleItems, checkedItem) { dialog, which ->
                animeViewModel.setSortType(UserAnimeSortType.values()[which])
                dialog.dismiss()
            }.show()
    }

    fun scrollRecyclerView() = binding.rvAnimeByStatus.smoothScrollToPosition(0)
}
