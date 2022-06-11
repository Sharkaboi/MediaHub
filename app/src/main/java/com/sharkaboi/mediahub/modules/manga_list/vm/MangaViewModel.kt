package com.sharkaboi.mediahub.modules.manga_list.vm

import androidx.lifecycle.*
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.sharkaboi.mediahub.data.api.enums.MangaStatus
import com.sharkaboi.mediahub.data.api.enums.UserMangaSortType
import com.sharkaboi.mediahub.data.api.models.usermanga.UserMangaListResponse
import com.sharkaboi.mediahub.modules.manga_list.repository.MangaListRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class MangaViewModel
@Inject constructor(
    private val mangaListRepository: MangaListRepository,
    private val savedStateHandle: SavedStateHandle
) : ViewModel() {
    private val savedMangaStatus = savedStateHandle.get<String>(CHOSEN_MANGA_STATUS_KEY)
    private val savedSortType = savedStateHandle.get<String>(CHOSEN_SORT_TYPE_KEY)

    private var _currentChosenMangaStatus: MangaStatus =
        MangaStatus.parse(savedMangaStatus) ?: MangaStatus.all
    private val currentChosenMangaStatus get() = _currentChosenMangaStatus

    private var _currentChosenSortType: UserMangaSortType =
        UserMangaSortType.parse(savedSortType) ?: UserMangaSortType.list_updated_at
    val currentChosenSortType get() = _currentChosenSortType

    private var _mangaList = MutableLiveData<PagingData<UserMangaListResponse.Data>>()
    val mangaList: LiveData<PagingData<UserMangaListResponse.Data>> = _mangaList

    init {
        Timber.d("Saved state manga status $savedMangaStatus")
        Timber.d("Saved state sort type $savedSortType")
    }

    private fun getMangaList(shouldEmpty: Boolean) = viewModelScope.launch {
        if (shouldEmpty) {
            _mangaList.value = PagingData.empty()
        }
        val newResult: Flow<PagingData<UserMangaListResponse.Data>> =
            mangaListRepository.getMangaListFlow(
                mangaStatus = currentChosenMangaStatus,
                mangaSortType = currentChosenSortType
            ).cachedIn(viewModelScope)
        _mangaList.value = newResult.firstOrNull() ?: PagingData.empty()
    }

    fun setMangaStatus(status: MangaStatus) {
        _currentChosenMangaStatus = status
        savedStateHandle.set(CHOSEN_MANGA_STATUS_KEY, status.name)
        getMangaList(false)
    }

    fun setSortType(userMangaSortType: UserMangaSortType) {
        _currentChosenSortType = userMangaSortType
        savedStateHandle.set(CHOSEN_SORT_TYPE_KEY, userMangaSortType.name)
        getMangaList(false)
    }

    fun refresh() {
        getMangaList(false)
    }

    companion object {
        const val CHOSEN_MANGA_STATUS_KEY = "mangaStatus"
        private const val CHOSEN_SORT_TYPE_KEY = "sortType"
    }
}
