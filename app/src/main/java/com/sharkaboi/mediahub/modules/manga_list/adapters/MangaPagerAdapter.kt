package com.sharkaboi.mediahub.modules.manga_list.adapters

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Lifecycle
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.sharkaboi.mediahub.data.api.enums.MangaStatus
import com.sharkaboi.mediahub.modules.manga_list.ui.MangaListByStatusFragment

class MangaPagerAdapter(fm: FragmentManager, lifecycle: Lifecycle) :
    FragmentStateAdapter(fm, lifecycle) {
    override fun getItemCount(): Int = 6

    override fun createFragment(position: Int): Fragment {
        return MangaListByStatusFragment.newInstance(MangaStatus.values()[position])
    }
}
