package com.sharkaboi.mediahub.common.views.image_slider

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Lifecycle
import androidx.viewpager2.adapter.FragmentStateAdapter

class ImageSliderAdapter(
    private val imagesList: List<String>,
    fm: FragmentManager,
    lifecycle: Lifecycle
) : FragmentStateAdapter(fm, lifecycle) {
    override fun getItemCount(): Int = imagesList.size

    override fun createFragment(position: Int): Fragment {
        return FullScreenImageFragment.newInstance(imagesList[position])
    }
}
