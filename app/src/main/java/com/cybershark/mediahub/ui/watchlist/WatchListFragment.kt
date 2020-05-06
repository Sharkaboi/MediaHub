package com.cybershark.mediahub.ui.watchlist

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.cybershark.mediahub.R

class WatchListFragment : Fragment() {

    private lateinit var watchListViewModel: WatchListViewModel

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        watchListViewModel = ViewModelProviders.of(this).get(WatchListViewModel::class.java)
        val rootView = inflater.inflate(R.layout.fragment_watchlist, container, false)
        val textView: TextView = rootView.findViewById(R.id.text_watchlist)
        watchListViewModel.text.observe(viewLifecycleOwner, Observer {
            textView.text = it
        })
        return rootView
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

    }
}
