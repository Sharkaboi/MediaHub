package com.cybershark.mediahub.ui.manga.views

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.cybershark.mediahub.R
import com.cybershark.mediahub.ui.manga.adapters.MangaPagerAdapter
import kotlinx.android.synthetic.main.fragment_manga.*

class MangaFragment : Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_manga, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupViewPager()
    }

    private fun setupViewPager() {
        vpManga.adapter=MangaPagerAdapter(childFragmentManager,2)
        tabLayoutManga.setupWithViewPager(vpManga)
    }
}
