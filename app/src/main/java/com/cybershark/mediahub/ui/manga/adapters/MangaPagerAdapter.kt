package com.cybershark.mediahub.ui.manga.adapters

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import com.cybershark.mediahub.ui.manga.views.library.MangaLibraryFragment
import com.cybershark.mediahub.ui.manga.views.updates.MangaUpdatesFragment

class MangaPagerAdapter(fragmentManager: FragmentManager, behaviour: Int) : FragmentPagerAdapter(fragmentManager,behaviour) {
    override fun getItem(position: Int): Fragment {
        return when(position){
            0-> MangaUpdatesFragment()
            else-> MangaLibraryFragment()
        }
    }

    override fun getCount(): Int {
        return 2
    }

    override fun getPageTitle(position: Int): CharSequence? {
        return when(position){
            0 -> "Updates"
            else -> "Library"
        }
    }
}