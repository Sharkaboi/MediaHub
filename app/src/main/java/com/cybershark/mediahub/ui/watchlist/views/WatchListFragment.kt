package com.cybershark.mediahub.ui.watchlist.views

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import com.cybershark.mediahub.R
import com.cybershark.mediahub.ui.watchlist.adapters.WatchListAdapter
import com.cybershark.mediahub.ui.watchlist.viewmodel.WatchListViewModel
import kotlinx.android.synthetic.main.fragment_watchlist.*

class WatchListFragment : Fragment() {

    private lateinit var watchListViewModel: WatchListViewModel

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        watchListViewModel = ViewModelProviders.of(this).get(WatchListViewModel::class.java)
        return inflater.inflate(R.layout.fragment_watchlist, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        rvWatchlist.layoutManager=LinearLayoutManager(requireContext())
        val adapter=WatchListAdapter()
        watchListViewModel.watchListDummy.observe(viewLifecycleOwner, Observer {
            adapter.itemList=it
        })
        rvWatchlist.adapter=adapter
    }
}
