package com.cybershark.mediahub.ui.modules.movies.adapters

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import com.cybershark.mediahub.ui.modules.movies.views.finished.MoviesFinishedFragment
import com.cybershark.mediahub.ui.modules.movies.views.watching.MoviesWatchingFragment

class MoviesPagerAdapter(fragmentManager: FragmentManager, behaviour: Int) :
    FragmentPagerAdapter(fragmentManager, behaviour) {
    
    override fun getItem(position: Int): Fragment {
        return when (position) {
            0 -> MoviesWatchingFragment()
            else -> MoviesFinishedFragment()
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