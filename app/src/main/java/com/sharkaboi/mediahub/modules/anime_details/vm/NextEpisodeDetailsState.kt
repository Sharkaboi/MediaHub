package com.sharkaboi.mediahub.modules.anime_details.vm

import androidx.lifecycle.MutableLiveData
import com.sharkaboi.GetNextAiringAnimeEpisodeQuery

sealed class NextEpisodeDetailsState {
    object Idle : NextEpisodeDetailsState()
    object Loading : NextEpisodeDetailsState()
    data class FetchSuccess(val nextAiringEpisode: GetNextAiringAnimeEpisodeQuery.Media) :
        NextEpisodeDetailsState()

    data class NextEpisodeDetailsFailure(val message: String) : NextEpisodeDetailsState()
}

fun MutableLiveData<NextEpisodeDetailsState>.setLoading() = this.apply {
    value = NextEpisodeDetailsState.Loading
}

fun MutableLiveData<NextEpisodeDetailsState>.getDefault() = this.apply {
    value = NextEpisodeDetailsState.Idle
}

fun MutableLiveData<NextEpisodeDetailsState>.setFetchSuccess(nextAiringEpisode: GetNextAiringAnimeEpisodeQuery.Media) =
    this.apply {
        value = NextEpisodeDetailsState.FetchSuccess(nextAiringEpisode)
    }

fun MutableLiveData<NextEpisodeDetailsState>.setFailure(message: String) = this.apply {
    value = NextEpisodeDetailsState.NextEpisodeDetailsFailure(message)
}
