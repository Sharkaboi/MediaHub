package com.sharkaboi.mediahub.modules.anime_ranking.repository

import androidx.paging.PagingData
import com.sharkaboi.mediahub.data.api.enums.AnimeRankingType
import com.sharkaboi.mediahub.data.api.models.anime.AnimeRankingResponse
import kotlinx.coroutines.flow.Flow

interface AnimeRankingRepository {
    suspend fun getAnimeRanking(animeRankingType: AnimeRankingType): Flow<PagingData<AnimeRankingResponse.Data>>
}
