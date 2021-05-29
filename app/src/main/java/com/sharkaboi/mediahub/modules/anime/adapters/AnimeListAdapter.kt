package com.sharkaboi.mediahub.modules.anime.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import coil.load
import coil.transform.RoundedCornersTransformation
import com.sharkaboi.mediahub.R
import com.sharkaboi.mediahub.common.data.api.models.useranime.UserAnimeListResponse
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
                    ivAnimeBanner.load(it.node.mainPicture?.large ?: it.node.mainPicture?.medium) {
                        crossfade(true)
                        placeholder(R.drawable.ic_anime_placeholder)
                        error(R.drawable.ic_anime_placeholder)
                        transformations(RoundedCornersTransformation(topLeft = 8f, topRight = 8f))
                    }
                    tvAnimeName.text = it.node.title
                    val numEpisodes =
                        if (it.node.numTotalEpisodes == 0) "??" else it.node.numTotalEpisodes
                    tvEpisodesWatched.text =
                        ("${it.listStatus.numWatchedEpisodes}/${numEpisodes}")
                    tvScore.text = ("★ ${it.listStatus.score}")
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

    companion object {
        private const val TAG = "AnimeListAdapter"
    }
}