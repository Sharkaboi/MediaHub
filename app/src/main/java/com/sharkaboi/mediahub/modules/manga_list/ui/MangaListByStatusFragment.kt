package com.sharkaboi.mediahub.modules.manga_list.ui

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
import com.sharkaboi.mediahub.data.api.enums.UserMangaSortType
import com.sharkaboi.mediahub.databinding.FragmentMangaListByStatusBinding
import com.sharkaboi.mediahub.modules.manga_list.adapters.MangaListAdapter
import com.sharkaboi.mediahub.modules.manga_list.adapters.MangaLoadStateAdapter
import com.sharkaboi.mediahub.modules.manga_list.vm.MangaViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import timber.log.Timber

@AndroidEntryPoint
class MangaListByStatusFragment : Fragment() {
    private var _binding: FragmentMangaListByStatusBinding? = null
    private val binding get() = _binding!!
    private val mangaViewModel by activityViewModels<MangaViewModel>()
    private lateinit var mangaListAdapter: MangaListAdapter
    private val navController by lazy { findNavController() }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentMangaListByStatusBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onDestroyView() {
        mangaListAdapter.removeLoadStateListener(loadStateListener)
        binding.rvMangaByStatus.adapter = null
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
        mangaViewModel.refresh()
    }

    private fun setUpRecyclerView() {
        binding.rvMangaByStatus.apply {
            mangaListAdapter = MangaListAdapter { mangaId ->
                val action = BottomNavGraphDirections.openMangaById(mangaId)
                navController.navigate(action)
            }
            layoutManager = GridLayoutManager(context, UIConstants.AnimeAndMangaGridSpanCount)
            itemAnimator = DefaultItemAnimator()
            adapter = mangaListAdapter.withLoadStateFooter(
                footer = MangaLoadStateAdapter()
            )
        }
    }

    private val loadStateListener = { loadStates: CombinedLoadStates ->
        if (loadStates.source.refresh is LoadState.Error) {
            val errorMessage = (loadStates.source.refresh as LoadState.Error).error.message
            Timber.d("setObservers: $errorMessage")
            showToast(errorMessage)
        }
        binding.progressBar.isShowing = loadStates.refresh is LoadState.Loading
        binding.tvEmptyHint.isVisible =
            loadStates.refresh is LoadState.NotLoading && mangaListAdapter.itemCount == 0
    }

    private fun setListeners() {
        mangaListAdapter.addLoadStateListener(loadStateListener)
        binding.swipeRefresh.setOnRefreshListener {
            mangaViewModel.refresh()
            binding.swipeRefresh.isRefreshing = false
        }
        binding.ibFilter.setOnClickListener {
            openSortMenu()
        }
        observe(mangaViewModel.mangaList) { pagingData ->
            lifecycleScope.launch { mangaListAdapter.submitData(pagingData) }
            scrollRecyclerView()
        }
    }

    private fun openSortMenu() {
        val singleItems = UserMangaSortType.getFormattedArray(requireContext())
        val checkedItem = UserMangaSortType.values().indexOf(mangaViewModel.currentChosenSortType)
        MaterialAlertDialogBuilder(requireContext())
            .setTitle(R.string.sort_manga_by_hint)
            .setSingleChoiceItems(singleItems, checkedItem) { dialog, which ->
                mangaViewModel.setSortType(UserMangaSortType.values()[which])
                dialog.dismiss()
            }.show()
    }

    fun scrollRecyclerView() = binding.rvMangaByStatus.smoothScrollToPosition(0)
}
