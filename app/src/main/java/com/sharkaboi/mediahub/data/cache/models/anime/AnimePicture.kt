package com.sharkaboi.mediahub.data.cache.models.anime

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class AnimePicture(
    @PrimaryKey val animeId: Int,
    val large: String?,
    val medium: String
)
