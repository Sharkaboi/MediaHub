package com.sharkaboi.mediahub.modules.manga_list.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.sharkaboi.mediahub.common.constants.UIConstants
import com.sharkaboi.mediahub.common.extensions.getProgressStringWith
import com.sharkaboi.mediahub.common.extensions.getRatingStringWithRating
import com.sharkaboi.mediahub.data.api.models.usermanga.UserMangaListResponse
import com.sharkaboi.mediahub.databinding.MangaListItemBinding

class MangaListAdapter(
    private val onItemClick: (id: Int) -> Unit
) : PagingDataAdapter<UserMangaListResponse.Data, MangaListAdapter.MangaListViewHolder>(
    MangaListItemComparator
) {

    inner class MangaListViewHolder(private val mangaListItemBinding: MangaListItemBinding) :
        RecyclerView.ViewHolder(mangaListItemBinding.root) {
        fun bind(item: UserMangaListResponse.Data?) {
            item?.let {
                mangaListItemBinding.apply {
                    ivMangaBanner.load(
                        uri = it.node.mainPicture?.large ?: it.node.mainPicture?.medium,
                        builder = UIConstants.TopRoundedMangaImageBuilder
                    )
                    tvMangaName.text = it.node.title
                    tvChapsRead.text = tvChapsRead.context.getProgressStringWith(
                        it.listStatus.numChaptersRead,
                        it.node.numChapters
                    )
                    tvVolumesRead.text = tvVolumesRead.context.getProgressStringWith(
                        it.listStatus.numVolumesRead,
                        it.node.numVolumes
                    )
                    tvScore.text = tvScore.context.getRatingStringWithRating(it.listStatus.score)
                    root.setOnClickListener {
                        onItemClick(item.node.id)
                    }
                }
            }
        }
    }

    override fun onBindViewHolder(holder: MangaListViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MangaListViewHolder {
        return MangaListViewHolder(
            MangaListItemBinding.inflate(
                LayoutInflater.from(parent.context), parent, false
            )
        )
    }

    object MangaListItemComparator : DiffUtil.ItemCallback<UserMangaListResponse.Data>() {
        override fun areItemsTheSame(
            oldItem: UserMangaListResponse.Data,
            newItem: UserMangaListResponse.Data
        ): Boolean {
            return oldItem.node.id == newItem.node.id
        }

        override fun areContentsTheSame(
            oldItem: UserMangaListResponse.Data,
            newItem: UserMangaListResponse.Data
        ): Boolean {
            return oldItem == newItem
        }
    }
}
