package com.sharkaboi.mediahub.modules.splash.vm

import androidx.lifecycle.MutableLiveData

sealed class SplashState {
    object Idle : SplashState()
    object Loading : SplashState()
    data class FetchComplete(
        val isAccessTokenValid: Boolean,
        val hasExpired: Boolean,
        val isDarkMode: Boolean
    ) : SplashState()

    object LoginExpired : SplashState()
}

fun MutableLiveData<SplashState>.getDefault() = this.apply {
    value = SplashState.Idle
}

fun MutableLiveData<SplashState>.setLoading() = this.apply {
    value = SplashState.Loading
}

fun MutableLiveData<SplashState>.setLoginExpired() = this.apply {
    value = SplashState.LoginExpired
}

fun MutableLiveData<SplashState>.setFetchComplete(
    isAccessTokenValid: Boolean,
    hasExpired: Boolean,
    isDarkMode: Boolean
) = this.apply {
    value = SplashState.FetchComplete(
        isAccessTokenValid,
        hasExpired,
        isDarkMode
    )
}
