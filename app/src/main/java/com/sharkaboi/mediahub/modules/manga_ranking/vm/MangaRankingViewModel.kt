package com.sharkaboi.mediahub.modules.manga_ranking.vm

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.sharkaboi.mediahub.data.api.enums.MangaRankingType
import com.sharkaboi.mediahub.data.api.models.manga.MangaRankingResponse
import com.sharkaboi.mediahub.modules.manga_ranking.repository.MangaRankingRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

@HiltViewModel
class MangaRankingViewModel
@Inject constructor(
    private val mangaRankingRepository: MangaRankingRepository
) : ViewModel() {
    private var _selectedRankingType: MangaRankingType = MangaRankingType.all
    val selectedRankingType: MangaRankingType get() = _selectedRankingType
    private var _pagedResult: Flow<PagingData<MangaRankingResponse.Data>>? = null

    suspend fun getMangaRankingOfFilter(): Flow<PagingData<MangaRankingResponse.Data>> {
        val newResult: Flow<PagingData<MangaRankingResponse.Data>> =
            mangaRankingRepository
                .getMangaRanking(_selectedRankingType)
                .cachedIn(viewModelScope)
        _pagedResult = newResult
        return newResult
    }

    fun setRankingType(mangaRankingType: MangaRankingType) {
        _selectedRankingType = mangaRankingType
    }
}
