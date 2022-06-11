package com.sharkaboi.mediahub.modules.manga_list.repository

import androidx.paging.PagingData
import com.sharkaboi.mediahub.data.api.enums.MangaStatus
import com.sharkaboi.mediahub.data.api.enums.UserMangaSortType
import com.sharkaboi.mediahub.data.api.models.usermanga.UserMangaListResponse
import kotlinx.coroutines.flow.Flow

interface MangaListRepository {

    suspend fun getMangaListFlow(
        mangaStatus: MangaStatus,
        mangaSortType: UserMangaSortType = UserMangaSortType.list_updated_at
    ): Flow<PagingData<UserMangaListResponse.Data>>
}
