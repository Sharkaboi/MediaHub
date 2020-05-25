package com.cybershark.mediahub.ui.series.adapters

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import com.cybershark.mediahub.ui.series.views.finished.SeriesFinishedFragment
import com.cybershark.mediahub.ui.series.views.watching.SeriesWatchingFragment

class SeriesPagerAdapter(fragmentManager: FragmentManager, behaviour: Int) :
    FragmentPagerAdapter(fragmentManager, behaviour) {

    override fun getItem(position: Int): Fragment {
        return when (position) {
            0 -> SeriesWatchingFragment()
            else -> SeriesFinishedFragment()
        }
    }

    override fun getCount(): Int {
        return 2
    }

    override fun getPageTitle(position: Int): CharSequence? {
        return when (position) {
            0 -> "Watching"
            else -> "Finished"
        }
    }
}