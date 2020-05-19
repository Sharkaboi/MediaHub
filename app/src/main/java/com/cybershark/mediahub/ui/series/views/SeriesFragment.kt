package com.cybershark.mediahub.ui.series.views

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.cybershark.mediahub.R
import com.cybershark.mediahub.ui.series.adapters.SeriesPagerAdapter
import kotlinx.android.synthetic.main.fragment_series.*

class SeriesFragment : Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_series, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupViewPager()
    }

    private fun setupViewPager() {
        vpSeries.adapter = SeriesPagerAdapter(childFragmentManager, 3)
        tabLayoutSeries.setupWithViewPager(vpSeries)
    }
}
