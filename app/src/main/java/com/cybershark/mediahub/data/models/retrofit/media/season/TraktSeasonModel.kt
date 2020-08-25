package com.cybershark.mediahub.data.models.retrofit.media.season

data class TraktSeasonModel(
    val number: Int,
    val ids: TraktSeasonIds
)

/*

JSON Structure

{
    "number": 0,
    "ids": {
        "trakt": 1,
        "tvdb": 439371,
        "tmdb": 3577
    }
}

 */