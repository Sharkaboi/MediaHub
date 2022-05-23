package com.sharkaboi.mediahub.data.cache.models.anime

import androidx.room.Embedded
import androidx.room.Junction
import androidx.room.Relation

data class AnimeWithGenres(
    @Embedded val anime: AnimeDetails,
    @Relation(
        parentColumn = "animeId",
        entityColumn = "genreId",
        associateBy = Junction(AnimeGenreCrossRef::class)
    )
    val genres: List<AnimeGenre>?
)

