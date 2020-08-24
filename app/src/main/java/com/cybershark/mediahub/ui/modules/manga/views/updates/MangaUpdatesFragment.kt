package com.cybershark.mediahub.ui.modules.manga.views.updates

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isGone
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.coroutineScope
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.cybershark.mediahub.R
import com.cybershark.mediahub.databinding.FragmentMangaUpdatesBinding
import com.cybershark.mediahub.ui.modules.manga.adapters.MangaUpdatesAdapter
import com.cybershark.mediahub.ui.modules.manga.viewmodels.MangaViewModel
import kotlinx.coroutines.launch

class MangaUpdatesFragment : Fragment() {

    private val mangaViewModel by lazy { ViewModelProvider(this).get(MangaViewModel::class.java) }
    private lateinit var binding : FragmentMangaUpdatesBinding

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = FragmentMangaUpdatesBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.contentLoadingScreen.isVisible = true
        setupRecyclerView()
        setupListeners()
        binding.contentLoadingScreen.isGone = true
    }

    private fun setupListeners() {
        binding.swipeRefreshMangaUpdates.setOnRefreshListener{
            viewLifecycleOwner.lifecycle.coroutineScope.launch { mangaViewModel.updateNewChaptersFromRep() }
            mangaViewModel.testUpdate()
            binding.swipeRefreshMangaUpdates.isRefreshing = false
        }
    }

    private fun setupRecyclerView() {
        val adapter = MangaUpdatesAdapter()

        binding.rvMangaUpdates.apply {
            this.adapter = adapter
            this.layoutManager = LinearLayoutManager(context)
            this.addItemDecoration(DividerItemDecoration(context, LinearLayoutManager.VERTICAL))
            this.setHasFixedSize(true)
            itemAnimator = DefaultItemAnimator()
        }

        mangaViewModel.dummyData.observe(viewLifecycleOwner, Observer {
            binding.tvNoChapters.isVisible = it.isEmpty()
            adapter.setAdapterList(it)
        })
    }
}
