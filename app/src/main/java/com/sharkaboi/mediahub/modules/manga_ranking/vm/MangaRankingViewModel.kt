package com.sharkaboi.mediahub.modules.manga_ranking.vm

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.sharkaboi.mediahub.common.data.api.enums.MangaRankingType
import com.sharkaboi.mediahub.common.data.api.models.manga.MangaRankingResponse
import com.sharkaboi.mediahub.modules.manga_ranking.repository.MangaRankingRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

@HiltViewModel
class MangaRankingViewModel
@Inject constructor(
    private val mangaRankingRepository: MangaRankingRepository
) : ViewModel() {
    private var _pagedResult: Flow<PagingData<MangaRankingResponse.Data>>? = null

    suspend fun setRankFilterType(
        it: MangaRankingType
    ): Flow<PagingData<MangaRankingResponse.Data>> {
        val newResult: Flow<PagingData<MangaRankingResponse.Data>> =
            mangaRankingRepository.getMangaRanking(it).cachedIn(viewModelScope)
        _pagedResult = newResult
        return newResult
    }

    companion object {
        private const val TAG = "MangaRankingViewModel"
    }
}