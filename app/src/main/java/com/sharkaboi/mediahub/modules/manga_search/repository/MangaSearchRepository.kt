package com.sharkaboi.mediahub.modules.manga_search.repository

import android.util.Log
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.sharkaboi.mediahub.data.api.ApiConstants
import com.sharkaboi.mediahub.data.api.models.manga.MangaSearchResponse
import com.sharkaboi.mediahub.data.datastore.DataStoreRepository
import com.sharkaboi.mediahub.data.api.retrofit.MangaService
import com.sharkaboi.mediahub.data.paging.MangaSearchDataSource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.firstOrNull

class MangaSearchRepository(
    private val mangaService: MangaService,
    private val dataStoreRepository: DataStoreRepository
) {
    suspend fun getMangaByQuery(query: String): Flow<PagingData<MangaSearchResponse.Data>> {
        val accessToken: String? = dataStoreRepository.accessTokenFlow.firstOrNull()
        Log.d(TAG, "accessToken: $accessToken")
        return Pager(
            config = PagingConfig(
                pageSize = ApiConstants.API_PAGE_LIMIT,
                enablePlaceholders = false
            ),
            pagingSourceFactory = {
                MangaSearchDataSource(
                    mangaService = mangaService,
                    accessToken = accessToken,
                    query = query
                )
            }
        ).flow
    }

    companion object {
        private const val TAG = "MangaSearchRepository"
    }
}