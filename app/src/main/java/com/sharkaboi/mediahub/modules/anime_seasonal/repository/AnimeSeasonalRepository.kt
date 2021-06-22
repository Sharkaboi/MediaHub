package com.sharkaboi.mediahub.modules.anime_seasonal.repository

import androidx.paging.PagingData
import com.sharkaboi.mediahub.data.api.enums.AnimeSeason
import com.sharkaboi.mediahub.data.api.models.anime.AnimeSeasonalResponse
import kotlinx.coroutines.flow.Flow

interface AnimeSeasonalRepository {
    suspend fun getAnimeSeasonal(
        animeSeason: AnimeSeason,
        year: Int
    ): Flow<PagingData<AnimeSeasonalResponse.Data>>
}