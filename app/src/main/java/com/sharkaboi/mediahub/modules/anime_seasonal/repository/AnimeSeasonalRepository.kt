package com.sharkaboi.mediahub.modules.anime_seasonal.repository

import android.util.Log
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.sharkaboi.mediahub.common.data.api.ApiConstants
import com.sharkaboi.mediahub.common.data.api.enums.AnimeSeason
import com.sharkaboi.mediahub.common.data.api.models.anime.AnimeSeasonalResponse
import com.sharkaboi.mediahub.common.data.datastore.DataStoreRepository
import com.sharkaboi.mediahub.common.data.retrofit.AnimeService
import com.sharkaboi.mediahub.common.paging.AnimeSeasonalDataSource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.firstOrNull

class AnimeSeasonalRepository(
    private val animeService: AnimeService,
    private val dataStoreRepository: DataStoreRepository
) {

    suspend fun getAnimeSeasonal(
        animeSeason: AnimeSeason,
        year: Int
    ): Flow<PagingData<AnimeSeasonalResponse.Data>> {
        val accessToken: String? = dataStoreRepository.accessTokenFlow.firstOrNull()
        Log.d(TAG, "accessToken: $accessToken")
        return Pager(
            config = PagingConfig(
                pageSize = ApiConstants.API_PAGE_LIMIT,
                enablePlaceholders = false
            ),
            pagingSourceFactory = {
                AnimeSeasonalDataSource(
                    animeService = animeService,
                    accessToken = accessToken,
                    animeSeason = animeSeason,
                    year = year
                )
            }
        ).flow
    }

    companion object {
        private const val TAG = "AnimeSeasonalRepository"
    }
}