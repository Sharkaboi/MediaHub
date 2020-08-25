package com.cybershark.mediahub.data.models.retrofit.media.show

data class TraktShowModel(
    val title: String,
    val year: Int,
    val ids: TraktShowIds
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