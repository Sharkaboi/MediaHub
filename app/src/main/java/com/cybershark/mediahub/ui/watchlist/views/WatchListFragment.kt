package com.cybershark.mediahub.ui.watchlist.views

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.cybershark.mediahub.R
import com.cybershark.mediahub.ui.watchlist.adapters.WatchListAdapter
import com.cybershark.mediahub.ui.watchlist.viewmodel.WatchListViewModel
import kotlinx.android.synthetic.main.fragment_watchlist.*

class WatchListFragment : Fragment() {

    private val watchListViewModel by lazy { ViewModelProvider(this).get(WatchListViewModel::class.java) }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_watchlist, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        rvWatchlist.layoutManager = LinearLayoutManager(context)
        val adapter = WatchListAdapter()
        rvWatchlist.adapter = adapter

        watchListViewModel.watchListDummy.observe(viewLifecycleOwner, Observer {
            adapter.setItemsList(it)
        })
    }
}
