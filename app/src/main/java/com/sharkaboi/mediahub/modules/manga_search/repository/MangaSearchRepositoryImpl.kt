package com.sharkaboi.mediahub.modules.manga_search.repository

import android.content.SharedPreferences
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.sharkaboi.mediahub.data.api.constants.ApiConstants
import com.sharkaboi.mediahub.data.api.models.manga.MangaSearchResponse
import com.sharkaboi.mediahub.data.api.retrofit.MangaService
import com.sharkaboi.mediahub.data.datastore.DataStoreRepository
import com.sharkaboi.mediahub.modules.manga_search.data.MangaSearchDataSource
import com.sharkaboi.mediahub.data.sharedpref.SharedPreferencesKeys
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.firstOrNull
import timber.log.Timber

class MangaSearchRepositoryImpl(
    private val mangaService: MangaService,
    private val dataStoreRepository: DataStoreRepository,
    private val sharedPreferences: SharedPreferences
) : MangaSearchRepository {

    override suspend fun getMangaByQuery(query: String): Flow<PagingData<MangaSearchResponse.Data>> {
        val showNsfw = sharedPreferences.getBoolean(SharedPreferencesKeys.NSFW_OPTION, false)
        val accessToken: String? = dataStoreRepository.accessTokenFlow.firstOrNull()
        Timber.d("accessToken: $accessToken")
        return Pager(
            config = PagingConfig(
                pageSize = ApiConstants.API_PAGE_LIMIT,
                enablePlaceholders = false
            ),
            pagingSourceFactory = {
                MangaSearchDataSource(
                    mangaService = mangaService,
                    accessToken = accessToken,
                    query = query,
                    showNsfw = showNsfw
                )
            }
        ).flow
    }
}
