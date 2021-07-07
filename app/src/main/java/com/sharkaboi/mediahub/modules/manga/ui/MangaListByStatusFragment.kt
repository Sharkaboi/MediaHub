package com.sharkaboi.mediahub.modules.manga.ui

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
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.sharkaboi.mediahub.common.extensions.showToast
import com.sharkaboi.mediahub.data.api.enums.MangaStatus
import com.sharkaboi.mediahub.data.api.enums.UserMangaSortType
import com.sharkaboi.mediahub.databinding.FragmentMangaListByStatusBinding
import com.sharkaboi.mediahub.modules.manga.adapters.MangaListAdapter
import com.sharkaboi.mediahub.modules.manga.adapters.MangaLoadStateAdapter
import com.sharkaboi.mediahub.modules.manga.vm.MangaViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import timber.log.Timber

@AndroidEntryPoint
class MangaListByStatusFragment : Fragment() {
    private var _binding: FragmentMangaListByStatusBinding? = null
    private val binding get() = _binding!!
    private val mangaViewModel by viewModels<MangaViewModel>()
    private lateinit var mangaListAdapter: MangaListAdapter
    private val navController by lazy { findNavController() }
    private var resultsJob: Job? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentMangaListByStatusBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onDestroyView() {
        resultsJob?.cancel()
        resultsJob = null
        binding.rvMangaByStatus.adapter = null
        _binding = null
        super.onDestroyView()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initStatus()
        setUpRecyclerView()
        setObservers()
        setListeners()
    }

    override fun onResume() {
        super.onResume()
        getMangaList()
    }

    private fun initStatus() {
        val status = arguments?.getString(MANGA_STATUS_KEY)?.let { status ->
            MangaStatus.valueOf(status)
        } ?: MangaStatus.all
        mangaViewModel.setMangaStatus(status)
    }

    private fun setUpRecyclerView() {
        binding.rvMangaByStatus.apply {
            mangaListAdapter = MangaListAdapter { mangaId ->
                val action = MangaFragmentDirections.openMangaDetailsWithId(mangaId)
                navController.navigate(action)
            }
            layoutManager = GridLayoutManager(context, 3)
            itemAnimator = DefaultItemAnimator()
            adapter = mangaListAdapter.withLoadStateFooter(
                footer = MangaLoadStateAdapter()
            )
        }
    }

    private fun setObservers() {
        getMangaList()
        lifecycleScope.launch {
            mangaListAdapter.addLoadStateListener { loadStates ->
                if (loadStates.source.refresh is LoadState.Error) {
                    val errorMessage = (loadStates.source.refresh as LoadState.Error).error.message
                    Timber.d("setObservers: $errorMessage")
                    showToast(errorMessage)
                }
                binding.progressBar.isShowing = loadStates.refresh is LoadState.Loading
                binding.tvEmptyHint.isVisible =
                    loadStates.refresh is LoadState.NotLoading && mangaListAdapter.itemCount == 0
            }
        }
        binding.swipeRefresh.setOnRefreshListener {
            getMangaList()
            binding.swipeRefresh.isRefreshing = false
        }
    }

    private fun setListeners() {
        binding.ibFilter.setOnClickListener {
            openSortMenu()
        }
    }

    private fun openSortMenu() {
        val singleItems = UserMangaSortType.getFormattedArray()
        val checkedItem = UserMangaSortType.values().indexOf(mangaViewModel.currentChosenSortType)
        MaterialAlertDialogBuilder(requireContext())
            .setTitle("Sort manga by")
            .setSingleChoiceItems(singleItems, checkedItem) { dialog, which ->
                mangaViewModel.setSortType(UserMangaSortType.values()[which])
                getMangaList()
                dialog.dismiss()
            }
            .show()
    }

    private fun getMangaList() {
        resultsJob?.cancel()
        resultsJob = lifecycleScope.launch {
            mangaViewModel.getMangaList()
                .collectLatest { pagingData ->
                    mangaListAdapter.submitData(pagingData)
                    scrollRecyclerView()
                }
        }
    }

    fun scrollRecyclerView() = binding.rvMangaByStatus.smoothScrollToPosition(0)

    companion object {
        private const val MANGA_STATUS_KEY = "status"

        @JvmStatic
        fun newInstance(status: MangaStatus) =
            MangaListByStatusFragment().apply {
                arguments = Bundle().apply {
                    putString(MANGA_STATUS_KEY, status.name)
                }
            }
    }
}
