package com.sharkaboi.mediahub.modules.discover.repository

import com.sharkaboi.mediahub.data.api.models.anime.AnimeRankingResponse
import com.sharkaboi.mediahub.data.api.models.anime.AnimeSeasonalResponse
import com.sharkaboi.mediahub.data.api.models.anime.AnimeSuggestionsResponse
import com.sharkaboi.mediahub.data.wrappers.MHTaskState

interface DiscoverRepository {
    suspend fun getAnimeRecommendations(): MHTaskState<AnimeSuggestionsResponse>
    suspend fun getAnimeSeasonals(): MHTaskState<AnimeSeasonalResponse>
    suspend fun getAnimeRankings(): MHTaskState<AnimeRankingResponse>
}