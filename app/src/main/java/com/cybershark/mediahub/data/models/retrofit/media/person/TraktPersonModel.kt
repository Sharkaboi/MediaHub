package com.cybershark.mediahub.data.models.retrofit.media.person

data class TraktPersonModel(
    val name: String,
    val ids: TraktPersonIds
)

/*

JSON Structure

{
    "name": "Bryan Cranston",
    "ids": {
        "trakt": 142,
        "slug": "bryan-cranston",
        "imdb": "nm0186505",
        "tmdb": 17419
    }
}

 */