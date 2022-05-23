package com.sharkaboi.mediahub.modules.anime_suggestions.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.sharkaboi.mediahub.common.constants.UIConstants
import com.sharkaboi.mediahub.common.extensions.getRatingStringWithRating
import com.sharkaboi.mediahub.data.api.models.anime.AnimeSuggestionsResponse
import com.sharkaboi.mediahub.databinding.AnimeListItemBinding

class AnimeSuggestionsAdapter(
    private val onItemClick: (id: Int) -> Unit
) : PagingDataAdapter<AnimeSuggestionsResponse.Data, AnimeSuggestionsAdapter.AnimeSuggestionsListViewHolder>(
    AnimeSuggestionsListItemComparator
) {

    inner class AnimeSuggestionsListViewHolder(private val animeListItemBinding: AnimeListItemBinding) :
        RecyclerView.ViewHolder(animeListItemBinding.root) {
        fun bind(item: AnimeSuggestionsResponse.Data?) {
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

    override fun onBindViewHolder(holder: AnimeSuggestionsListViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): AnimeSuggestionsListViewHolder {
        return AnimeSuggestionsListViewHolder(
            AnimeListItemBinding.inflate(
                LayoutInflater.from(parent.context), parent, false
            )
        )
    }

    object AnimeSuggestionsListItemComparator :
        DiffUtil.ItemCallback<AnimeSuggestionsResponse.Data>() {
        override fun areItemsTheSame(
            oldItem: AnimeSuggestionsResponse.Data,
            newItem: AnimeSuggestionsResponse.Data
        ): Boolean {
            return oldItem.node.id == newItem.node.id
        }

        override fun areContentsTheSame(
            oldItem: AnimeSuggestionsResponse.Data,
            newItem: AnimeSuggestionsResponse.Data
        ): Boolean {
            return oldItem == newItem
        }
    }
}
