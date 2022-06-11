package com.sharkaboi.mediahub.modules.anime_seasonal.vm

import androidx.lifecycle.*
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.sharkaboi.mediahub.data.api.enums.getAnimeSeason
import com.sharkaboi.mediahub.data.api.models.anime.AnimeSeasonalResponse
import com.sharkaboi.mediahub.modules.anime_seasonal.repository.AnimeSeasonalRepository
import com.sharkaboi.mediahub.modules.anime_seasonal.util.AnimeSeasonWrapper
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class AnimeSeasonalViewModel
@Inject constructor(
    private val animeSeasonalRepository: AnimeSeasonalRepository,
    savedStateHandle: SavedStateHandle
) : ViewModel() {

    private val selectedSeason =
        savedStateHandle.get<String>(ANIME_SELECTED_SEASON).getAnimeSeason()
    private val selectedYear = savedStateHandle.get<Int>(ANIME_SELECTED_YEAR)

    private var _animeSeason: AnimeSeasonWrapper =
        AnimeSeasonWrapper.parseFrom(selectedSeason, selectedYear)
            ?: AnimeSeasonWrapper.currentSeason()
    val animeSeason: AnimeSeasonWrapper get() = _animeSeason

    private var _result = MutableLiveData<PagingData<AnimeSeasonalResponse.Data>>()
    val result: LiveData<PagingData<AnimeSeasonalResponse.Data>> = _result

    init {
        Timber.d("Saved state for anime season $selectedSeason")
        Timber.d("Saved state for anime season year $selectedYear")
        getAnimeOfSeason()
    }

    private fun getAnimeOfSeason() = viewModelScope.launch {
        val newResult: Flow<PagingData<AnimeSeasonalResponse.Data>> =
            animeSeasonalRepository
                .getAnimeSeasonal(animeSeason.animeSeason, animeSeason.year)
                .cachedIn(viewModelScope)
        _result.value = newResult.firstOrNull() ?: PagingData.empty()
    }

    fun previousSeason() {
        _animeSeason = _animeSeason.prev()
        getAnimeOfSeason()
    }

    fun nextSeason() {
        _animeSeason = _animeSeason.next()
        getAnimeOfSeason()
    }

    companion object {
        private const val ANIME_SELECTED_SEASON = "season"
        private const val ANIME_SELECTED_YEAR = "year"
    }
}
