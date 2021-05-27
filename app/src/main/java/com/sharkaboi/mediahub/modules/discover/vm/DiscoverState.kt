package com.sharkaboi.mediahub.modules.discover.vm

import androidx.lifecycle.MutableLiveData
import com.sharkaboi.mediahub.modules.discover.util.DiscoverAnimeListWrapper

sealed class DiscoverState {
    object Idle : DiscoverState()
    object Loading : DiscoverState()
    data class AnimeDetailsSuccess(val data: DiscoverAnimeListWrapper) : DiscoverState()
    data class AnimeDetailsFailure(val message: String) : DiscoverState()
}

fun MutableLiveData<DiscoverState>.setLoading() = this.apply {
    value = DiscoverState.Loading
}

fun MutableLiveData<DiscoverState>.setIdle() = this.apply {
    value = DiscoverState.Idle
}

fun MutableLiveData<DiscoverState>.setFetchSuccess(data: DiscoverAnimeListWrapper) =
    this.apply {
        value = DiscoverState.AnimeDetailsSuccess(data)
    }

fun MutableLiveData<DiscoverState>.setFailure(message: String) = this.apply {
    value = DiscoverState.AnimeDetailsFailure(message)
}
