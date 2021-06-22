package com.sharkaboi.mediahub.modules.anime_suggestions.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import coil.load
import coil.transform.RoundedCornersTransformation
import com.sharkaboi.mediahub.R
import com.sharkaboi.mediahub.common.extensions.roundOfString
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
                    ivAnimeBanner.load(it.node.mainPicture?.large ?: it.node.mainPicture?.medium) {
                        crossfade(true)
                        placeholder(R.drawable.ic_anime_placeholder)
                        error(R.drawable.ic_anime_placeholder)
                        fallback(R.drawable.ic_anime_placeholder)
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

    companion object {
        private const val TAG = "AnimeSuggestionsAdapter"
    }
}