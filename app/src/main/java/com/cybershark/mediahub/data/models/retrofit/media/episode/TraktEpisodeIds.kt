package com.cybershark.mediahub.data.models.retrofit.media.episode

data class TraktEpisodeIds(
    val trakt: Int,
    val tvdb: Int,
    val imdb: String,
    val tmdb: Int
)

/*

JSON Structure

{
    "trakt": 16,
    "tvdb": 349232,
    "imdb": "tt0959621",
    "tmdb": 62085
}

 */