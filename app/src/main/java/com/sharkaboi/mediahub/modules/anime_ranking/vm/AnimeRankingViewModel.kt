package com.sharkaboi.mediahub.modules.anime_ranking.vm

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.sharkaboi.mediahub.data.api.enums.AnimeRankingType
import com.sharkaboi.mediahub.data.api.models.anime.AnimeRankingResponse
import com.sharkaboi.mediahub.modules.anime_ranking.repository.AnimeRankingRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

@HiltViewModel
class AnimeRankingViewModel
@Inject constructor(
    private val animeRankingRepository: AnimeRankingRepository
) : ViewModel() {
    private var _pagedResult: Flow<PagingData<AnimeRankingResponse.Data>>? = null

    suspend fun setRankFilterType(
        it: AnimeRankingType
    ): Flow<PagingData<AnimeRankingResponse.Data>> {
        val newResult: Flow<PagingData<AnimeRankingResponse.Data>> =
            animeRankingRepository.getAnimeRanking(it).cachedIn(viewModelScope)
        _pagedResult = newResult
        return newResult
    }

    companion object {
        private const val TAG = "AnimeRankingViewModel"
    }
}