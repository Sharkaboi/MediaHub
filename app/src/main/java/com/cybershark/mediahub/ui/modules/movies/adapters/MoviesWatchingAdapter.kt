package com.cybershark.mediahub.ui.modules.movies.adapters

import android.transition.AutoTransition
import android.transition.TransitionManager
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.view.isGone
import androidx.core.view.isVisible
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.RecyclerView
import coil.load
import coil.transform.RoundedCornersTransformation
import com.cybershark.mediahub.R
import com.cybershark.mediahub.data.room.entities.MoviesModel
import com.cybershark.mediahub.databinding.MoviesWatchingItemBinding
import com.cybershark.mediahub.ui.modules.movies.util.MovieItemDiffUtilCallback

class MoviesWatchingAdapter : RecyclerView.Adapter<MoviesWatchingAdapter.MoviesWatchingViewHolder>() {

    private lateinit var binding: MoviesWatchingItemBinding

    class MoviesWatchingViewHolder(private val binding: MoviesWatchingItemBinding) : RecyclerView.ViewHolder(binding.root) {

        fun bind(moviesModel: MoviesModel) {
            binding.tvMovieDescription.text = moviesModel.description ?: "No description found."
            binding.tvMovieName.text = moviesModel.name
            binding.tvMovieReleaseYear.text = moviesModel.release_year
            binding.ivBanner.load(moviesModel.image_url) {
                error(R.drawable.error_placeholder)
                transformations(RoundedCornersTransformation(8f))
            }
            binding.rbMovieRating.rating = moviesModel.rating ?: 0F
            val lengthInMin = moviesModel.length_in_min
            binding.tvMovieLength.text = ("${lengthInMin / 60} H ${lengthInMin % 60} min")

            binding.ibDescription.setOnClickListener {
                if (binding.tvMovieDescription.isGone) {
                    TransitionManager.beginDelayedTransition(binding.cardView, AutoTransition())
                    binding.tvMovieDescription.isVisible = true
                    binding.ibDescription.load(R.drawable.ic_arrow_up)
                } else {
                    TransitionManager.beginDelayedTransition(binding.cardView, AutoTransition())
                    binding.tvMovieDescription.isGone = true
                    binding.ivBanner.load(R.drawable.ic_arrow_down)
                }
            }

        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MoviesWatchingViewHolder {
        binding = MoviesWatchingItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MoviesWatchingViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return listDiffer.currentList.size
    }

    override fun onBindViewHolder(holder: MoviesWatchingViewHolder, position: Int) {
        holder.bind(listDiffer.currentList[position])
    }

    fun setItemsList(list: List<MoviesModel>) {
        listDiffer.submitList(list)
    }

    private val listDiffer = AsyncListDiffer(this, MovieItemDiffUtilCallback)
}
