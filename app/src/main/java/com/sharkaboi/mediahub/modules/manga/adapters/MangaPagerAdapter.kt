package com.sharkaboi.mediahub.modules.manga.adapters

import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.sharkaboi.mediahub.common.data.api.enums.MangaStatus
import com.sharkaboi.mediahub.modules.manga.ui.MangaListByStatusFragment

class MangaPagerAdapter(fragment: Fragment) : FragmentStateAdapter(fragment) {
    override fun getItemCount(): Int = 6

    override fun createFragment(position: Int): Fragment {
        return MangaListByStatusFragment.newInstance(MangaStatus.values()[position])
    }
}