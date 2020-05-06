package com.cybershark.mediahub.ui.series

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.cybershark.mediahub.R

class SeriesFragment : Fragment() {

    private lateinit var seriesViewModel: SeriesViewModel

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        seriesViewModel = ViewModelProviders.of(this).get(SeriesViewModel::class.java)
        val rootView = inflater.inflate(R.layout.fragment_series, container, false)
        val textView: TextView = rootView.findViewById(R.id.text_series)
        seriesViewModel.text.observe(viewLifecycleOwner, Observer {
            textView.text = it
        })
        return rootView
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

    }
}
