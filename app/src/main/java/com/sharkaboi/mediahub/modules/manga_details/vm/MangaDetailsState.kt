package com.sharkaboi.mediahub.modules.manga_details.vm

import androidx.lifecycle.MutableLiveData
import com.sharkaboi.mediahub.data.api.models.manga.MangaByIDResponse

sealed class MangaDetailsState {
    object Idle : MangaDetailsState()
    object Loading : MangaDetailsState()
    data class FetchSuccess(val mangaByIDResponse: MangaByIDResponse) : MangaDetailsState()
    data class MangaDetailsFailure(val message: String) : MangaDetailsState()
}

fun MutableLiveData<MangaDetailsState>.setLoading() = this.apply {
    value = MangaDetailsState.Loading
}

fun MutableLiveData<MangaDetailsState>.getDefault() = this.apply {
    value = MangaDetailsState.Idle
}

fun MutableLiveData<MangaDetailsState>.setFetchSuccess(mangaByIDResponse: MangaByIDResponse) =
    this.apply {
        value = MangaDetailsState.FetchSuccess(mangaByIDResponse)
    }

fun MutableLiveData<MangaDetailsState>.setFailure(message: String) = this.apply {
    value = MangaDetailsState.MangaDetailsFailure(message)
}
