package com.sharkaboi.mediahub.data.cache.models.anime

import androidx.room.Embedded
import androidx.room.Relation

data class AnimeWithGenreAndPictures(
    @Embedded val animeWithGenres: AnimeWithGenres,
    @Relation(
        entity = AnimePicture::class,
        parentColumn = "animeId",
        entityColumn = "animeId"
    )
    val pictures: List<AnimePicture>
)