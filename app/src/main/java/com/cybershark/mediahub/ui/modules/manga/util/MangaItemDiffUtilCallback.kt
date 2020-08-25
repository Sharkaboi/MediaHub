package com.cybershark.mediahub.ui.modules.manga.util

import androidx.recyclerview.widget.DiffUtil
import com.cybershark.mediahub.data.room.entities.MangaModel

object MangaItemDiffUtilCallback : DiffUtil.ItemCallback<MangaModel>() {

    override fun areItemsTheSame(oldItem: MangaModel, newItem: MangaModel): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: MangaModel, newItem: MangaModel): Boolean {
        return oldItem == newItem
    }
}