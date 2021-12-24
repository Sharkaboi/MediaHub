package com.sharkaboi.mediahub.modules.anime_seasonal.repository

import android.content.SharedPreferences
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.sharkaboi.mediahub.data.api.constants.ApiConstants
import com.sharkaboi.mediahub.data.api.enums.AnimeSeason
import com.sharkaboi.mediahub.data.api.models.anime.AnimeSeasonalResponse
import com.sharkaboi.mediahub.data.api.retrofit.AnimeService
import com.sharkaboi.mediahub.data.datastore.DataStoreRepository
import com.sharkaboi.mediahub.data.sharedpref.SharedPreferencesKeys
import com.sharkaboi.mediahub.modules.anime_seasonal.data.AnimeSeasonalDataSource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.firstOrNull
import timber.log.Timber

class AnimeSeasonalRepositoryImpl(
    private val animeService: AnimeService,
    private val dataStoreRepository: DataStoreRepository,
    private val sharedPreferences: SharedPreferences
) : AnimeSeasonalRepository {

    override suspend fun getAnimeSeasonal(
        animeSeason: AnimeSeason,
        year: Int
    ): Flow<PagingData<AnimeSeasonalResponse.Data>> {
        val showNsfw = sharedPreferences.getBoolean(SharedPreferencesKeys.NSFW_OPTION, false)
        val accessToken: String? = dataStoreRepository.accessTokenFlow.firstOrNull()
        Timber.d("accessToken: $accessToken")
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
                    year = year,
                    showNsfw = showNsfw
                )
            }
        ).flow
    }
}
