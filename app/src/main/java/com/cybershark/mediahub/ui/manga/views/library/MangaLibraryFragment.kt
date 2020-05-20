package com.cybershark.mediahub.ui.manga.views.library

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.coroutineScope
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.cybershark.mediahub.R
import com.cybershark.mediahub.ui.manga.adapters.MangaLibraryAdapter
import com.cybershark.mediahub.ui.manga.viewmodels.MangaViewModel
import kotlinx.android.synthetic.main.fragment_manga_library.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class MangaLibraryFragment : Fragment() {

    private val mangaViewModel by lazy { ViewModelProvider(this).get(MangaViewModel::class.java) }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_manga_library, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        contentLoadingScreen.visibility = View.VISIBLE

        val layoutManager = StaggeredGridLayoutManager(3, StaggeredGridLayoutManager.VERTICAL)
        layoutManager.gapStrategy = StaggeredGridLayoutManager.GAP_HANDLING_NONE
        rvMangaLibrary.layoutManager = layoutManager

        val adapter = MangaLibraryAdapter()
        rvMangaLibrary.adapter = adapter

        mangaViewModel.dummyData.observe(viewLifecycleOwner, Observer {
            tvNoTitlesAdded.visibility = if (it.isEmpty()) View.VISIBLE else View.GONE
            adapter.setAdapterList(it)
            adapter.notifyDataSetChanged()
        })

        contentLoadingScreen.visibility = View.GONE

        swipeRefresh.setOnRefreshListener{
            viewLifecycleOwner.lifecycle.coroutineScope.launch { mangaViewModel.updateLibraryFromRep() }
            swipeRefresh.isRefreshing = false
        }

        fabAddManga.setOnClickListener {
            openAddMangaFragment()
        }
    }

    private fun openAddMangaFragment() {
        //TODO("Not yet implemented")
    }
}
