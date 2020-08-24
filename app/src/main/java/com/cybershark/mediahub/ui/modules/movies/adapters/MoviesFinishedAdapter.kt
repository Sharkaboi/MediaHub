package com.cybershark.mediahub.ui.modules.movies.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.cybershark.mediahub.R
import com.cybershark.mediahub.data.models.entities.MoviesModel
import com.cybershark.mediahub.databinding.MovieFinishedItemBinding
import com.cybershark.mediahub.ui.modules.movies.util.MovieItemDiffUtilCallback

class MoviesFinishedAdapter : RecyclerView.Adapter<MoviesFinishedAdapter.MoviesFinishedViewHolder>() {

    private lateinit var binding: MovieFinishedItemBinding

    class MoviesFinishedViewHolder(private val binding: MovieFinishedItemBinding) : RecyclerView.ViewHolder(binding.root) {

        fun bind(moviesModel: MoviesModel) {
            binding.ivMoviePoster.load(moviesModel.image_url){
                error(R.drawable.error_placeholder)
            }
            binding.tvMovieName.text = moviesModel.name
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MoviesFinishedViewHolder {
        binding = MovieFinishedItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MoviesFinishedViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return listDiffer.currentList.size
    }

    override fun onBindViewHolder(holder: MoviesFinishedViewHolder, position: Int) {
        holder.bind(listDiffer.currentList[position])
    }

    fun setItemsList(list: List<MoviesModel>) {
        listDiffer.submitList(list)
    }

    private val listDiffer = AsyncListDiffer(this, MovieItemDiffUtilCallback)
}
