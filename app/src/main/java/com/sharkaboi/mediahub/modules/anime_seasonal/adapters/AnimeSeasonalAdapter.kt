package com.sharkaboi.mediahub.modules.anime_seasonal.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.sharkaboi.mediahub.common.constants.UIConstants
import com.sharkaboi.mediahub.common.extensions.getRatingStringWithRating
import com.sharkaboi.mediahub.data.api.models.anime.AnimeSeasonalResponse
import com.sharkaboi.mediahub.databinding.AnimeListItemBinding

class AnimeSeasonalAdapter(
    private val onItemClick: (id: Int) -> Unit
) : PagingDataAdapter<AnimeSeasonalResponse.Data, AnimeSeasonalAdapter.AnimeSeasonalListViewHolder>(
    AnimeSeasonalListItemComparator
) {

    inner class AnimeSeasonalListViewHolder(private val animeListItemBinding: AnimeListItemBinding) :
        RecyclerView.ViewHolder(animeListItemBinding.root) {
        fun bind(item: AnimeSeasonalResponse.Data?) {
            item?.let {
                animeListItemBinding.apply {
                    ivAnimeBanner.load(
                        it.node.mainPicture?.large ?: it.node.mainPicture?.medium,
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

    override fun onBindViewHolder(holder: AnimeSeasonalListViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AnimeSeasonalListViewHolder {
        return AnimeSeasonalListViewHolder(
            AnimeListItemBinding.inflate(
                LayoutInflater.from(parent.context), parent, false
            )
        )
    }

    object AnimeSeasonalListItemComparator : DiffUtil.ItemCallback<AnimeSeasonalResponse.Data>() {
        override fun areItemsTheSame(
            oldItem: AnimeSeasonalResponse.Data,
            newItem: AnimeSeasonalResponse.Data
        ): Boolean {
            return oldItem.node.id == newItem.node.id
        }

        override fun areContentsTheSame(
            oldItem: AnimeSeasonalResponse.Data,
            newItem: AnimeSeasonalResponse.Data
        ): Boolean {
            return oldItem == newItem
        }
    }
}
