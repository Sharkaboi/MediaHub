package com.sharkaboi.mediahub.modules.anime_details.vm

import androidx.lifecycle.*
import com.sharkaboi.mediahub.data.api.enums.AnimeStatus
import com.sharkaboi.mediahub.data.wrappers.MHError
import com.sharkaboi.mediahub.modules.anime_details.repository.AnimeDetailsRepository
import com.sharkaboi.mediahub.modules.anime_details.util.AnimeDetailsUpdateClass
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class AnimeDetailsViewModel
@Inject constructor(
    private val animeDetailsRepository: AnimeDetailsRepository,
    savedStateHandle: SavedStateHandle
) : ViewModel() {
    private val animeId = savedStateHandle.get<Int>(ANIME_ID_KEY) ?: 0

    private val _animeDetailState = MutableLiveData<AnimeDetailsState>().getDefault()
    val animeDetailState: LiveData<AnimeDetailsState> = _animeDetailState

    private var _animeDetailsUpdate: MutableLiveData<AnimeDetailsUpdateClass> =
        MutableLiveData<AnimeDetailsUpdateClass>()
    val animeDetailsUpdate: LiveData<AnimeDetailsUpdateClass> = _animeDetailsUpdate

    private var _nextEpisodeDetails: MutableLiveData<NextEpisodeDetailsState> =
        MutableLiveData<NextEpisodeDetailsState>().getDefault()
    val nextEpisodeDetails: LiveData<NextEpisodeDetailsState> = _nextEpisodeDetails

    init {
        Timber.d("Saved state anime id $animeId")
        getAnimeDetails(animeId)
        getNextEpisodeDetails(animeId)
    }

    private fun getAnimeDetails(animeId: Int) {
        viewModelScope.launch {
            _animeDetailState.setLoading()
            val result = animeDetailsRepository.getAnimeById(animeId)
            if (result.isSuccess) {
                result.data?.let {
                    Timber.d("getAnimeDetails: ${result.data}")
                    _animeDetailsUpdate.value = AnimeDetailsUpdateClass(
                        animeStatus = AnimeStatus.parse(it.myListStatus?.status),
                        animeId = it.id,
                        score = it.myListStatus?.score,
                        numWatchedEpisode = it.myListStatus?.numEpisodesWatched,
                        totalEps = it.numEpisodes
                    )
                    _animeDetailState.setFetchSuccess(result.data)
                }
            } else {
                _animeDetailState.setFailure(result.error.errorMessage)
            }
        }
    }

    private fun getNextEpisodeDetails(animeId: Int) {
        viewModelScope.launch {
            _nextEpisodeDetails.setLoading()
            val result = animeDetailsRepository.getNextAiringEpisodeById(animeId)
            if (result.isSuccess) {
                result.data?.let {
                    Timber.d("nextEpisodeDetails: ${result.data}")
                    _nextEpisodeDetails.setFetchSuccess(result.data)
                }
            } else {
                _nextEpisodeDetails.setFailure(result.error.errorMessage)
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
                (
                        this.value?.animeStatus != AnimeStatus.watching &&
                                this.value?.animeStatus != AnimeStatus.completed
                        )
            ) {
                value = this.value?.copy(animeStatus = AnimeStatus.watching)
            }
            value = if (value?.totalEps != 0 && numWatchedEps >= value?.totalEps ?: 0) {
                value?.copy(
                    numWatchedEpisode = value?.totalEps,
                    animeStatus = AnimeStatus.completed
                )
            } else {
                value?.copy(numWatchedEpisode = numWatchedEps)
            }
        }
    }

    fun add1ToWatchedEps() {
        Timber.d(_animeDetailsUpdate.value.toString())
        _animeDetailsUpdate.apply {
            if (this.value?.animeStatus == null ||
                (
                        this.value?.animeStatus != AnimeStatus.watching &&
                                this.value?.animeStatus != AnimeStatus.completed
                        )
            ) {
                value = this.value?.copy(animeStatus = AnimeStatus.watching)
            }
            val res = value?.numWatchedEpisode?.plus(1) ?: 1
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
        Timber.d(_animeDetailsUpdate.value.toString())
        _animeDetailsUpdate.apply {
            if (this.value?.animeStatus == null ||
                (
                        this.value?.animeStatus != AnimeStatus.watching &&
                                this.value?.animeStatus != AnimeStatus.completed
                        )
            ) {
                value = this.value?.copy(animeStatus = AnimeStatus.watching)
            }
            val res = value?.numWatchedEpisode?.plus(5) ?: 5
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
        Timber.d(_animeDetailsUpdate.value.toString())
        _animeDetailsUpdate.apply {
            if (this.value?.animeStatus == null ||
                (
                        this.value?.animeStatus != AnimeStatus.watching &&
                                this.value?.animeStatus != AnimeStatus.completed
                        )
            ) {
                value = this.value?.copy(animeStatus = AnimeStatus.watching)
            }
            val res = value?.numWatchedEpisode?.plus(10) ?: 10
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
            _animeDetailState.setLoading()
            animeDetailsUpdate.value?.let {
                Timber.d("submitStatusUpdate: $it")
                val result = animeDetailsRepository.updateAnimeStatus(
                    animeId = animeId,
                    animeStatus = it.animeStatus?.name,
                    score = it.score,
                    numWatchedEps = it.numWatchedEpisode
                )
                if (result.isSuccess) {
                    refreshDetails()
                } else {
                    _animeDetailState.setFailure(result.error.errorMessage)
                }
            } ?: run {
                _animeDetailState.setFailure(MHError.InvalidStateError.errorMessage)
            }
        }
    }

    fun removeFromList(animeId: Int) {
        viewModelScope.launch {
            _animeDetailState.setLoading()
            val result = animeDetailsRepository.removeAnimeFromList(animeId = animeId)
            if (result.isSuccess) {
                refreshDetails()
            } else {
                _animeDetailState.setFailure(result.error.errorMessage)
            }
        }
    }

    fun refreshDetails() {
        getAnimeDetails(animeId)
        getNextEpisodeDetails(animeId)
    }

    companion object {
        private const val ANIME_ID_KEY = "animeId"
    }
}
