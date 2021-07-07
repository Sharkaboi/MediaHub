package com.sharkaboi.mediahub.modules.anime_suggestions.vm

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.sharkaboi.mediahub.data.api.models.anime.AnimeSuggestionsResponse
import com.sharkaboi.mediahub.modules.anime_suggestions.repository.AnimeSuggestionsRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

@HiltViewModel
class AnimeSuggestionsViewModel
@Inject constructor(
    private val animeSuggestionsRepository: AnimeSuggestionsRepository
) : ViewModel() {
    private var _pagedResult: Flow<PagingData<AnimeSuggestionsResponse.Data>>? = null

    suspend fun getAnimeSuggestions(): Flow<PagingData<AnimeSuggestionsResponse.Data>> {
        val newResult: Flow<PagingData<AnimeSuggestionsResponse.Data>> =
            animeSuggestionsRepository.getAnimeSuggestions().cachedIn(viewModelScope)
        _pagedResult = newResult
        return newResult
    }
}
