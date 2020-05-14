package com.cybershark.mediahub.ui.stats.views

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.cybershark.mediahub.R
import com.cybershark.mediahub.ui.stats.viewmodels.StatsViewModel

class StatsFragment : Fragment() {

    private lateinit var statsViewModel: StatsViewModel

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        statsViewModel = ViewModelProviders.of(this).get(StatsViewModel::class.java)
        val rootView = inflater.inflate(R.layout.fragment_stats, container, false)
        val textView: TextView = rootView.findViewById(R.id.text_stats)
        statsViewModel.text.observe(viewLifecycleOwner, Observer {
            textView.text = it
        })
        return rootView
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

    }
}
