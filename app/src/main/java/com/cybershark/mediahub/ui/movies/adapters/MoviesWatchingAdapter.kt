package com.cybershark.mediahub.ui.movies.adapters

import android.transition.AutoTransition
import android.transition.TransitionManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.RatingBar
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.cybershark.mediahub.R
import com.cybershark.mediahub.data.models.MoviesModel

class MoviesWatchingAdapter :
    RecyclerView.Adapter<MoviesWatchingAdapter.MoviesWatchingViewHolder>() {

    private var itemsList = listOf<MoviesModel>()

    inner class MoviesWatchingViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val tvMovieDescription = itemView.findViewById<TextView>(R.id.tvMovieDescription)!!
        private val tvMovieName = itemView.findViewById<TextView>(R.id.tvMovieName)!!
        private val tvMovieReleaseYear = itemView.findViewById<TextView>(R.id.tvMovieReleaseYear)!!
        private val ivBanner = itemView.findViewById<ImageView>(R.id.ivBanner)!!
        private val rbMovieRating = itemView.findViewById<RatingBar>(R.id.rbMovieRating)!!
        private val tvMovieLength = itemView.findViewById<TextView>(R.id.tvMovieLength)!!
        private val ibDescription = itemView.findViewById<ImageButton>(R.id.ibDescription)!!
        private val cardView = itemView.findViewById<CardView>(R.id.cardView)!!

        fun bind(moviesModel: MoviesModel) {
            tvMovieDescription.text = moviesModel.description ?: "No description found."
            tvMovieName.text = moviesModel.name
            tvMovieReleaseYear.text = moviesModel.release_year
            Glide.with(ivBanner.context)
                .asBitmap()
                .load(moviesModel.image_url)
                .error(R.drawable.error_placeholder)
                .centerCrop()
                .transform(RoundedCorners(8))
                .into(ivBanner)
            rbMovieRating.rating = moviesModel.rating ?: 0F
            val lengthInMin = moviesModel.length_in_min
            tvMovieLength.text = ("${lengthInMin / 60} H ${lengthInMin % 60} min")
            
            ibDescription.setOnClickListener {
                when (tvMovieDescription.visibility) {
                    View.GONE -> {
                        TransitionManager.beginDelayedTransition(cardView, AutoTransition())
                        tvMovieDescription.visibility = View.VISIBLE
                        Glide.with(ibDescription.context)
                            .load(R.drawable.ic_arrow_up)
                            .into(ibDescription)
                    }
                    View.VISIBLE -> {
                        TransitionManager.beginDelayedTransition(cardView, AutoTransition())
                        tvMovieDescription.visibility = View.GONE
                        Glide.with(ibDescription.context)
                            .load(R.drawable.ic_arrow_down)
                            .into(ibDescription)
                    }
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MoviesWatchingViewHolder {
        return MoviesWatchingViewHolder(
            LayoutInflater.from(parent.context)
                .inflate(R.layout.movies_watching_item, parent, false)
        )
    }

    override fun getItemCount(): Int {
        return itemsList.size
    }

    override fun onBindViewHolder(holder: MoviesWatchingViewHolder, position: Int) {
        holder.bind(itemsList[position])
    }

    fun setItemsList(it: List<MoviesModel>) {
        itemsList = it
    }
}
