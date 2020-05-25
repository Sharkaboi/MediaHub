package com.cybershark.mediahub.ui.movies.views.finished

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.coroutineScope
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.cybershark.mediahub.R
import com.cybershark.mediahub.ui.movies.adapters.MoviesFinishedAdapter
import com.cybershark.mediahub.ui.movies.viewmodel.MoviesViewModel
import kotlinx.android.synthetic.main.fragment_manga_library.*
import kotlinx.android.synthetic.main.fragment_movies_finished.*
import kotlinx.android.synthetic.main.fragment_movies_finished.contentLoadingScreen
import kotlinx.coroutines.launch

class MoviesFinishedFragment : Fragment() {

    private val moviesViewModel by lazy { ViewModelProvider(this).get(MoviesViewModel::class.java) }
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_movies_finished, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        contentLoadingScreen.visibility = View.VISIBLE

        val adapter = MoviesFinishedAdapter()

        rvMoviesFinished.apply {
            this.adapter = adapter
            layoutManager = GridLayoutManager(context, 3, GridLayoutManager.VERTICAL, false)
            setHasFixedSize(true)
        }

        moviesViewModel.dummyMoviesData.observe(viewLifecycleOwner, Observer {
            tvNoMoviesWatched.visibility = if (it.isEmpty()) View.VISIBLE else View.GONE
            adapter.setItemsList(it)
            adapter.notifyDataSetChanged()
        })

        contentLoadingScreen.visibility = View.GONE

        swipeRefreshMoviesFinished.setOnRefreshListener{
            viewLifecycleOwner.lifecycle.coroutineScope.launch { moviesViewModel.updateMoviesWatchedFromRep() }
            swipeRefreshMoviesFinished.isRefreshing = false
        }
    }
}