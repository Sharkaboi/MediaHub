package com.cybershark.mediahub.ui.movies.adapters

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import com.cybershark.mediahub.ui.movies.views.finished.MoviesFinishedFragment
import com.cybershark.mediahub.ui.movies.views.trending.MoviesTrendingFragment
import com.cybershark.mediahub.ui.movies.views.watching.MoviesWatchingFragment

class MoviesPagerAdapter(fragmentManager: FragmentManager, behaviour: Int) : FragmentPagerAdapter(fragmentManager, behaviour) {
    override fun getItem(position: Int): Fragment {
        return when (position) {
            0 -> MoviesWatchingFragment()
            1 -> MoviesFinishedFragment()
            else -> MoviesTrendingFragment()
        }
    }

    override fun getCount(): Int {
        return 3
    }

    override fun getPageTitle(position: Int): CharSequence? {
        return when(position){
            0->"Watching"
            1->"Finished"
            else->"Trending"
        }
    }
}