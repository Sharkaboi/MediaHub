package com.sharkaboi.mediahub.modules.manga.vm

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.sharkaboi.mediahub.data.api.enums.MangaStatus
import com.sharkaboi.mediahub.data.api.enums.UserMangaSortType
import com.sharkaboi.mediahub.data.api.models.usermanga.UserMangaListResponse
import com.sharkaboi.mediahub.modules.manga.repository.MangaRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

@HiltViewModel
class MangaViewModel
@Inject constructor(
    private val mangaRepository: MangaRepository
) : ViewModel() {
    private var _currentChosenMangaStatus: MangaStatus = MangaStatus.all
    val currentChosenMangaStatus get() = _currentChosenMangaStatus
    private var _currentChosenSortType: UserMangaSortType = UserMangaSortType.list_updated_at
    val currentChosenSortType get() = _currentChosenSortType
    private var _pagedMangaList: Flow<PagingData<UserMangaListResponse.Data>>? = null

    suspend fun getMangaList(): Flow<PagingData<UserMangaListResponse.Data>> {
        val newResult: Flow<PagingData<UserMangaListResponse.Data>> =
            mangaRepository.getMangaListFlow(
                mangaStatus = currentChosenMangaStatus,
                mangaSortType = currentChosenSortType
            ).cachedIn(viewModelScope)
        _pagedMangaList = newResult
        return newResult
    }

    fun setMangaStatus(status: MangaStatus) {
        _currentChosenMangaStatus = status
    }

    fun setSortType(userMangaSortType: UserMangaSortType) {
        _currentChosenSortType = userMangaSortType
    }

    companion object {
        private const val TAG = "MangaViewModel"
    }
}