package com.sharkaboi.mediahub.modules.discover.vm

import androidx.lifecycle.MutableLiveData

sealed class DiscoverState {
    object Idle : DiscoverState()
    object Loading : DiscoverState()
    data class AnimeDetailsFailure(val message: String) : DiscoverState()
}

fun MutableLiveData<DiscoverState>.setLoading() = this.apply {
    value = DiscoverState.Loading
}

fun MutableLiveData<DiscoverState>.setIdle() = this.apply {
    value = DiscoverState.Idle
}

fun MutableLiveData<DiscoverState>.setFailure(message: String) = this.apply {
    value = DiscoverState.AnimeDetailsFailure(message)
}
