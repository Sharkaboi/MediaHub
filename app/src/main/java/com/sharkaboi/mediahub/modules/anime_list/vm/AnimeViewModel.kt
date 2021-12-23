package com.sharkaboi.mediahub.modules.anime_list.vm

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.sharkaboi.mediahub.data.api.enums.AnimeStatus
import com.sharkaboi.mediahub.data.api.enums.UserAnimeSortType
import com.sharkaboi.mediahub.data.api.models.useranime.UserAnimeListResponse
import com.sharkaboi.mediahub.modules.anime_list.repository.AnimeRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

@HiltViewModel
class AnimeViewModel
@Inject constructor(
    private val animeRepository: AnimeRepository
) : ViewModel() {
    private var _currentChosenAnimeStatus: AnimeStatus = AnimeStatus.all
    val currentChosenAnimeStatus: AnimeStatus get() = _currentChosenAnimeStatus
    private var _currentChosenSortType: UserAnimeSortType = UserAnimeSortType.list_updated_at
    val currentChosenSortType: UserAnimeSortType get() = _currentChosenSortType
    private var _pagedAnimeList: Flow<PagingData<UserAnimeListResponse.Data>>? = null

    suspend fun getAnimeList(): Flow<PagingData<UserAnimeListResponse.Data>> {
        val newResult: Flow<PagingData<UserAnimeListResponse.Data>> =
            animeRepository.getAnimeListFlow(
                animeStatus = currentChosenAnimeStatus,
                animeSortType = currentChosenSortType
            ).cachedIn(viewModelScope)
        _pagedAnimeList = newResult
        return newResult
    }

    fun setSortType(userAnimeSortType: UserAnimeSortType) {
        _currentChosenSortType = userAnimeSortType
    }

    fun setAnimeStatus(animeStatus: AnimeStatus) {
        _currentChosenAnimeStatus = animeStatus
    }
}
