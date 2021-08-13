package com.sharkaboi.mediahub.modules.anime_ranking.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.sharkaboi.mediahub.R
import com.sharkaboi.mediahub.common.constants.UIConstants
import com.sharkaboi.mediahub.common.extensions.getRatingStringWithRating
import com.sharkaboi.mediahub.data.api.models.anime.AnimeRankingResponse
import com.sharkaboi.mediahub.databinding.AnimeListItemBinding

class AnimeRankingDetailedAdapter(
    private val onItemClick: (id: Int) -> Unit
) : PagingDataAdapter<AnimeRankingResponse.Data, AnimeRankingDetailedAdapter.AnimeRankingListViewHolder>(
    AnimeRankingListItemComparator
) {

    inner class AnimeRankingListViewHolder(private val animeListItemBinding: AnimeListItemBinding) :
        RecyclerView.ViewHolder(animeListItemBinding.root) {
        fun bind(item: AnimeRankingResponse.Data?) {
            item?.let {
                animeListItemBinding.apply {
                    ivAnimeBanner.load(
                        uri = it.node.mainPicture?.large ?: it.node.mainPicture?.medium,
                        builder = UIConstants.AnimeImageBuilder
                    )
                    tvAnimeName.text = it.node.title
                    tvEpisodesWatched.text = tvEpisodesWatched.context.getString(
                        R.string.media_rank_template,
                        it.ranking.rank
                    )
                    tvScore.text = tvScore.context.getRatingStringWithRating(it.node.meanScore)
                    root.setOnClickListener {
                        onItemClick(item.node.id)
                    }
                }
            }
        }
    }

    override fun onBindViewHolder(holder: AnimeRankingListViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AnimeRankingListViewHolder {
        return AnimeRankingListViewHolder(
            AnimeListItemBinding.inflate(
                LayoutInflater.from(parent.context), parent, false
            )
        )
    }

    object AnimeRankingListItemComparator : DiffUtil.ItemCallback<AnimeRankingResponse.Data>() {
        override fun areItemsTheSame(
            oldItem: AnimeRankingResponse.Data,
            newItem: AnimeRankingResponse.Data
        ): Boolean {
            return oldItem.node.id == newItem.node.id
        }

        override fun areContentsTheSame(
            oldItem: AnimeRankingResponse.Data,
            newItem: AnimeRankingResponse.Data
        ): Boolean {
            return oldItem == newItem
        }
    }
}
