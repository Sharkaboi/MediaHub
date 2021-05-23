package com.sharkaboi.mediahub.modules.manga.ui

import android.os.Bundle
import android.util.Log
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
import com.sharkaboi.mediahub.common.data.api.enums.MangaStatus
import com.sharkaboi.mediahub.common.data.api.enums.UserMangaSortType
import com.sharkaboi.mediahub.common.extensions.showToast
import com.sharkaboi.mediahub.databinding.FragmentMangaListByStatusBinding
import com.sharkaboi.mediahub.modules.manga.adapters.MangaListAdapter
import com.sharkaboi.mediahub.modules.manga.adapters.MangaLoadStateAdapter
import com.sharkaboi.mediahub.modules.manga.vm.MangaViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@AndroidEntryPoint
class MangaListByStatusFragment : Fragment() {
    private var status: MangaStatus = MangaStatus.all
    private var _binding: FragmentMangaListByStatusBinding? = null
    private val binding get() = _binding!!
    private val mangaViewModel by viewModels<MangaViewModel>()
    private lateinit var mangaListAdapter: MangaListAdapter
    private val navController by lazy { findNavController() }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            status = MangaStatus.valueOf(
                it.getString(MANGA_STATUS_KEY) ?: MangaStatus.all.name
            )
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentMangaListByStatusBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onDestroyView() {
        binding.rvMangaByStatus.adapter = null
        _binding = null
        super.onDestroyView()
    }

    override fun onResume() {
        super.onResume()
        viewLifecycleOwner.lifecycleScope.launch {
            mangaViewModel.getMangaList(status, UserMangaSortType.list_updated_at)
                .collectLatest { pagingData ->
                    mangaListAdapter.submitData(pagingData)
                }
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setUpRecyclerView()
        setObservers()
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
        viewLifecycleOwner.lifecycleScope.launch {
            mangaViewModel.getMangaList(status, UserMangaSortType.list_updated_at)
                .collectLatest { pagingData ->
                    mangaListAdapter.submitData(pagingData)
                }
        }
        viewLifecycleOwner.lifecycleScope.launch {
            mangaListAdapter.addLoadStateListener { loadStates ->
                if (loadStates.source.refresh is LoadState.Error) {
                    val errorMessage = (loadStates.source.refresh as LoadState.Error).error.message
                    Log.d(TAG, "setObservers: $errorMessage")
                    showToast(errorMessage)
                }
                binding.progressBar.isShowing = loadStates.refresh is LoadState.Loading
                binding.tvEmptyHint.isVisible =
                    loadStates.refresh is LoadState.NotLoading && mangaListAdapter.itemCount == 0
            }
        }
        binding.swipeRefresh.setOnRefreshListener {
            viewLifecycleOwner.lifecycleScope.launch {
                mangaViewModel.getMangaList(status, UserMangaSortType.list_updated_at)
                    .collectLatest { pagingData ->
                        binding.swipeRefresh.isRefreshing = false
                        mangaListAdapter.submitData(pagingData)
                    }
            }
        }
    }

    fun scrollRecyclerView() = binding.rvMangaByStatus.smoothScrollToPosition(0)

    companion object {
        private const val MANGA_STATUS_KEY = "status"
        private const val TAG = "MangaListByStatusFragme"

        @JvmStatic
        fun newInstance(status: MangaStatus) =
            MangaListByStatusFragment().apply {
                arguments = Bundle().apply {
                    putString(MANGA_STATUS_KEY, status.name)
                }
            }
    }
}