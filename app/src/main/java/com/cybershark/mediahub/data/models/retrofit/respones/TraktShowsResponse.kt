package com.cybershark.mediahub.data.models.retrofit.respones

import com.cybershark.mediahub.data.models.retrofit.media.episode.TraktEpisodeModel
import com.cybershark.mediahub.data.models.retrofit.media.show.TraktShowModel

data class TraktShowsResponse(
    val first_aired: String,
    val episode: TraktEpisodeModel,
    val show: TraktShowModel
)

/*

JSON Structure

{
    "first_aired": "2014-07-14T01:00:00.000Z",
    "episode": {
      "season": 7,
      "number": 4,
      "title": "Death is Not the End",
      "ids": {
        "trakt": 443,
        "tvdb": 4851180,
        "imdb": "tt3500614",
        "tmdb": 988123
      }
    },
    "show": {
      "title": "True Blood",
      "year": 2008,
      "ids": {
        "trakt": 5,
        "slug": "true-blood",
        "tvdb": 82283,
        "imdb": "tt0844441",
        "tmdb": 10545
      }
    }
}

 */