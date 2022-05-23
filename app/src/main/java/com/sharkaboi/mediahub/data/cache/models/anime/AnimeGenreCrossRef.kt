package com.sharkaboi.mediahub.data.cache.models.anime

import androidx.room.Entity

@Entity(primaryKeys = ["genreId", "animeId"])
data class AnimeGenreCrossRef(
    val genreId: Int,
    val animeId: Int
)