package com.sharkaboi.mediahub.modules.anime.ui

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
import com.sharkaboi.mediahub.data.api.enums.AnimeStatus
import com.sharkaboi.mediahub.data.api.enums.UserAnimeSortType
import com.sharkaboi.mediahub.databinding.FragmentAnimeListByStatusBinding
import com.sharkaboi.mediahub.modules.anime.adapters.AnimeListAdapter
import com.sharkaboi.mediahub.modules.anime.adapters.AnimeLoadStateAdapter
import com.sharkaboi.mediahub.modules.anime.vm.AnimeViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@AndroidEntryPoint
class AnimeListByStatusFragment : Fragment() {
    private var _binding: FragmentAnimeListByStatusBinding? = null
    private val binding get() = _binding!!
    private val animeViewModel by viewModels<AnimeViewModel>()
    private lateinit var animeListAdapter: AnimeListAdapter
    private val navController by lazy { findNavController() }
    private var resultsJob: Job? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentAnimeListByStatusBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onDestroyView() {
        resultsJob?.cancel()
        resultsJob = null
        binding.rvAnimeByStatus.adapter = null
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

    private fun initStatus() {
        val status = arguments?.getString(ANIME_STATUS_KEY)?.let { status ->
            AnimeStatus.valueOf(status)
        } ?: AnimeStatus.all
        animeViewModel.setAnimeStatus(status)
    }

    override fun onResume() {
        super.onResume()
        getAnimeList()
    }

    private fun setUpRecyclerView() {
        binding.rvAnimeByStatus.apply {
            animeListAdapter = AnimeListAdapter { animeId ->
                val action = AnimeFragmentDirections.openAnimeDetailsWithId(animeId)
                navController.navigate(action)
            }
            layoutManager = GridLayoutManager(context, 3)
            itemAnimator = DefaultItemAnimator()
            adapter = animeListAdapter.withLoadStateFooter(
                footer = AnimeLoadStateAdapter()
            )
        }
    }

    private fun setObservers() {
        getAnimeList()
        lifecycleScope.launch {
            animeListAdapter.addLoadStateListener { loadStates ->
                if (loadStates.source.refresh is LoadState.Error) {
                    showToast((loadStates.source.refresh as LoadState.Error).error.message)
                }
                binding.progressBar.isShowing = loadStates.refresh is LoadState.Loading
                binding.tvEmptyHint.isVisible =
                    loadStates.refresh is LoadState.NotLoading && animeListAdapter.itemCount == 0
            }
        }
        binding.swipeRefresh.setOnRefreshListener {
            getAnimeList()
            binding.swipeRefresh.isRefreshing = false
        }
    }

    private fun setListeners() {
        binding.ibFilter.setOnClickListener {
            openSortMenu()
        }
    }

    private fun openSortMenu() {
        val singleItems = UserAnimeSortType.getFormattedArray()
        val checkedItem = UserAnimeSortType.values().indexOf(animeViewModel.currentChosenSortType)
        MaterialAlertDialogBuilder(requireContext())
            .setTitle("Sort anime by")
            .setSingleChoiceItems(singleItems, checkedItem) { dialog, which ->
                animeViewModel.setSortType(UserAnimeSortType.values()[which])
                getAnimeList()
                dialog.dismiss()
            }
            .show()
    }

    fun getAnimeList() {
        resultsJob?.cancel()
        resultsJob = lifecycleScope.launch {
            animeViewModel.getAnimeList().collectLatest { pagingData ->
                animeListAdapter.submitData(pagingData)
                scrollRecyclerView()
            }
        }
    }

    fun scrollRecyclerView() = binding.rvAnimeByStatus.smoothScrollToPosition(0)

    companion object {
        private const val ANIME_STATUS_KEY = "status"

        @JvmStatic
        fun newInstance(status: AnimeStatus) =
            AnimeListByStatusFragment().apply {
                arguments = Bundle().apply {
                    putString(ANIME_STATUS_KEY, status.name)
                }
            }
    }
}
