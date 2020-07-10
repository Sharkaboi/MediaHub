package com.cybershark.mediahub.ui.modules.manga.views

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.cybershark.mediahub.R
import com.cybershark.mediahub.ui.modules.manga.adapters.MangaPagerAdapter
import com.cybershark.mediahub.ui.modules.manga.views.addmanga.AddMangaDialog
import kotlinx.android.synthetic.main.fragment_manga.*

class MangaFragment : Fragment() {

    private val navController by lazy { findNavController() }
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_manga, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupViewPager()
        fabAddManga.setOnClickListener { openMangaAddDialog() }
    }

    private fun openMangaAddDialog() {
        //Snackbar.make(fabAddManga,"Add Manga Dialog",Snackbar.LENGTH_SHORT).show()
        //TODO("Not yet implemented")
        AddMangaDialog().show(childFragmentManager,"mangaSearchDialog")
    }

    private fun setupViewPager() {
        vpManga.adapter=MangaPagerAdapter(childFragmentManager,2)
        tabLayoutManga.setupWithViewPager(vpManga)
    }
}
