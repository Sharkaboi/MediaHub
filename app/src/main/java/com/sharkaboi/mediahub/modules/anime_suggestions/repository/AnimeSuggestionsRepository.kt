package com.sharkaboi.mediahub.modules.anime_suggestions.repository

import android.util.Log
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.sharkaboi.mediahub.common.data.api.ApiConstants
import com.sharkaboi.mediahub.common.data.api.models.anime.AnimeSuggestionsResponse
import com.sharkaboi.mediahub.common.data.datastore.DataStoreRepository
import com.sharkaboi.mediahub.common.data.retrofit.AnimeService
import com.sharkaboi.mediahub.common.paging.AnimeSuggestionsDataSource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.firstOrNull

class AnimeSuggestionsRepository(
    private val animeService: AnimeService,
    private val dataStoreRepository: DataStoreRepository
) {

    suspend fun getAnimeSuggestions(): Flow<PagingData<AnimeSuggestionsResponse.Data>> {
        val accessToken: String? = dataStoreRepository.accessTokenFlow.firstOrNull()
        Log.d(TAG, "accessToken: $accessToken")
        return Pager(
            config = PagingConfig(
                pageSize = ApiConstants.API_PAGE_LIMIT,
                enablePlaceholders = false
            ),
            pagingSourceFactory = {
                AnimeSuggestionsDataSource(
                    animeService = animeService,
                    accessToken = accessToken
                )
            }
        ).flow
    }

    companion object {
        private const val TAG = "AnimeSuggestionRepsitry"
    }
}