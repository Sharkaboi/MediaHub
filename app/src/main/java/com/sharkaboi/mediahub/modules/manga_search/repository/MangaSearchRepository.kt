package com.sharkaboi.mediahub.modules.manga_search.repository

import androidx.paging.PagingData
import com.sharkaboi.mediahub.data.api.models.manga.MangaSearchResponse
import kotlinx.coroutines.flow.Flow

interface MangaSearchRepository {

    suspend fun getMangaByQuery(query: String): Flow<PagingData<MangaSearchResponse.Data>>
}