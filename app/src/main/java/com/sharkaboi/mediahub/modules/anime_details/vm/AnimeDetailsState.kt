package com.sharkaboi.mediahub.modules.anime_details.vm

import androidx.lifecycle.MutableLiveData
import com.sharkaboi.mediahub.common.data.api.models.anime.AnimeByIDResponse

sealed class AnimeDetailsState {
    object Idle : AnimeDetailsState()
    object Loading : AnimeDetailsState()
    data class FetchSuccess(val animeByIDResponse: AnimeByIDResponse) : AnimeDetailsState()
    data class AnimeDetailsFailure(val message: String) : AnimeDetailsState()
}

fun MutableLiveData<AnimeDetailsState>.setLoading() = this.apply {
    value = AnimeDetailsState.Loading
}

fun MutableLiveData<AnimeDetailsState>.getDefault() = this.apply {
    value = AnimeDetailsState.Idle
}

fun MutableLiveData<AnimeDetailsState>.setFetchSuccess(animeByIDResponse: AnimeByIDResponse) =
    this.apply {
        value = AnimeDetailsState.FetchSuccess(animeByIDResponse)
    }

fun MutableLiveData<AnimeDetailsState>.setFailure(message: String) = this.apply {
    value = AnimeDetailsState.AnimeDetailsFailure(message)
}
