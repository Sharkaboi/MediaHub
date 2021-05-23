package com.sharkaboi.mediahub.modules.manga.vm

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.sharkaboi.mediahub.common.data.api.enums.MangaStatus
import com.sharkaboi.mediahub.common.data.api.enums.UserMangaSortType
import com.sharkaboi.mediahub.common.data.api.models.usermanga.UserMangaListResponse
import com.sharkaboi.mediahub.modules.manga.repository.MangaRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

@HiltViewModel
class MangaViewModel
@Inject constructor(
    private val mangaRepository: MangaRepository
) : ViewModel() {
    private var currentChosenMangaStatus: MangaStatus = MangaStatus.all
    private var currentChosenSortType: UserMangaSortType = UserMangaSortType.list_updated_at
    private var _pagedMangaList: Flow<PagingData<UserMangaListResponse.Data>>? = null

    suspend fun getMangaList(
        mangaStatus: MangaStatus,
        userMangaSortType: UserMangaSortType
    ): Flow<PagingData<UserMangaListResponse.Data>> {
        currentChosenMangaStatus = mangaStatus
        currentChosenSortType = userMangaSortType
        val newResult: Flow<PagingData<UserMangaListResponse.Data>> =
            mangaRepository.getMangaListFlow(
                mangaStatus = currentChosenMangaStatus,
                mangaSortType = currentChosenSortType
            ).cachedIn(viewModelScope)
        _pagedMangaList = newResult
        return newResult
    }

    companion object {
        private const val TAG = "AnimeViewModel"
    }
}