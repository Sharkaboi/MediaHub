package com.cybershark.mediahub.ui.modules.manga.views.library

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
import androidx.recyclerview.widget.GridLayoutManager
import com.cybershark.mediahub.databinding.FragmentMangaLibraryBinding
import com.cybershark.mediahub.ui.modules.manga.adapters.MangaLibraryAdapter
import com.cybershark.mediahub.ui.modules.manga.viewmodels.MangaViewModel
import kotlinx.coroutines.launch

class MangaLibraryFragment : Fragment() {

    private val mangaViewModel by lazy { ViewModelProvider(this).get(MangaViewModel::class.java) }
    private lateinit var binding: FragmentMangaLibraryBinding

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = FragmentMangaLibraryBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.contentLoadingScreen.isVisible = true

        setupRecyclerView()
        setListeners()

        binding.contentLoadingScreen.isGone =  true
    }

    private fun setListeners() {
        binding.swipeRefreshMangaLibrary.setOnRefreshListener {
            viewLifecycleOwner.lifecycle.coroutineScope.launch { mangaViewModel.updateLibraryFromRep() }
            binding.swipeRefreshMangaLibrary.isRefreshing = false
            mangaViewModel.testUpdate()
        }
    }

    private fun setupRecyclerView() {
        val adapter = MangaLibraryAdapter()

        binding.rvMangaLibrary.apply {
            this.adapter = adapter
            this.layoutManager = GridLayoutManager(context, 3)
            this.setHasFixedSize(true)
            itemAnimator = DefaultItemAnimator()
        }

        mangaViewModel.dummyData.observe(viewLifecycleOwner, Observer {
            binding.tvNoTitlesAdded.isVisible = it.isEmpty()
            adapter.setAdapterList(it)
        })
    }
}
