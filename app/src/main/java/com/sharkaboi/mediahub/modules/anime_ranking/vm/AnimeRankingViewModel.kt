package com.sharkaboi.mediahub.modules.anime_ranking.vm

import androidx.lifecycle.*
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.sharkaboi.mediahub.data.api.enums.AnimeRankingType
import com.sharkaboi.mediahub.data.api.models.anime.AnimeRankingResponse
import com.sharkaboi.mediahub.modules.anime_ranking.repository.AnimeRankingRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class AnimeRankingViewModel
@Inject constructor(
    private val animeRankingRepository: AnimeRankingRepository,
    private val savedStateHandle: SavedStateHandle
) : ViewModel() {
    private val selectedRankingType: String? = savedStateHandle.get(ANIME_RANKING_KEY)

    private var _rankingType: AnimeRankingType =
        AnimeRankingType.getAnimeRankingFromString(selectedRankingType)
    val rankingType: AnimeRankingType get() = _rankingType

    private val _result = MutableLiveData<PagingData<AnimeRankingResponse.Data>>()
    val result: LiveData<PagingData<AnimeRankingResponse.Data>> = _result

    init {
        Timber.d("Saved state for anime ranking type $selectedRankingType")
        getAnimeForRankingType()
    }

    private fun getAnimeForRankingType() = viewModelScope.launch {
        val newResult: Flow<PagingData<AnimeRankingResponse.Data>> =
            animeRankingRepository
                .getAnimeRanking(_rankingType)
                .cachedIn(viewModelScope)
        _result.value = newResult.firstOrNull()
    }

    fun setRankingType(animeRankingType: AnimeRankingType) {
        _rankingType = animeRankingType
        savedStateHandle.set(ANIME_RANKING_KEY, animeRankingType.name)
        getAnimeForRankingType()
    }

    companion object {
        private const val ANIME_RANKING_KEY = "animeRankingType"
    }
}
