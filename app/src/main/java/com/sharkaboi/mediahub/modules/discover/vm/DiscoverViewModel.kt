package com.sharkaboi.mediahub.modules.discover.vm

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sharkaboi.mediahub.modules.discover.repository.DiscoverRepository
import com.sharkaboi.mediahub.modules.discover.util.DiscoverAnimeListWrapper
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DiscoverViewModel
@Inject constructor(
    private val discoverRepository: DiscoverRepository
) : ViewModel() {
    private val _uiState = MutableLiveData<DiscoverState>().setIdle()
    val uiState: LiveData<DiscoverState> = _uiState

    init {
        getAnimeRecommendations()
    }

    private fun getAnimeRecommendations() {
        _uiState.setLoading()
        viewModelScope.launch {
            val animeRecommendationsResult = discoverRepository.getAnimeRecommendations()
            if (animeRecommendationsResult.isSuccess) {
                val animeRankingsResult = discoverRepository.getAnimeRankings()
                if (animeRankingsResult.isSuccess) {
                    val animeSeasonalsResult = discoverRepository.getAnimeSeasonals()
                    if (animeSeasonalsResult.isSuccess) {
                        if (animeRecommendationsResult.data?.data != null &&
                            animeRankingsResult.data?.data != null &&
                            animeSeasonalsResult.data?.data != null
                        ) {
                            _uiState.setFetchSuccess(
                                DiscoverAnimeListWrapper(
                                    animeOfCurrentSeason = animeSeasonalsResult.data.data,
                                    animeRankings = animeRankingsResult.data.data,
                                    animeSuggestions = animeRecommendationsResult.data.data
                                )
                            )
                        }
                    } else {
                        _uiState.setFailure(animeSeasonalsResult.error.errorMessage)
                    }
                } else {
                    _uiState.setFailure(animeRankingsResult.error.errorMessage)
                }
            } else {
                _uiState.setFailure(animeRecommendationsResult.error.errorMessage)
            }
        }
    }
}