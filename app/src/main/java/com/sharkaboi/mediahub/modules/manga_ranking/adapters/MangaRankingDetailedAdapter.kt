package com.sharkaboi.mediahub.modules.manga_ranking.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import coil.load
import coil.transform.RoundedCornersTransformation
import com.sharkaboi.mediahub.R
import com.sharkaboi.mediahub.data.api.models.manga.MangaRankingResponse
import com.sharkaboi.mediahub.common.extensions.roundOfString
import com.sharkaboi.mediahub.databinding.MangaListItemBinding

class MangaRankingDetailedAdapter(
    private val onItemClick: (id: Int) -> Unit
) : PagingDataAdapter<MangaRankingResponse.Data, MangaRankingDetailedAdapter.MangaRankingListViewHolder>(
    MangaRankingListItemComparator
) {

    inner class MangaRankingListViewHolder(private val mangaListItemBinding: MangaListItemBinding) :
        RecyclerView.ViewHolder(mangaListItemBinding.root) {
        fun bind(item: MangaRankingResponse.Data?) {
            item?.let {
                mangaListItemBinding.apply {
                    ivMangaBanner.load(it.node.mainPicture?.large ?: it.node.mainPicture?.medium) {
                        crossfade(true)
                        placeholder(R.drawable.ic_manga_placeholder)
                        error(R.drawable.ic_manga_placeholder)
                        transformations(RoundedCornersTransformation(topLeft = 8f, topRight = 8f))
                    }
                    tvMangaName.text = it.node.title
                    tvChapsRead.text = ("Rank : ${it.ranking.rank}")
                    tvVolumesRead.isVisible = false
                    tvScore.text = ("★ ${it.node.meanScore?.roundOfString() ?: "0"}")
                    root.setOnClickListener {
                        onItemClick(item.node.id)
                    }
                }
            }
        }
    }

    override fun onBindViewHolder(holder: MangaRankingListViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MangaRankingListViewHolder {
        return MangaRankingListViewHolder(
            MangaListItemBinding.inflate(
                LayoutInflater.from(parent.context), parent, false
            )
        )
    }

    object MangaRankingListItemComparator : DiffUtil.ItemCallback<MangaRankingResponse.Data>() {
        override fun areItemsTheSame(
            oldItem: MangaRankingResponse.Data,
            newItem: MangaRankingResponse.Data
        ): Boolean {
            return oldItem.node.id == newItem.node.id
        }

        override fun areContentsTheSame(
            oldItem: MangaRankingResponse.Data,
            newItem: MangaRankingResponse.Data
        ): Boolean {
            return oldItem == newItem
        }
    }

    companion object {
        private const val TAG = "MangaRankingDetailedAdapter"
    }
}