package com.cybershark.mediahub.ui.movies.views.watching

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.cybershark.mediahub.R
import com.cybershark.mediahub.ui.movies.adapters.MoviesWatchingAdapter
import com.cybershark.mediahub.ui.movies.viewmodel.MoviesViewModel
import kotlinx.android.synthetic.main.fragment_movies_watching.*


class MoviesWatchingFragment : Fragment() {

    private lateinit var moviesViewModel: MoviesViewModel

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        moviesViewModel= ViewModelProvider(this).get(MoviesViewModel::class.java)
        return inflater.inflate(R.layout.fragment_movies_watching,container,false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        rvMoviesWatching.layoutManager = LinearLayoutManager(context)
        val adapter= MoviesWatchingAdapter()
        moviesViewModel.dummyMoviesData.observe(viewLifecycleOwner, Observer {
            adapter.setItemsList(it)
        })
        rvMoviesWatching.adapter=adapter
    }
}
