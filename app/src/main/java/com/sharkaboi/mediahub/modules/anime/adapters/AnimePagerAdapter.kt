package com.sharkaboi.mediahub.modules.anime.adapters

import android.util.Log
import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.sharkaboi.mediahub.common.data.api.enums.AnimeStatus
import com.sharkaboi.mediahub.modules.anime.ui.AnimeListByStatusFragment

class AnimePagerAdapter(fragment: Fragment) : FragmentStateAdapter(fragment) {
    override fun getItemCount(): Int = 6

    override fun createFragment(position: Int): Fragment {
        Log.d(TAG, "createFragment position : $position")
        return AnimeListByStatusFragment.newInstance(AnimeStatus.values()[position])
    }

    companion object {
        private const val TAG = "AnimePagerAdapter"
    }
}