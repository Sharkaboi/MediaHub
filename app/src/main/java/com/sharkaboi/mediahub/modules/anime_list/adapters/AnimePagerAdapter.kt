package com.sharkaboi.mediahub.modules.anime_list.adapters

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Lifecycle
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.sharkaboi.mediahub.data.api.enums.AnimeStatus
import com.sharkaboi.mediahub.modules.anime_list.ui.AnimeListByStatusFragment

class AnimePagerAdapter(fm: FragmentManager, lifecycle: Lifecycle) :
    FragmentStateAdapter(fm, lifecycle) {
    override fun getItemCount(): Int = AnimeStatus.values().count()

    override fun createFragment(position: Int): Fragment {
        return AnimeListByStatusFragment()
    }
}
