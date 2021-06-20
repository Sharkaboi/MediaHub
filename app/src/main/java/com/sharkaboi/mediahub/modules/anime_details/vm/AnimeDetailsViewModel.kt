package com.sharkaboi.mediahub.modules.anime_details.vm

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sharkaboi.mediahub.data.api.enums.AnimeStatus
import com.sharkaboi.mediahub.data.api.enums.animeStatusFromString
import com.sharkaboi.mediahub.modules.anime_details.repository.AnimeDetailsRepository
import com.sharkaboi.mediahub.modules.anime_details.util.AnimeDetailsUpdateClass
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
    private var _animeDetailsUpdate: MutableLiveData<AnimeDetailsUpdateClass> =
        MutableLiveData<AnimeDetailsUpdateClass>()
    val animeDetailsUpdate: LiveData<AnimeDetailsUpdateClass> = _animeDetailsUpdate

    fun getAnimeDetails(animeId: Int) {
        viewModelScope.launch {
            _uiState.setLoading()
            val result = animeDetailsRepository.getAnimeById(animeId)
            if (result.isSuccess) {
                result.data?.let {
                    Log.d(TAG, "getAnimeDetails: ${result.data}")
                    _animeDetailsUpdate.value = AnimeDetailsUpdateClass(
                        animeStatus = it.myListStatus?.status?.animeStatusFromString(),
                        animeId = it.id,
                        score = it.myListStatus?.score,
                        numWatchedEpisode = it.myListStatus?.numEpisodesWatched,
                        totalEps = it.numEpisodes
                    )
                    _uiState.setFetchSuccess(result.data)
                }
            } else {
                _uiState.setFailure(result.error.errorMessage)
            }
        }
    }

    fun setStatus(animeStatus: AnimeStatus) {
        _animeDetailsUpdate.apply {
            value = value?.copy(animeStatus = animeStatus)
            if (animeStatus == AnimeStatus.completed) {
                value = value?.copy(numWatchedEpisode = value?.totalEps)
            }
        }
    }

    fun setEpisodeCount(numWatchedEps: Int) {
        _animeDetailsUpdate.apply {
            if (this.value?.animeStatus == null ||
                this.value?.animeStatus != AnimeStatus.watching ||
                this.value?.animeStatus != AnimeStatus.completed
            ) {
                value = this.value?.copy(animeStatus = AnimeStatus.watching)
            }
            value = value?.copy(numWatchedEpisode = numWatchedEps)
        }
    }

    fun add1ToWatchedEps() {
        Log.d(TAG, _animeDetailsUpdate.value.toString())
        _animeDetailsUpdate.apply {
            if (this.value?.animeStatus == null ||
                this.value?.animeStatus != AnimeStatus.watching ||
                this.value?.animeStatus != AnimeStatus.completed
            ) {
                value = this.value?.copy(animeStatus = AnimeStatus.watching)
            }
            val res = value?.numWatchedEpisode?.plus(1) ?: 0
            value = if (value?.totalEps != 0 && res >= value?.totalEps ?: 0) {
                value?.copy(
                    numWatchedEpisode = value?.totalEps,
                    animeStatus = AnimeStatus.completed
                )
            } else {
                value?.copy(numWatchedEpisode = res)
            }
        }
    }

    fun add5ToWatchedEps() {
        Log.d(TAG, _animeDetailsUpdate.value.toString())
        _animeDetailsUpdate.apply {
            if (this.value?.animeStatus == null ||
                this.value?.animeStatus != AnimeStatus.watching ||
                this.value?.animeStatus != AnimeStatus.completed
            ) {
                value = this.value?.copy(animeStatus = AnimeStatus.watching)
            }
            val res = value?.numWatchedEpisode?.plus(5) ?: 0
            value = if (value?.totalEps != 0 && res >= value?.totalEps ?: 0) {
                value?.copy(
                    numWatchedEpisode = value?.totalEps,
                    animeStatus = AnimeStatus.completed
                )
            } else {
                value?.copy(numWatchedEpisode = res)
            }
        }
    }

    fun add10ToWatchedEps() {
        Log.d(TAG, _animeDetailsUpdate.value.toString())
        _animeDetailsUpdate.apply {
            if (this.value?.animeStatus == null ||
                this.value?.animeStatus != AnimeStatus.watching ||
                this.value?.animeStatus != AnimeStatus.completed
            ) {
                value = this.value?.copy(animeStatus = AnimeStatus.watching)
            }
            val res = value?.numWatchedEpisode?.plus(10) ?: 0
            value = if (value?.totalEps != 0 && res >= value?.totalEps ?: 0) {
                value?.copy(
                    numWatchedEpisode = value?.totalEps,
                    animeStatus = AnimeStatus.completed
                )
            } else {
                value?.copy(numWatchedEpisode = res)
            }
        }
    }

    fun setScore(score: Int) {
        _animeDetailsUpdate.apply {
            value = value?.copy(score = score)
        }
    }

    fun submitStatusUpdate(animeId: Int) {
        viewModelScope.launch {
            _uiState.setLoading()
            animeDetailsUpdate.value?.let {
                Log.d(TAG, "submitStatusUpdate: $it")
                val result = animeDetailsRepository.updateAnimeStatus(
                    animeId = animeId,
                    animeStatus = it.animeStatus?.name,
                    score = it.score,
                    numWatchedEps = it.numWatchedEpisode
                )
                if (result.isSuccess) {
                    getAnimeDetails(animeId)
                } else {
                    _uiState.setFailure(result.error.errorMessage)
                }
            } ?: run {
                _uiState.setFailure("No data to update")
            }
        }
    }

    fun removeFromList(animeId: Int) {
        viewModelScope.launch {
            _uiState.setLoading()
            val result = animeDetailsRepository.removeAnimeFromList(animeId = animeId)
            if (result.isSuccess) {
                getAnimeDetails(animeId)
            } else {
                _uiState.setFailure(result.error.errorMessage)
            }
        }
    }

    companion object {
        private const val TAG = "AnimeDetailsViewModel"
    }
}