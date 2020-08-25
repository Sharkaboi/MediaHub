package com.cybershark.mediahub.data.models.retrofit.media.movie

data class TraktMovieIds(
    val trakt: String,
    val slug: String,
    val imdb: String,
    val tmdb: Int
)

/*

JSON Structure

{
    "trakt": 1,
    "slug": "batman-begins-2005",
    "imdb": "tt0372784",
    "tmdb": 272
}

 */
