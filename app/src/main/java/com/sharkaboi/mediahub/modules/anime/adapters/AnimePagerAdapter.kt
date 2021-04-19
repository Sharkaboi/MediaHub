package com.sharkaboi.mediahub.modules.anime.adapters

import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.sharkaboi.mediahub.common.data.api.enums.AnimeStatus
import com.sharkaboi.mediahub.modules.anime.ui.AnimeListByStatusFragment

class AnimePagerAdapter(fragment: Fragment) : FragmentStateAdapter(fragment) {
    override fun getItemCount(): Int = 6

    override fun createFragment(position: Int): Fragment {
        return AnimeListByStatusFragment.newInstance(AnimeStatus.values()[position])
    }

}