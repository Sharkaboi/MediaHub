package com.sharkaboi.mediahub.data.cache.models.anime

import androidx.room.Embedded
import androidx.room.Relation

data class AnimeWithGenreAndPicturesAndRecommendations(
    @Embedded val animeWithGenresAndPictures: AnimeWithGenreAndPictures,
    @Relation(
        entity = AnimeRecommendations::class,
        parentColumn = "animeId",
        entityColumn = "animeId"
    )
    val recommendations: List<AnimeRecommendations>
)