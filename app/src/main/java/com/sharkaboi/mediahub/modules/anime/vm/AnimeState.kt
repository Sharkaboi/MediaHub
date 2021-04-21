package com.sharkaboi.mediahub.modules.anime.vm

import androidx.lifecycle.MutableLiveData
import androidx.paging.PagingData
import com.sharkaboi.mediahub.common.data.api.models.useranime.UserAnimeListResponse
import com.sharkaboi.mediahub.common.extensions.emptyString
import kotlinx.coroutines.flow.Flow

sealed class AnimeState {
    object Idle : AnimeState()
    object Loading : AnimeState()
    data class PageFlowAttach(
        val flow: Flow<PagingData<UserAnimeListResponse.Data>>
    ) : AnimeState()

    data class AnimeError(
        val message: String
    ) : AnimeState()
}

fun MutableLiveData<AnimeState>.setLoading() = this.apply {
    value = AnimeState.Loading
}

fun MutableLiveData<AnimeState>.getDefault() = this.apply {
    value = AnimeState.Idle
}

fun MutableLiveData<AnimeState>.setFetchSuccess(flow: Flow<PagingData<UserAnimeListResponse.Data>>) =
    this.apply {
        value = AnimeState.PageFlowAttach(flow)
    }

fun MutableLiveData<AnimeState>.setAnimeFailure(error: Throwable) = this.apply {
    value = AnimeState.AnimeError(error.message ?: String.emptyString)
}

