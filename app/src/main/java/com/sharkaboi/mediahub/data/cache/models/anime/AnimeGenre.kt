package com.sharkaboi.mediahub.data.cache.models.anime

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class AnimeGenre(
    @PrimaryKey val genreId: Int,
    val name: String,
)
