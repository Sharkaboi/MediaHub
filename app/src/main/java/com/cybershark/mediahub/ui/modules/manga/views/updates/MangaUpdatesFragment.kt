package com.cybershark.mediahub.ui.modules.manga.views.updates

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.coroutineScope
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.cybershark.mediahub.R
import com.cybershark.mediahub.ui.modules.manga.adapters.MangaUpdatesAdapter
import com.cybershark.mediahub.ui.modules.manga.viewmodels.MangaViewModel
import kotlinx.android.synthetic.main.fragment_manga_updates.*
import kotlinx.android.synthetic.main.fragment_manga_updates.contentLoadingScreen
import kotlinx.coroutines.launch

class MangaUpdatesFragment : Fragment() {

    private val mangaViewModel by lazy { ViewModelProvider(this).get(MangaViewModel::class.java) }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_manga_updates, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        contentLoadingScreen.visibility = View.VISIBLE

        val adapter = MangaUpdatesAdapter()

        rvMangaUpdates.apply {
            this.adapter = adapter
            this.layoutManager = LinearLayoutManager(context)
            this.addItemDecoration(DividerItemDecoration(context, LinearLayoutManager.VERTICAL))
            this.setHasFixedSize(true)
            itemAnimator = DefaultItemAnimator()
        }

        mangaViewModel.dummyData.observe(viewLifecycleOwner, Observer {
            tvNoChapters.visibility = if (it.isEmpty()) View.VISIBLE else View.GONE
            adapter.setAdapterList(it)
            //adapter.notifyDataSetChanged()
        })

        contentLoadingScreen.visibility = View.GONE

        swipeRefreshMangaUpdates.setOnRefreshListener{
            viewLifecycleOwner.lifecycle.coroutineScope.launch { mangaViewModel.updateNewChaptersFromRep() }
            mangaViewModel.testUpdate()
            swipeRefreshMangaUpdates.isRefreshing = false
        }
    }
}
