package com.sharkaboi.mediahub.modules.manga.repository

import android.util.Log
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.sharkaboi.mediahub.data.api.ApiConstants
import com.sharkaboi.mediahub.data.api.enums.MangaStatus
import com.sharkaboi.mediahub.data.api.enums.UserMangaSortType
import com.sharkaboi.mediahub.data.api.models.usermanga.UserMangaListResponse
import com.sharkaboi.mediahub.data.datastore.DataStoreRepository
import com.sharkaboi.mediahub.data.api.retrofit.UserMangaService
import com.sharkaboi.mediahub.data.paging.UserMangaListDataSource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.firstOrNull

class MangaRepository(
    private val userMangaService: UserMangaService,
    private val dataStoreRepository: DataStoreRepository
) {
    suspend fun getMangaListFlow(
        mangaStatus: MangaStatus,
        mangaSortType: UserMangaSortType = UserMangaSortType.list_updated_at
    ): Flow<PagingData<UserMangaListResponse.Data>> {
        val accessToken: String? = dataStoreRepository.accessTokenFlow.firstOrNull()
        Log.d(TAG, "accessToken: $accessToken")
        return Pager(
            config = PagingConfig(
                pageSize = ApiConstants.API_PAGE_LIMIT,
                enablePlaceholders = false
            ),
            pagingSourceFactory = {
                UserMangaListDataSource(
                    userMangaService = userMangaService,
                    accessToken = accessToken,
                    mangaStatus = mangaStatus,
                    mangaSortType = mangaSortType
                )
            }
        ).flow
    }

    companion object {
        private const val TAG = "MangaRepository"
    }
}