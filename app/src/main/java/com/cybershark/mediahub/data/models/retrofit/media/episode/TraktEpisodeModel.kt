package com.cybershark.mediahub.data.models.retrofit.media.episode

data class TraktEpisodeModel(
    val season: Int,
    val number: Int,
    val title: String,
    val ids: TraktEpisodeIds
)

/*

JSON Structure

{
    "season": 1,
    "number": 1,
    "title": "Pilot",
    "ids": {
        "trakt": 16,
        "tvdb": 349232,
        "imdb": "tt0959621",
        "tmdb": 62085
    }
}

 */