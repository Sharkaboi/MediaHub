package com.sharkaboi.mediahub.modules.manga.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import coil.load
import coil.transform.RoundedCornersTransformation
import com.sharkaboi.mediahub.R
import com.sharkaboi.mediahub.common.data.api.models.usermanga.UserMangaListResponse
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
                    ivMangaBanner.load(it.node.mainPicture?.large ?: it.node.mainPicture?.medium) {
                        crossfade(true)
                        placeholder(R.drawable.ic_anime_placeholder)
                        error(R.drawable.ic_anime_placeholder)
                        transformations(RoundedCornersTransformation(topLeft = 8f, topRight = 8f))
                    }
                    tvMangaName.text = it.node.title
                    val numChapters =
                        if (it.node.numChapters == 0) "??" else it.node.numChapters
                    tvChapsRead.text =
                        ("${it.listStatus.numChaptersRead}/${numChapters}")
                    val numVolumes =
                        if (it.node.numVolumes == 0) "??" else it.node.numVolumes
                    tvVolumesRead.text =
                        ("${it.listStatus.numVolumesRead}/${numVolumes}")
                    tvScore.text = ("★ ${it.listStatus.score}")
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

    companion object {
        private const val TAG = "MangaListAdapter"
    }
}