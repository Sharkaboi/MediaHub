package com.sharkaboi.mediahub.modules.anime_details.vm

import androidx.lifecycle.*
import com.sharkaboi.mediahub.data.api.enums.AnimeStatus
import com.sharkaboi.mediahub.data.wrappers.MHError
import com.sharkaboi.mediahub.modules.anime_details.repository.AnimeDetailsRepository
import com.sharkaboi.mediahub.modules.anime_details.util.*
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

    private fun getAnimeDetails(animeId: Int) = viewModelScope.launch {
        _animeDetailState.setLoading()
        val result = animeDetailsRepository.getAnimeById(animeId)
        if (!result.isSuccess) {
            _animeDetailState.setFailure(result.error.errorMessage)
            return@launch
        }

        Timber.d("getAnimeDetails: $result.data")
        _animeDetailsUpdate.value = AnimeDetailsUpdateClass(
            animeStatus = AnimeStatus.parse(result.data.myListStatus?.status),
            animeId = result.data.id,
            score = result.data.myListStatus?.score,
            numWatchedEpisode = result.data.myListStatus?.numEpisodesWatched,
            totalEps = result.data.numEpisodes
        )
        _animeDetailState.setFetchSuccess(result.data)
    }

    private fun getNextEpisodeDetails(animeId: Int) = viewModelScope.launch {
        _nextEpisodeDetails.setLoading()
        val result = animeDetailsRepository.getNextAiringEpisodeById(animeId)
        if (!result.isSuccess) {
            _nextEpisodeDetails.setFailure(result.error.errorMessage)
            return@launch
        }

        Timber.d("nextEpisodeDetails: ${result.data}")
        _nextEpisodeDetails.setFetchSuccess(result.data)
    }

    fun setStatus(animeStatus: AnimeStatus) {
        _animeDetailsUpdate.setStatus(animeStatus)

        if (animeStatus == AnimeStatus.completed) {
            _animeDetailsUpdate.setWatchedAsTotal()
        }
    }

    fun setEpisodeCount(numWatchedEps: Int) {
        if (_animeDetailsUpdate.isNotOfStatus(AnimeStatus.watching, AnimeStatus.completed)) {
            _animeDetailsUpdate.setStatus(AnimeStatus.watching)
        }

        if (_animeDetailsUpdate.isMoreOrEqualToTotal(numWatchedEps)) {
            _animeDetailsUpdate.setWatchedAsTotal()
            _animeDetailsUpdate.setStatus(AnimeStatus.completed)
            return
        }

        _animeDetailsUpdate.setWatchedEps(numWatchedEps)
    }

    fun addToWatchedEps(count: Int) {
        val addedCount = _animeDetailsUpdate.getAddedWatchedEps(count)
        setEpisodeCount(addedCount)
    }

    fun setScore(score: Int) {
        _animeDetailsUpdate.setScore(score)
    }

    fun submitStatusUpdate() = viewModelScope.launch {
        _animeDetailState.setLoading()
        val details = animeDetailsUpdate.value
        if (details == null) {
            _animeDetailState.setFailure(MHError.InvalidStateError.errorMessage)
            return@launch
        }

        val result = animeDetailsRepository.updateAnimeStatus(
            animeId = details.animeId,
            animeStatus = details.animeStatus?.name,
            score = details.score,
            numWatchedEps = details.numWatchedEpisode
        )

        if (!result.isSuccess) {
            _animeDetailState.setFailure(result.error.errorMessage)
            return@launch
        }

        refreshDetails()
    }

    fun removeFromList() = viewModelScope.launch {
        val id = animeDetailsUpdate.value?.animeId ?: return@launch
        _animeDetailState.setLoading()
        val result = animeDetailsRepository.removeAnimeFromList(animeId = id)
        if (!result.isSuccess) {
            _animeDetailState.setFailure(result.error.errorMessage)
            return@launch
        }

        refreshDetails()
    }

    fun refreshDetails() {
        getAnimeDetails(animeId)
        getNextEpisodeDetails(animeId)
    }

    companion object {
        private const val ANIME_ID_KEY = "animeId"
    }
}
