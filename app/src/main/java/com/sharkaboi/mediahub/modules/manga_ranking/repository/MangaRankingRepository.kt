package com.sharkaboi.mediahub.modules.manga_ranking.repository

import android.util.Log
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.sharkaboi.mediahub.common.data.api.ApiConstants
import com.sharkaboi.mediahub.common.data.api.enums.MangaRankingType
import com.sharkaboi.mediahub.common.data.api.models.manga.MangaRankingResponse
import com.sharkaboi.mediahub.common.data.datastore.DataStoreRepository
import com.sharkaboi.mediahub.common.data.retrofit.MangaService
import com.sharkaboi.mediahub.common.paging.MangaRankingDataSource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.firstOrNull

class MangaRankingRepository(
    private val mangaService: MangaService,
    private val dataStoreRepository: DataStoreRepository
) {

    suspend fun getMangaRanking(mangaRankingType: MangaRankingType): Flow<PagingData<MangaRankingResponse.Data>> {
        val accessToken: String? = dataStoreRepository.accessTokenFlow.firstOrNull()
        Log.d(TAG, "accessToken: $accessToken")
        return Pager(
            config = PagingConfig(
                pageSize = ApiConstants.API_PAGE_LIMIT,
                enablePlaceholders = false
            ),
            pagingSourceFactory = {
                MangaRankingDataSource(
                    mangaService = mangaService,
                    accessToken = accessToken,
                    mangaRankingType = mangaRankingType
                )
            }
        ).flow
    }

    companion object {
        private const val TAG = "MangaRankingRepository"
    }
}