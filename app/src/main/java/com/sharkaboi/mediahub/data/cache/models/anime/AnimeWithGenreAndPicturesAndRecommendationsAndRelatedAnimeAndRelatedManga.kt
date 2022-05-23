package com.sharkaboi.mediahub.data.cache.models.anime

import androidx.room.Embedded
import androidx.room.Relation

data class AnimeWithGenreAndPicturesAndRecommendationsAndRelatedAnimeAndRelatedManga(
    @Embedded val animeWithGenresAndPicturesAndRecommendationsAndRelatedAnime: AnimeWithGenreAndPicturesAndRecommendationsAndRelatedAnime,
    @Relation(
        entity = RelatedManga::class,
        parentColumn = "animeId",
        entityColumn = "animeId"
    )
    val relatedManga: List<RelatedManga>
)