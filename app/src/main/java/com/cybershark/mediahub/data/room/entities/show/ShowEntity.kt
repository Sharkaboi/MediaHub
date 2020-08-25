package com.cybershark.mediahub.data.room.entities.show

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "shows")
data class ShowEntity(
    @PrimaryKey
    val traktID: Int,
    val title: String,
    val yearOfRelease: Int,
    val slug: String,
    val tvdbID: Int,
    val imdbID: String,
    val tmdbID: Int
)

/*

JSON Structure

{
    "title": "Breaking Bad",
    "year": 2008,
    "ids": {
        "trakt": 1,
        "slug": "breaking-bad",
        "tvdb": 81189,
        "imdb": "tt0903747",
        "tmdb": 1396
    }
}

 */