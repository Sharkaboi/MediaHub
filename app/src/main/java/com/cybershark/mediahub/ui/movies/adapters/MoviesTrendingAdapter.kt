package com.cybershark.mediahub.ui.movies.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.cybershark.mediahub.R
import com.cybershark.mediahub.data.models.MoviesModel

class MoviesTrendingAdapter :
    RecyclerView.Adapter<MoviesTrendingAdapter.MoviesTrendingViewModel>() {

    private var itemsList = listOf<MoviesModel>()

    inner class MoviesTrendingViewModel(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val ivMoviePoster = itemView.findViewById<ImageView>(R.id.ivMoviePoster)!!
        private val tvMovieName = itemView.findViewById<TextView>(R.id.tvMovieName)!!
        
        fun bind(moviesModel: MoviesModel){
            Glide.with(ivMoviePoster.context).asBitmap().load(moviesModel.image_url)
                .error(R.drawable.error_placeholder).into(ivMoviePoster)
            tvMovieName.text = moviesModel.name
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MoviesTrendingViewModel {
        return MoviesTrendingViewModel(LayoutInflater.from(parent.context).inflate(R.layout.movie_trending_item, parent, false))
    }

    override fun getItemCount(): Int {
        return itemsList.size
    }

    override fun onBindViewHolder(holder: MoviesTrendingViewModel, position: Int) {
        holder.bind(itemsList[position])
    }

    fun setItemsList(it: List<MoviesModel>) {
        itemsList = it
    }
}
