package com.sharkaboi.mediahub.modules.anime_suggestions.vm

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.sharkaboi.mediahub.data.api.models.anime.AnimeSuggestionsResponse
import com.sharkaboi.mediahub.modules.anime_suggestions.repository.AnimeSuggestionsRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AnimeSuggestionsViewModel
@Inject constructor(
    private val animeSuggestionsRepository: AnimeSuggestionsRepository
) : ViewModel() {
    private var _result = MutableLiveData<PagingData<AnimeSuggestionsResponse.Data>>()
    val result: LiveData<PagingData<AnimeSuggestionsResponse.Data>> = _result

    init {
        getAnimeSuggestions()
    }

    private fun getAnimeSuggestions() = viewModelScope.launch {
        val newResult: Flow<PagingData<AnimeSuggestionsResponse.Data>> =
            animeSuggestionsRepository.getAnimeSuggestions().cachedIn(viewModelScope)
        _result.value = newResult.firstOrNull()
    }
}
