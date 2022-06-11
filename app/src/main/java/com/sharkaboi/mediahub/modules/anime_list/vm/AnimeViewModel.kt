package com.sharkaboi.mediahub.modules.anime_list.vm

import androidx.lifecycle.*
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.sharkaboi.mediahub.data.api.enums.AnimeStatus
import com.sharkaboi.mediahub.data.api.enums.UserAnimeSortType
import com.sharkaboi.mediahub.data.api.models.useranime.UserAnimeListResponse
import com.sharkaboi.mediahub.modules.anime_list.repository.AnimeListRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class AnimeViewModel
@Inject constructor(
    private val animeListRepository: AnimeListRepository,
    private val savedStateHandle: SavedStateHandle
) : ViewModel() {
    private val savedAnimeStatus = savedStateHandle.get<String>(CHOSEN_ANIME_STATUS_KEY)
    private val savedSortType = savedStateHandle.get<String>(CHOSEN_SORT_TYPE_KEY)

    private var _currentChosenAnimeStatus: AnimeStatus =
        AnimeStatus.parse(savedAnimeStatus) ?: AnimeStatus.all
    val currentChosenAnimeStatus: AnimeStatus get() = _currentChosenAnimeStatus

    private var _currentChosenSortType: UserAnimeSortType =
        UserAnimeSortType.parse(savedSortType) ?: UserAnimeSortType.list_updated_at
    val currentChosenSortType: UserAnimeSortType get() = _currentChosenSortType

    private var _animeList = MutableLiveData<PagingData<UserAnimeListResponse.Data>>()
    val animeList: LiveData<PagingData<UserAnimeListResponse.Data>> = _animeList

    init {
        Timber.d("Saved state anime status $savedAnimeStatus")
        Timber.d("Saved state sort type $savedSortType")
    }

    private fun getAnimeList(shouldEmpty: Boolean) = viewModelScope.launch {
        if (shouldEmpty) {
            _animeList.value = PagingData.empty()
        }
        val newResult: Flow<PagingData<UserAnimeListResponse.Data>> =
            animeListRepository.getAnimeListFlow(
                animeStatus = currentChosenAnimeStatus,
                animeSortType = currentChosenSortType
            ).cachedIn(viewModelScope)
        _animeList.value = newResult.firstOrNull() ?: PagingData.empty()
    }

    fun setSortType(userAnimeSortType: UserAnimeSortType) {
        _currentChosenSortType = userAnimeSortType
        savedStateHandle.set(CHOSEN_SORT_TYPE_KEY, userAnimeSortType.name)
        getAnimeList(false)
    }

    fun setAnimeStatus(animeStatus: AnimeStatus) {
        _currentChosenAnimeStatus = animeStatus
        savedStateHandle.set(CHOSEN_ANIME_STATUS_KEY, animeStatus.name)
        getAnimeList(true)
    }

    fun refresh() {
        getAnimeList(false)
    }

    companion object {
        const val CHOSEN_ANIME_STATUS_KEY = "animeStatus"
        private const val CHOSEN_SORT_TYPE_KEY = "sortType"
    }
}
