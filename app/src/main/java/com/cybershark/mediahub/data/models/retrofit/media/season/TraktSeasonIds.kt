package com.cybershark.mediahub.data.models.retrofit.media.season

data class TraktSeasonIds(
    val trakt: Int,
    val tvdb: Int,
    val tmdb: Int
)

/*

JSON Structure

{
    "trakt": 1,
    "tvdb": 439371,
    "tmdb": 3577
}

 */