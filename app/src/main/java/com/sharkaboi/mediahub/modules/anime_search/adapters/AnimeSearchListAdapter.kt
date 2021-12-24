package com.sharkaboi.mediahub.modules.anime_search.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.sharkaboi.mediahub.common.constants.UIConstants
import com.sharkaboi.mediahub.common.extensions.getRatingStringWithRating
import com.sharkaboi.mediahub.data.api.models.anime.AnimeSearchResponse
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
                    ivAnimeBanner.load(
                        uri = it.node.mainPicture?.large ?: it.node.mainPicture?.medium,
                        builder = UIConstants.TopRoundedAnimeImageBuilder
                    )
                    tvAnimeName.text = it.node.title
                    tvEpisodesWatched.isVisible = false
                    tvScore.text = tvScore.context.getRatingStringWithRating(it.node.meanScore)
                    root.setOnClickListener {
                        onItemClick(item.node.id)
                    }
                }
            }
        }
    }

    override fun onBindViewHolder(holder: AnimeSearchListViewHolder, position: Int) {
        holder.bind(getItem(position))
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
}
