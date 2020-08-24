package com.cybershark.mediahub.ui.modules.watchlist.util

import androidx.recyclerview.widget.DiffUtil
import com.cybershark.mediahub.data.models.entities.WatchListModel

object WatchListDiffUtilItemCallback : DiffUtil.ItemCallback<WatchListModel>() {
    override fun areItemsTheSame(oldItem: WatchListModel, newItem: WatchListModel): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: WatchListModel, newItem: WatchListModel): Boolean {
        return oldItem == newItem
    }

}
