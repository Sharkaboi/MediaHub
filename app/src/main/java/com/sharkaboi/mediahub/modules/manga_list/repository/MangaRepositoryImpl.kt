package com.sharkaboi.mediahub.modules.manga_list.repository

import android.content.SharedPreferences
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.sharkaboi.mediahub.data.api.constants.ApiConstants
import com.sharkaboi.mediahub.data.api.enums.MangaStatus
import com.sharkaboi.mediahub.data.api.enums.UserMangaSortType
import com.sharkaboi.mediahub.data.api.models.usermanga.UserMangaListResponse
import com.sharkaboi.mediahub.data.api.retrofit.UserMangaService
import com.sharkaboi.mediahub.data.datastore.DataStoreRepository
import com.sharkaboi.mediahub.data.sharedpref.SharedPreferencesKeys
import com.sharkaboi.mediahub.modules.manga_list.data.UserMangaListDataSource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.firstOrNull
import timber.log.Timber

class MangaRepositoryImpl(
    private val userMangaService: UserMangaService,
    private val dataStoreRepository: DataStoreRepository,
    private val sharedPreferences: SharedPreferences
) : MangaRepository {

    override suspend fun getMangaListFlow(
        mangaStatus: MangaStatus,
        mangaSortType: UserMangaSortType
    ): Flow<PagingData<UserMangaListResponse.Data>> {
        val showNsfw = sharedPreferences.getBoolean(SharedPreferencesKeys.NSFW_OPTION, false)
        val accessToken: String? = dataStoreRepository.accessTokenFlow.firstOrNull()
        Timber.d("accessToken: $accessToken")
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
                    mangaSortType = mangaSortType,
                    showNsfw = showNsfw
                )
            }
        ).flow
    }
}
