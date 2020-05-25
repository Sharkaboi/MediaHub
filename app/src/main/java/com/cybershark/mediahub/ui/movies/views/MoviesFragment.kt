package com.cybershark.mediahub.ui.movies.views

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.cybershark.mediahub.R
import com.cybershark.mediahub.ui.movies.adapters.MoviesPagerAdapter
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.fragment_movies.*

class MoviesFragment : Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_movies, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupViewPager()
        fabAddMovies.setOnClickListener { openAddMoviesDialog() }
    }

    private fun openAddMoviesDialog() {
        Snackbar.make(fabAddMovies,"Add movies dialog",Snackbar.LENGTH_SHORT).show()
        //TODO("Not yet implemented")
    }

    private fun setupViewPager() {
        vpMovies.adapter = MoviesPagerAdapter(childFragmentManager, 2)
        tabLayoutMovies.setupWithViewPager(vpMovies)
    }
}
