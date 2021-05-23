package com.sharkaboi.mediahub.modules.anime_search.adapters

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import coil.load
import coil.transform.RoundedCornersTransformation
import com.sharkaboi.mediahub.R
import com.sharkaboi.mediahub.common.data.api.models.anime.AnimeSearchResponse
import com.sharkaboi.mediahub.common.extensions.roundOfString
import com.sharkaboi.mediahub.databinding.AnimeListItemBinding

class AnimeSearchListAdapter(
    private val onItemClick: (id: Int) -> Unit
) : PagingDataAdapter<AnimeSearchResponse.Data, AnimeSearchListAdapter.AnimeSearchListViewHolder>(
    AnimeSearchListItemComparator
) {

    inner class AnimeSearchListViewHolder(private val animeListItemBinding: AnimeListItemBinding) :
        RecyclerView.ViewHolder(animeListItemBinding.root) {
        fun bind(item: AnimeSearchResponse.Data?) {
            item?.let {
                animeListItemBinding.apply {
                    ivAnimeBanner.load(it.node.mainPicture?.large ?: it.node.mainPicture?.medium) {
                        crossfade(true)
                        placeholder(R.drawable.ic_anime_placeholder)
                        error(R.drawable.ic_anime_placeholder)
                        transformations(RoundedCornersTransformation(topLeft = 8f, topRight = 8f))
                    }
                    tvAnimeName.text = it.node.title
                    tvEpisodesWatched.isVisible = false
                    tvScore.text = ("★ ${it.node.meanScore?.roundOfString() ?: "0"}")
                    root.setOnClickListener {
                        onItemClick(item.node.id)
                    }
                }
            }
        }
    }

    override fun onBindViewHolder(holder: AnimeSearchListViewHolder, position: Int) {
        holder.bind(getItem(position))
        Log.d(TAG, getItem(position).toString())
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AnimeSearchListViewHolder {
        return AnimeSearchListViewHolder(
            AnimeListItemBinding.inflate(
                LayoutInflater.from(parent.context), parent, false
            )
        )
    }

    object AnimeSearchListItemComparator : DiffUtil.ItemCallback<AnimeSearchResponse.Data>() {
        override fun areItemsTheSame(
            oldItem: AnimeSearchResponse.Data,
            newItem: AnimeSearchResponse.Data
        ): Boolean {
            return oldItem.node.id == newItem.node.id
        }

        override fun areContentsTheSame(
            oldItem: AnimeSearchResponse.Data,
            newItem: AnimeSearchResponse.Data
        ): Boolean {
            return oldItem == newItem
        }
    }

    companion object {
        private const val TAG = "AnimeSearchListAdapter"
    }
}