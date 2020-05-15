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

class MoviesFinishedAdapter : RecyclerView.Adapter<MoviesFinishedAdapter.MoviesFinishedViewHolder>() {

    private var itemsList= listOf<MoviesModel>()

    inner class MoviesFinishedViewHolder(itemView:View):RecyclerView.ViewHolder(itemView) {
        val ivMoviePoster=itemView.findViewById<ImageView>(R.id.ivMoviePoster)!!
        val tvMovieName=itemView.findViewById<TextView>(R.id.tvMovieName)!!
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MoviesFinishedViewHolder {
        return MoviesFinishedViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.movie_trending_item,parent,false))
    }

    override fun getItemCount(): Int {
        return itemsList.size
    }

    override fun onBindViewHolder(holder: MoviesFinishedViewHolder, position: Int) {
        holder.apply {
            Glide.with(ivMoviePoster.context).asBitmap().load(itemsList[position].image_url).error(R.drawable.error_placeholder).into(ivMoviePoster)
            tvMovieName.text=itemsList[position].name
        }
    }

    fun setItemsList(it: List<MoviesModel>) {
        itemsList=it
    }

}
