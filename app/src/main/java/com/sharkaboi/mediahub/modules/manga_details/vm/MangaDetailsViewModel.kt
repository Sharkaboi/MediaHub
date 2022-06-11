package com.sharkaboi.mediahub.modules.manga_details.vm

import androidx.lifecycle.*
import com.sharkaboi.mediahub.data.api.enums.MangaStatus
import com.sharkaboi.mediahub.data.wrappers.MHError
import com.sharkaboi.mediahub.modules.manga_details.repository.MangaDetailsRepository
import com.sharkaboi.mediahub.modules.manga_details.util.*
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class MangaDetailsViewModel
@Inject constructor(
    private val mangaDetailsRepository: MangaDetailsRepository,
    savedStateHandle: SavedStateHandle
) : ViewModel() {
    private val mangaId = savedStateHandle.get<Int>(MANGA_ID_KEY) ?: 0

    private val _uiState = MutableLiveData<MangaDetailsState>().getDefault()
    val uiState: LiveData<MangaDetailsState> = _uiState

    private var _mangaDetailsUpdate: MutableLiveData<MangaDetailsUpdateClass> =
        MutableLiveData<MangaDetailsUpdateClass>()
    val mangaDetailsUpdate: LiveData<MangaDetailsUpdateClass> = _mangaDetailsUpdate

    init {
        Timber.d("Saved state manga id $mangaId")
        getMangaDetails(mangaId)
    }

    private fun getMangaDetails(mangaId: Int) = viewModelScope.launch {
        _uiState.setLoading()
        val result = mangaDetailsRepository.getMangaById(mangaId)
        if (!result.isSuccess) {
            _uiState.setFailure(result.error.errorMessage)
            return@launch
        }

        Timber.d("getMangaDetails: ${result.data}")
        _mangaDetailsUpdate.value = MangaDetailsUpdateClass(
            mangaStatus = MangaStatus.parse(result.data.myListStatus?.status),
            mangaId = result.data.id,
            score = result.data.myListStatus?.score,
            numReadChapters = result.data.myListStatus?.numChaptersRead,
            numReadVolumes = result.data.myListStatus?.numVolumesRead,
            totalVolumes = result.data.numVolumes,
            totalChapters = result.data.numChapters
        )
        _uiState.setFetchSuccess(result.data)
    }

    fun setStatus(mangaStatus: MangaStatus) {
        _mangaDetailsUpdate.setStatus(mangaStatus)

        if (mangaStatus == MangaStatus.completed) {
            _mangaDetailsUpdate.setReadAsTotal()
        }
    }

    fun setReadChapterCount(numReadChapters: Int) {
        if (_mangaDetailsUpdate.isNotOfStatus(MangaStatus.reading, MangaStatus.completed)) {
            _mangaDetailsUpdate.setStatus(MangaStatus.reading)
        }

        if (_mangaDetailsUpdate.isMoreOrEqualToTotalChaps(numReadChapters)) {
            _mangaDetailsUpdate.setReadAsTotalChaps()
            _mangaDetailsUpdate.setStatus(MangaStatus.completed)
            return
        }

        _mangaDetailsUpdate.setReadChapters(numReadChapters)
    }

    fun setReadVolumeCount(numReadVolumes: Int) {
        if (_mangaDetailsUpdate.isNotOfStatus(MangaStatus.reading, MangaStatus.completed)) {
            _mangaDetailsUpdate.setStatus(MangaStatus.reading)
        }

        if (_mangaDetailsUpdate.isMoreOrEqualToTotalVols(numReadVolumes)) {
            _mangaDetailsUpdate.setReadAsTotalVols()
            _mangaDetailsUpdate.setStatus(MangaStatus.completed)
            return
        }

        _mangaDetailsUpdate.setReadVolumes(numReadVolumes)
    }

    fun setScore(score: Int) {
        _mangaDetailsUpdate.setScore(score)
    }

    fun submitStatusUpdate() = viewModelScope.launch {
        _uiState.setLoading()
        val details = _mangaDetailsUpdate.value
        if (details == null) {
            _uiState.setFailure(MHError.InvalidStateError.errorMessage)
            return@launch
        }

        val result = mangaDetailsRepository.updateMangaStatus(details)

        if (!result.isSuccess) {
            _uiState.setFailure(result.error.errorMessage)
            return@launch
        }

        refreshDetails()
    }

    fun removeFromList() = viewModelScope.launch {
        val id = mangaDetailsUpdate.value?.mangaId ?: return@launch
        _uiState.setLoading()
        val result = mangaDetailsRepository.removeMangaFromList(mangaId = id)
        if (!result.isSuccess) {
            _uiState.setFailure(result.error.errorMessage)
            return@launch
        }

        refreshDetails()
    }

    fun refreshDetails() {
        getMangaDetails(mangaId)
    }

    companion object {
        private const val MANGA_ID_KEY = "mangaId"
    }
}
