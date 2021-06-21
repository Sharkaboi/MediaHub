package com.sharkaboi.mediahub.modules.anime_suggestions.repository

import androidx.paging.PagingData
import com.sharkaboi.mediahub.data.api.models.anime.AnimeSuggestionsResponse
import kotlinx.coroutines.flow.Flow

interface AnimeSuggestionsRepository {
    suspend fun getAnimeSuggestions(): Flow<PagingData<AnimeSuggestionsResponse.Data>>
}