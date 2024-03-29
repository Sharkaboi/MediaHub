package com.sharkaboi.mediahub.modules.anime_list.repository

import androidx.paging.PagingData
import com.sharkaboi.mediahub.data.api.enums.AnimeStatus
import com.sharkaboi.mediahub.data.api.enums.UserAnimeSortType
import com.sharkaboi.mediahub.data.api.models.useranime.UserAnimeListResponse
import kotlinx.coroutines.flow.Flow

interface AnimeListRepository {

    suspend fun getAnimeListFlow(
        animeStatus: AnimeStatus,
        animeSortType: UserAnimeSortType
    ): Flow<PagingData<UserAnimeListResponse.Data>>
}
