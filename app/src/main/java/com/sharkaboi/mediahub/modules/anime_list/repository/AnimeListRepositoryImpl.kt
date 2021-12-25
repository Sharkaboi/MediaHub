package com.sharkaboi.mediahub.modules.anime_list.repository

import android.content.SharedPreferences
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.sharkaboi.mediahub.data.api.constants.ApiConstants
import com.sharkaboi.mediahub.data.api.enums.AnimeStatus
import com.sharkaboi.mediahub.data.api.enums.UserAnimeSortType
import com.sharkaboi.mediahub.data.api.models.useranime.UserAnimeListResponse
import com.sharkaboi.mediahub.data.api.retrofit.UserAnimeService
import com.sharkaboi.mediahub.data.datastore.DataStoreRepository
import com.sharkaboi.mediahub.data.sharedpref.SharedPreferencesKeys
import com.sharkaboi.mediahub.modules.anime_list.data.UserAnimeListDataSource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.withContext
import timber.log.Timber

class AnimeListRepositoryImpl(
    private val userAnimeService: UserAnimeService,
    private val dataStoreRepository: DataStoreRepository,
    private val sharedPreferences: SharedPreferences
) : AnimeListRepository {

    override suspend fun getAnimeListFlow(
        animeStatus: AnimeStatus,
        animeSortType: UserAnimeSortType
    ): Flow<PagingData<UserAnimeListResponse.Data>> = withContext(Dispatchers.IO) {
        val showNsfw = sharedPreferences.getBoolean(SharedPreferencesKeys.NSFW_OPTION, false)
        val accessToken: String? = dataStoreRepository.accessTokenFlow.firstOrNull()
        Timber.d("accessToken: $accessToken")
        return@withContext Pager(
            config = PagingConfig(
                pageSize = ApiConstants.API_PAGE_LIMIT,
                enablePlaceholders = false
            ),
            pagingSourceFactory = {
                UserAnimeListDataSource(
                    userAnimeService = userAnimeService,
                    accessToken = accessToken,
                    animeStatus = animeStatus,
                    animeSortType = animeSortType,
                    showNsfw = showNsfw
                )
            }
        ).flow
    }
}
