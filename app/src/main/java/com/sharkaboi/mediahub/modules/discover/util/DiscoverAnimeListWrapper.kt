package com.sharkaboi.mediahub.modules.discover.util

import com.sharkaboi.mediahub.data.api.models.anime.AnimeRankingResponse
import com.sharkaboi.mediahub.data.api.models.anime.AnimeSeasonalResponse
import com.sharkaboi.mediahub.data.api.models.anime.AnimeSuggestionsResponse

data class DiscoverAnimeListWrapper(
    val animeSuggestions: List<AnimeSuggestionsResponse.Data>,
    val animeRankings: List<AnimeRankingResponse.Data>,
    val animeOfCurrentSeason: List<AnimeSeasonalResponse.Data>,
)
