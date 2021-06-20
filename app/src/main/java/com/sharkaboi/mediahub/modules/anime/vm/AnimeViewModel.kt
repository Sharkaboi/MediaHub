package com.sharkaboi.mediahub.modules.anime.vm

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.sharkaboi.mediahub.data.api.enums.AnimeStatus
import com.sharkaboi.mediahub.data.api.enums.UserAnimeSortType
import com.sharkaboi.mediahub.data.api.models.useranime.UserAnimeListResponse
import com.sharkaboi.mediahub.modules.anime.repository.AnimeRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

@HiltViewModel
class AnimeViewModel
@Inject constructor(
    private val animeRepository: AnimeRepository
) : ViewModel() {
    private var currentChosenAnimeStatus: AnimeStatus = AnimeStatus.all
    private var currentChosenSortType: UserAnimeSortType = UserAnimeSortType.list_updated_at
    private var _pagedAnimeList: Flow<PagingData<UserAnimeListResponse.Data>>? = null

    suspend fun getAnimeList(
        animeStatus: AnimeStatus,
        userAnimeSortType: UserAnimeSortType
    ): Flow<PagingData<UserAnimeListResponse.Data>> {
        currentChosenAnimeStatus = animeStatus
        currentChosenSortType = userAnimeSortType
        val newResult: Flow<PagingData<UserAnimeListResponse.Data>> =
            animeRepository.getAnimeListFlow(
                animeStatus = currentChosenAnimeStatus,
                animeSortType = currentChosenSortType
            ).cachedIn(viewModelScope)
        _pagedAnimeList = newResult
        return newResult
    }

    companion object {
        private const val TAG = "AnimeViewModel"
    }
}