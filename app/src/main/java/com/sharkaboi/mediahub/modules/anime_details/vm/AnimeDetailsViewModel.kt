package com.sharkaboi.mediahub.modules.anime_details.vm

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sharkaboi.mediahub.modules.anime_details.repository.AnimeDetailsRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AnimeDetailsViewModel
@Inject constructor(
    private val animeDetailsRepository: AnimeDetailsRepository
) : ViewModel() {

    private val _uiState = MutableLiveData<AnimeDetailsState>().getDefault()
    val uiState: LiveData<AnimeDetailsState> = _uiState

    fun getAnimeDetails(animeId: Int) {
        viewModelScope.launch {
            _uiState.setLoading()
            val result = animeDetailsRepository.getAnimeById(animeId)
            if (result.isSuccess) {
                _uiState.setFetchSuccess(result.data!!)
            } else {
                _uiState.setFailure(result.error.errorMessage)
            }
        }
    }
}