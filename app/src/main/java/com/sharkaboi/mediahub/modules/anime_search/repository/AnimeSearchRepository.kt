package com.sharkaboi.mediahub.modules.anime_search.repository

import android.util.Log
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.sharkaboi.mediahub.data.api.ApiConstants
import com.sharkaboi.mediahub.data.api.models.anime.AnimeSearchResponse
import com.sharkaboi.mediahub.data.datastore.DataStoreRepository
import com.sharkaboi.mediahub.data.api.retrofit.AnimeService
import com.sharkaboi.mediahub.data.paging.AnimeSearchDataSource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.firstOrNull

class AnimeSearchRepository(
    private val animeService: AnimeService,
    private val dataStoreRepository: DataStoreRepository
) {
    suspend fun getAnimeByQuery(query: String): Flow<PagingData<AnimeSearchResponse.Data>> {
        val accessToken: String? = dataStoreRepository.accessTokenFlow.firstOrNull()
        Log.d(TAG, "accessToken: $accessToken")
        return Pager(
            config = PagingConfig(
                pageSize = ApiConstants.API_PAGE_LIMIT,
                enablePlaceholders = false
            ),
            pagingSourceFactory = {
                AnimeSearchDataSource(
                    animeService = animeService,
                    accessToken = accessToken,
                    query = query
                )
            }
        ).flow
    }

    companion object {
        private const val TAG = "AnimeSearchRepository"
    }
}