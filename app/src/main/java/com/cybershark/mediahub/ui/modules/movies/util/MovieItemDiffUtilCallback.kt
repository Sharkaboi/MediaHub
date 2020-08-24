package com.cybershark.mediahub.ui.modules.movies.util

import androidx.recyclerview.widget.DiffUtil
import com.cybershark.mediahub.data.models.entities.MoviesModel

object MovieItemDiffUtilCallback : DiffUtil.ItemCallback<MoviesModel>() {

    override fun areItemsTheSame(oldItem: MoviesModel, newItem: MoviesModel): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: MoviesModel, newItem: MoviesModel): Boolean {
        return oldItem == newItem
    }
}
