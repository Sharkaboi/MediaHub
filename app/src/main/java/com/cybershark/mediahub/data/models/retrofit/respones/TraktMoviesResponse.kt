package com.cybershark.mediahub.data.models.retrofit.respones

import com.cybershark.mediahub.data.models.retrofit.media.movie.TraktMovieModel

data class TraktMoviesResponse(
    val released: String,
    val movie: TraktMovieModel
)

/*

JSON Response

{
    "released": "2014-08-01",
    "movie": {
      "title": "Guardians of the Galaxy",
      "year": 2014,
      "ids": {
        "trakt": 28,
        "slug": "guardians-of-the-galaxy-2014",
        "imdb": "tt2015381",
        "tmdb": 118340
      }
    }
  },
  {
    "released": "2014-08-01",
    "movie": {
      "title": "Get On Up",
      "year": 2014,
      "ids": {
        "trakt": 29,
        "slug": "get-on-up-2014",
        "imdb": "tt2473602",
        "tmdb": 239566
      }
    }
}

 */