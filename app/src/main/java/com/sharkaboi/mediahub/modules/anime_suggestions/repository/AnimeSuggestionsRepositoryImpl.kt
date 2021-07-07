package com.sharkaboi.mediahub.modules.anime_suggestions.repository

import android.content.SharedPreferences
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.sharkaboi.mediahub.data.api.ApiConstants
import com.sharkaboi.mediahub.data.api.models.anime.AnimeSuggestionsResponse
import com.sharkaboi.mediahub.data.api.retrofit.AnimeService
import com.sharkaboi.mediahub.data.datastore.DataStoreRepository
import com.sharkaboi.mediahub.data.paging.AnimeSuggestionsDataSource
import com.sharkaboi.mediahub.data.sharedpref.SharedPreferencesKeys
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.firstOrNull
import timber.log.Timber

class AnimeSuggestionsRepositoryImpl(
    private val animeService: AnimeService,
    private val dataStoreRepository: DataStoreRepository,
    private val sharedPreferences: SharedPreferences
) : AnimeSuggestionsRepository {

    override suspend fun getAnimeSuggestions(): Flow<PagingData<AnimeSuggestionsResponse.Data>> {
        val showNsfw = sharedPreferences.getBoolean(SharedPreferencesKeys.NSFW_OPTION, false)
        val accessToken: String? = dataStoreRepository.accessTokenFlow.firstOrNull()
        Timber.d("accessToken: $accessToken")
        return Pager(
            config = PagingConfig(
                pageSize = ApiConstants.API_PAGE_LIMIT,
                enablePlaceholders = false
            ),
            pagingSourceFactory = {
                AnimeSuggestionsDataSource(
                    animeService = animeService,
                    accessToken = accessToken,
                    showNsfw = showNsfw
                )
            }
        ).flow
    }
}
