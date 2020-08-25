package com.cybershark.mediahub.data.models.retrofit.media.show

data class TraktShowIds(
    val trakt: Int,
    val slug: String,
    val tvdb: Int,
    val imdb: String,
    val tmdb: Int
)

/*

JSON Structure

{
    "trakt": 1,
    "slug": "breaking-bad",
    "tvdb": 81189,
    "imdb": "tt0903747",
    "tmdb": 1396
}

 */