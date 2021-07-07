package com.sharkaboi.mediahub.modules.manga.repository

import androidx.paging.PagingData
import com.sharkaboi.mediahub.data.api.enums.MangaStatus
import com.sharkaboi.mediahub.data.api.enums.UserMangaSortType
import com.sharkaboi.mediahub.data.api.models.usermanga.UserMangaListResponse
import kotlinx.coroutines.flow.Flow

interface MangaRepository {

    suspend fun getMangaListFlow(
        mangaStatus: MangaStatus,
        mangaSortType: UserMangaSortType = UserMangaSortType.list_updated_at
    ): Flow<PagingData<UserMangaListResponse.Data>>
}
