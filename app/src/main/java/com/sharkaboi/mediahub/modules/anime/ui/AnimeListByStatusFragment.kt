package com.sharkaboi.mediahub.modules.anime.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.paging.LoadState
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.GridLayoutManager
import com.sharkaboi.mediahub.common.data.api.enums.AnimeStatus
import com.sharkaboi.mediahub.common.data.api.enums.UserAnimeSortType
import com.sharkaboi.mediahub.common.extensions.isShowing
import com.sharkaboi.mediahub.common.extensions.shortSnackBar
import com.sharkaboi.mediahub.databinding.FragmentAnimeListByStatusBinding
import com.sharkaboi.mediahub.modules.anime.adapters.AnimeListAdapter
import com.sharkaboi.mediahub.modules.anime.adapters.AnimeLoadStateAdapter
import com.sharkaboi.mediahub.modules.anime.vm.AnimeViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@AndroidEntryPoint
class AnimeListByStatusFragment : Fragment() {
    private var status: AnimeStatus = AnimeStatus.all
    private var _binding: FragmentAnimeListByStatusBinding? = null
    private val binding get() = _binding!!
    private val animeViewModel by viewModels<AnimeViewModel>()
    private lateinit var animeListAdapter: AnimeListAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            status = AnimeStatus.valueOf(it.getString(ANIME_STATUS_KEY) ?: AnimeStatus.all.name)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentAnimeListByStatusBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onDestroyView() {
        _binding = null
        super.onDestroyView()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setUpRecyclerView()
        setObservers()
    }

    private fun setUpRecyclerView() {
        binding.recyclerView.apply {
            animeListAdapter = AnimeListAdapter { animeId ->
                binding.root.shortSnackBar("$animeId clicked!")
            }
            layoutManager = GridLayoutManager(context, 3)
            itemAnimator = DefaultItemAnimator()
            adapter = animeListAdapter
            animeListAdapter.withLoadStateFooter(
                footer = AnimeLoadStateAdapter()
            )
        }
    }

    private fun setObservers() {
        viewLifecycleOwner.lifecycleScope.launch {
            animeViewModel.getAnimeList(status, UserAnimeSortType.list_updated_at)
                .collectLatest { pagingData ->
                    animeListAdapter.submitData(pagingData)
                }
        }
        viewLifecycleOwner.lifecycleScope.launch {
            animeListAdapter.addLoadStateListener { loadStates ->
                binding.progressBar.isShowing = loadStates.refresh is LoadState.Loading
                binding.tvEmptyHint.isVisible =
                    loadStates.refresh is LoadState.NotLoading && animeListAdapter.itemCount == 0
            }
        }

    }

    companion object {
        private const val ANIME_STATUS_KEY = "status"
        private const val TAG = "AnimeListByStatusFrgmnt"

        @JvmStatic
        fun newInstance(status: AnimeStatus) =
            AnimeListByStatusFragment().apply {
                arguments = Bundle().apply {
                    putString(ANIME_STATUS_KEY, status.name)
                }
            }
    }
}