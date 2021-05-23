package com.sharkaboi.mediahub.modules.anime.repository

import android.util.Log
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.sharkaboi.mediahub.common.data.api.ApiConstants
import com.sharkaboi.mediahub.common.data.api.enums.AnimeStatus
import com.sharkaboi.mediahub.common.data.api.enums.UserAnimeSortType
import com.sharkaboi.mediahub.common.data.api.models.useranime.UserAnimeListResponse
import com.sharkaboi.mediahub.common.data.datastore.DataStoreRepository
import com.sharkaboi.mediahub.common.data.retrofit.UserAnimeService
import com.sharkaboi.mediahub.common.paging.UserAnimeListDataSource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.firstOrNull

class AnimeRepository(
    private val userAnimeService: UserAnimeService,
    private val dataStoreRepository: DataStoreRepository
) {
    suspend fun getAnimeListFlow(
        animeStatus: AnimeStatus,
        animeSortType: UserAnimeSortType = UserAnimeSortType.list_updated_at
    ): Flow<PagingData<UserAnimeListResponse.Data>>  {
        val accessToken: String? = dataStoreRepository.accessTokenFlow.firstOrNull()
        Log.d(TAG, "accessToken: $accessToken")
        return Pager(
            config = PagingConfig(
                pageSize = ApiConstants.API_PAGE_LIMIT,
                enablePlaceholders = false
            ),
            pagingSourceFactory = {
                UserAnimeListDataSource(
                    userAnimeService = userAnimeService,
                    accessToken = accessToken,
                    animeStatus = animeStatus,
                    animeSortType = animeSortType
                )
            }
        ).flow
    }

    companion object {
        private const val TAG = "AnimeRepository"
    }
}