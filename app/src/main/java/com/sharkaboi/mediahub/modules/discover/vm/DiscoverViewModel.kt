package com.sharkaboi.mediahub.modules.discover.vm

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sharkaboi.mediahub.modules.discover.repository.DiscoverRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DiscoverViewModel
@Inject constructor(
    private val discoverRepository: DiscoverRepository
) : ViewModel() {
    private val _uiState = MutableLiveData<DiscoverState>().setIdle()
    val uiState: LiveData<DiscoverState> = _uiState

    init {
        getAnimeRecommendations()
    }

    private fun getAnimeRecommendations() {
        _uiState.setLoading()
        viewModelScope.launch {
            val result = discoverRepository.getAnimeRecommendations()
            if (result.isSuccess) {
                result.data?.data?.let { _uiState.setFetchSuccess(it) }
            } else {
                _uiState.setFailure(result.error.errorMessage)
            }
        }
    }
}