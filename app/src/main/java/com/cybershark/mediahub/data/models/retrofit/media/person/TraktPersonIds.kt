package com.cybershark.mediahub.data.models.retrofit.media.person

data class TraktPersonIds(
    val trakt: Int,
    val slug: String,
    val imdb: String,
    val tmdb: Int
)

/*

JSON Structure

{
    "trakt": 142,
    "slug": "bryan-cranston",
    "imdb": "nm0186505",
    "tmdb": 17419
}

 */