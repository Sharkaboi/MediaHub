package com.sharkaboi.mediahub.modules.anime_seasonal.vm

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.sharkaboi.mediahub.common.data.api.enums.AnimeSeason
import com.sharkaboi.mediahub.common.data.api.models.anime.AnimeSeasonalResponse
import com.sharkaboi.mediahub.modules.anime_seasonal.repository.AnimeSeasonalRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

@HiltViewModel
class AnimeSeasonalViewModel
@Inject constructor(
    private val animeSeasonalRepository: AnimeSeasonalRepository
) : ViewModel() {
    private var _pagedResult: Flow<PagingData<AnimeSeasonalResponse.Data>>? = null

    suspend fun setSeason(
        animeSeason: AnimeSeason,
        year: Int
    ): Flow<PagingData<AnimeSeasonalResponse.Data>> {
        val newResult: Flow<PagingData<AnimeSeasonalResponse.Data>> =
            animeSeasonalRepository.getAnimeSeasonal(animeSeason, year).cachedIn(viewModelScope)
        _pagedResult = newResult
        return newResult
    }

    companion object {
        private const val TAG = "AnimeSeasonalViewModel"
    }
}