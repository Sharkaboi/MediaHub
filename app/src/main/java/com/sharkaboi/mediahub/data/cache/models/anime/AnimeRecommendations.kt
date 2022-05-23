package com.sharkaboi.mediahub.data.cache.models.anime

import androidx.room.Entity

@Entity
data class AnimeRecommendations(
    val animeId: Int,
    val numRecommendations: Int,
    val recommendationAnimeId: Int,
    val title: String,
    val pictureL: String?,
    val pictureM: String?
)
