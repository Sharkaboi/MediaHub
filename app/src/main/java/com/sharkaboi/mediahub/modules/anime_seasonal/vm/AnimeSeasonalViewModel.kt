package com.sharkaboi.mediahub.modules.anime_seasonal.vm

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.sharkaboi.mediahub.data.api.enums.getAnimeSeason
import com.sharkaboi.mediahub.data.api.models.anime.AnimeSeasonalResponse
import com.sharkaboi.mediahub.modules.anime_seasonal.repository.AnimeSeasonalRepository
import com.sharkaboi.mediahub.modules.anime_seasonal.util.AnimeSeasonWrapper
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import java.time.LocalDate
import javax.inject.Inject

@HiltViewModel
class AnimeSeasonalViewModel
@Inject constructor(
    private val animeSeasonalRepository: AnimeSeasonalRepository
) : ViewModel() {
    private var _pagedResult: Flow<PagingData<AnimeSeasonalResponse.Data>>? = null
    private var _animeSeasonWrapper = AnimeSeasonWrapper(
        animeSeason = LocalDate.now().getAnimeSeason(),
        year = LocalDate.now().year
    )
    val animeSeasonWrapper get() = _animeSeasonWrapper

    suspend fun getAnimeOfSeason(): Flow<PagingData<AnimeSeasonalResponse.Data>> {
        val newResult: Flow<PagingData<AnimeSeasonalResponse.Data>> =
            animeSeasonalRepository
                .getAnimeSeasonal(_animeSeasonWrapper.animeSeason, _animeSeasonWrapper.year)
                .cachedIn(viewModelScope)
        _pagedResult = newResult
        return newResult
    }

    fun setAnimeSeason(animeSeasonWrapper: AnimeSeasonWrapper) {
        _animeSeasonWrapper = animeSeasonWrapper
    }

    fun previousSeason() {
        _animeSeasonWrapper = _animeSeasonWrapper.prev()
    }

    fun nextSeason() {
        _animeSeasonWrapper = _animeSeasonWrapper.next()
    }

    companion object {
        private const val TAG = "AnimeSeasonalViewModel"
    }
}