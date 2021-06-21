package com.sharkaboi.mediahub.modules.anime_search.repository

import androidx.paging.PagingData
import com.sharkaboi.mediahub.data.api.models.anime.AnimeSearchResponse
import kotlinx.coroutines.flow.Flow

interface AnimeSearchRepository {
    suspend fun getAnimeByQuery(query: String): Flow<PagingData<AnimeSearchResponse.Data>>
}