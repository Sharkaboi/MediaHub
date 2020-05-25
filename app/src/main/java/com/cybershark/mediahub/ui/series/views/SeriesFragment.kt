package com.cybershark.mediahub.ui.series.views

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.cybershark.mediahub.R
import com.cybershark.mediahub.ui.series.adapters.SeriesPagerAdapter
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.fragment_series.*

class SeriesFragment : Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_series, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupViewPager()
        fabAddSeries.setOnClickListener { openSeriesDialog() }
    }

    private fun openSeriesDialog() {
        Snackbar.make(fabAddSeries,"Add series dialog",Snackbar.LENGTH_SHORT).show()
        //TODO("Not yet implemented")
    }

    private fun setupViewPager() {
        vpSeries.adapter = SeriesPagerAdapter(childFragmentManager, 2)
        tabLayoutSeries.setupWithViewPager(vpSeries)
    }
}
