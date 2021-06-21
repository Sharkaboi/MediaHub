package com.sharkaboi.mediahub.modules.manga_ranking.repository

import androidx.paging.PagingData
import com.sharkaboi.mediahub.data.api.enums.MangaRankingType
import com.sharkaboi.mediahub.data.api.models.manga.MangaRankingResponse
import kotlinx.coroutines.flow.Flow

interface MangaRankingRepository {

    suspend fun getMangaRanking(mangaRankingType: MangaRankingType): Flow<PagingData<MangaRankingResponse.Data>>
}