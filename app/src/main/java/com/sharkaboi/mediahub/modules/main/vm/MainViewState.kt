package com.sharkaboi.mediahub.modules.main.vm

import androidx.lifecycle.MutableLiveData

sealed class MainViewState {
    object AuthTokenValid : MainViewState()
    object AuthTokenInValid : MainViewState()
}

fun MutableLiveData<MainViewState>.setValidAuthToken() = this.apply {
    value = MainViewState.AuthTokenValid
}

fun MutableLiveData<MainViewState>.setInValidAuthToken() = this.apply {
    value = MainViewState.AuthTokenInValid
}
