package com.sharkaboi.mediahub.data.cache.models.anime

import androidx.room.Embedded
import androidx.room.Junction
import androidx.room.Relation

data class AnimeWithStudios(
    @Embedded val anime: AnimeWithGenreAndPicturesAndRecommendationsAndRelatedAnimeAndRelatedManga,
    @Relation(
        parentColumn = "animeId",
        entityColumn = "studioId",
        associateBy = Junction(AnimeStudioCrossRef::class)
    )
    val studios: List<AnimeStudio>
)

