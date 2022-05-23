package com.sharkaboi.mediahub.data.cache.models.anime

import androidx.room.Entity

@Entity(primaryKeys = ["studioId", "animeId"])
data class AnimeStudioCrossRef(
    val studioId: Int,
    val animeId: Int
)