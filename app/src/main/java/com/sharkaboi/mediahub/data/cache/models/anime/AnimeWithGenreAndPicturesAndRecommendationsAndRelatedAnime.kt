package com.sharkaboi.mediahub.data.cache.models.anime

import androidx.room.Embedded
import androidx.room.Relation

data class AnimeWithGenreAndPicturesAndRecommendationsAndRelatedAnime(
    @Embedded val animeWithGenresAndPicturesAndRecommendations: AnimeWithGenreAndPicturesAndRecommendations,
    @Relation(
        entity = RelatedAnime::class,
        parentColumn = "animeId",
        entityColumn = "animeId"
    )
    val relatedAnime: List<RelatedAnime>
)