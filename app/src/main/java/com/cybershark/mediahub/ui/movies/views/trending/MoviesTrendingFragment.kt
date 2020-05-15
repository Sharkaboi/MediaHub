package com.cybershark.mediahub.ui.movies.views.trending

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.cybershark.mediahub.R
import com.cybershark.mediahub.ui.movies.adapters.MoviesTrendingAdapter
import com.cybershark.mediahub.ui.movies.viewmodel.MoviesViewModel
import kotlinx.android.synthetic.main.fragment_movies_trending.*

class MoviesTrendingFragment : Fragment() {

    private lateinit var moviesViewModel: MoviesViewModel

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        moviesViewModel=ViewModelProvider(this).get(MoviesViewModel::class.java)
        return inflater.inflate(R.layout.fragment_movies_trending,container,false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        rvTrendingMovies.layoutManager=LinearLayoutManager(context,LinearLayoutManager.HORIZONTAL,false)
        val adapter= MoviesTrendingAdapter()
        moviesViewModel.dummyMoviesData.observe(viewLifecycleOwner, Observer {
            adapter.setItemsList(it)
        })
        rvTrendingMovies.adapter=adapter
    }
}
