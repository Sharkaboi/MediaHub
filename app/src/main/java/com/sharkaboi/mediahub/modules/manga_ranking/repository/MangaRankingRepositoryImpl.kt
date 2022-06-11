package com.sharkaboi.mediahub.modules.manga_ranking.repository

import android.content.SharedPreferences
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.sharkaboi.mediahub.data.api.constants.ApiConstants
import com.sharkaboi.mediahub.data.api.enums.MangaRankingType
import com.sharkaboi.mediahub.data.api.models.manga.MangaRankingResponse
import com.sharkaboi.mediahub.data.api.retrofit.MangaService
import com.sharkaboi.mediahub.data.datastore.DataStoreRepository
import com.sharkaboi.mediahub.data.sharedpref.SharedPreferencesKeys
import com.sharkaboi.mediahub.modules.manga_ranking.data.MangaRankingDataSource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.firstOrNull
import timber.log.Timber

class MangaRankingRepositoryImpl(
    private val mangaService: MangaService,
    private val dataStoreRepository: DataStoreRepository,
    private val sharedPreferences: SharedPreferences
) : MangaRankingRepository {

    override suspend fun getMangaRanking(mangaRankingType: MangaRankingType): Flow<PagingData<MangaRankingResponse.Data>> {
        val showNsfw = sharedPreferences.getBoolean(SharedPreferencesKeys.NSFW_OPTION, false)
        val accessToken: String? = dataStoreRepository.accessTokenFlow.firstOrNull()
        return Pager(
            config = PagingConfig(
                pageSize = ApiConstants.API_PAGE_LIMIT,
                enablePlaceholders = false
            ),
            pagingSourceFactory = {
                MangaRankingDataSource(
                    mangaService = mangaService,
                    accessToken = accessToken,
                    mangaRankingType = mangaRankingType,
                    showNsfw = showNsfw
                )
            }
        ).flow
    }
}
