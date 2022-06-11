package com.sharkaboi.mediahub.modules.discover.vm

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sharkaboi.mediahub.data.api.models.anime.AnimeRankingResponse
import com.sharkaboi.mediahub.data.api.models.anime.AnimeSeasonalResponse
import com.sharkaboi.mediahub.data.api.models.anime.AnimeSuggestionsResponse
import com.sharkaboi.mediahub.modules.discover.repository.DiscoverRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DiscoverViewModel
@Inject constructor(
    private val discoverRepository: DiscoverRepository
) : ViewModel() {
    private val _uiState = MutableLiveData<DiscoverState>().setIdle()
    val uiState: LiveData<DiscoverState> = _uiState

    private val _animeSuggestions = MutableLiveData<List<AnimeSuggestionsResponse.Data>>()
    val animeSuggestions: LiveData<List<AnimeSuggestionsResponse.Data>> = _animeSuggestions

    private val _animeRankings = MutableLiveData<List<AnimeRankingResponse.Data>>()
    val animeRankings: LiveData<List<AnimeRankingResponse.Data>> = _animeRankings

    private val _animeOfCurrentSeason = MutableLiveData<List<AnimeSeasonalResponse.Data>>()
    val animeOfCurrentSeason: LiveData<List<AnimeSeasonalResponse.Data>> = _animeOfCurrentSeason

    init {
        getAnimeRecommendations()
    }

    private fun getAnimeRecommendations() = viewModelScope.launch {
        _uiState.setLoading()

        val animeRecommendationsDeferred = async { discoverRepository.getAnimeRecommendations() }
        val animeRankingsDeferred = async { discoverRepository.getAnimeRankings() }
        val animeSeasonalsDeferred = async { discoverRepository.getAnimeSeasonals() }
        val animeRecommendationsResult = animeRecommendationsDeferred.await()
        val animeRankingsResult = animeRankingsDeferred.await()
        val animeSeasonalsResult = animeSeasonalsDeferred.await()

        if (animeRecommendationsResult.isSuccess.not()) {
            _uiState.setFailure(animeRecommendationsResult.error.errorMessage)
        } else {
            _animeSuggestions.value = animeRecommendationsResult.data.data
        }

        if (animeRankingsResult.isSuccess.not()) {
            _uiState.setFailure(animeRankingsResult.error.errorMessage)
        } else {
            _animeRankings.value = animeRankingsResult.data.data
        }

        if (animeSeasonalsResult.isSuccess.not()) {
            _uiState.setFailure(animeSeasonalsResult.error.errorMessage)
        } else {
            _animeOfCurrentSeason.value = animeSeasonalsResult.data.data
        }

        _uiState.setIdle()
    }
}
