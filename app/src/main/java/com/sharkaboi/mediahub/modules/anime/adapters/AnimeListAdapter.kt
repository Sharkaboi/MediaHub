package com.sharkaboi.mediahub.modules.anime.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.sharkaboi.mediahub.common.constants.UIConstants
import com.sharkaboi.mediahub.common.extensions.getProgressStringWith
import com.sharkaboi.mediahub.common.extensions.getRatingStringWithRating
import com.sharkaboi.mediahub.data.api.models.useranime.UserAnimeListResponse
import com.sharkaboi.mediahub.databinding.AnimeListItemBinding

class AnimeListAdapter(
    private val onItemClick: (id: Int) -> Unit
) : PagingDataAdapter<UserAnimeListResponse.Data, AnimeListAdapter.AnimeListViewHolder>(
    AnimeListItemComparator
) {

    inner class AnimeListViewHolder(private val animeListItemBinding: AnimeListItemBinding) :
        RecyclerView.ViewHolder(animeListItemBinding.root) {
        fun bind(item: UserAnimeListResponse.Data?) {
            item?.let {
                animeListItemBinding.apply {
                    ivAnimeBanner.load(
                        uri = it.node.mainPicture?.large ?: it.node.mainPicture?.medium,
                        builder = UIConstants.AnimeImageBuilder
                    )
                    tvAnimeName.text = it.node.title
                    tvEpisodesWatched.text = tvEpisodesWatched.context.getProgressStringWith(
                        it.listStatus.numWatchedEpisodes,
                        it.node.numTotalEpisodes
                    )
                    tvScore.text = tvScore.context.getRatingStringWithRating(it.listStatus.score)
                    root.setOnClickListener {
                        onItemClick(item.node.id)
                    }
                }
            }
        }
    }

    override fun onBindViewHolder(holder: AnimeListViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AnimeListViewHolder {
        return AnimeListViewHolder(
            AnimeListItemBinding.inflate(
                LayoutInflater.from(parent.context), parent, false
            )
        )
    }

    object AnimeListItemComparator : DiffUtil.ItemCallback<UserAnimeListResponse.Data>() {
        override fun areItemsTheSame(
            oldItem: UserAnimeListResponse.Data,
            newItem: UserAnimeListResponse.Data
        ): Boolean {
            return oldItem.node.id == newItem.node.id
        }

        override fun areContentsTheSame(
            oldItem: UserAnimeListResponse.Data,
            newItem: UserAnimeListResponse.Data
        ): Boolean {
            return oldItem == newItem
        }
    }
}
