package com.sharkaboi.mediahub.data.cache.models.anime

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class AnimeStudio(
    @PrimaryKey val studioId: Int,
    val name: String
)
