package com.cybershark.mediahub.ui.movies.views.finished

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import com.cybershark.mediahub.R
import com.cybershark.mediahub.ui.movies.adapters.MoviesFinishedAdapter
import com.cybershark.mediahub.ui.movies.viewmodel.MoviesViewModel
import kotlinx.android.synthetic.main.fragment_movies_finished.*

class MoviesFinishedFragment : Fragment() {

    private lateinit var moviesViewModel: MoviesViewModel
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        moviesViewModel=ViewModelProvider(this).get(MoviesViewModel::class.java)
        return inflater.inflate(R.layout.fragment_movies_finished, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        rvMoviesFinished.layoutManager=GridLayoutManager(context,3,GridLayoutManager.VERTICAL,false)
        val adapter= MoviesFinishedAdapter()
        moviesViewModel.dummyMoviesData.observe(viewLifecycleOwner, Observer {
            adapter.setItemsList(it)
        })
        rvMoviesFinished.adapter=adapter
    }
}