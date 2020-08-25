package com.cybershark.mediahub.data.models.retrofit.media.movie

data class TraktMovieModel(
    val title: String,
    val year: Int,
    val ids: TraktMovieIds
)

/*

JSON Structure

{
    "title": "Batman Begins",
    "year": 2005,
    "ids": {
        "trakt": 1,
        "slug": "batman-begins-2005",
        "imdb": "tt0372784",
        "tmdb": 272
    }
}

 */