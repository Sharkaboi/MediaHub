package com.sharkaboi.mediahub.modules.auth.vm

import androidx.lifecycle.MutableLiveData

sealed class OAuthState {
    object Idle : OAuthState()
    data class RedirectToAuth(val codeChallenge: String, val state: String) : OAuthState()
    object Loading : OAuthState()
    object OAuthSuccess : OAuthState()
    data class OAuthFailure(val message: String) : OAuthState()
}

fun MutableLiveData<OAuthState>.setRedirectToAuth(codeChallenge: String, state: String) =
    this.apply {
        value = OAuthState.RedirectToAuth(codeChallenge, state)
    }

fun MutableLiveData<OAuthState>.setLoading() = this.apply {
    value = OAuthState.Loading
}

fun MutableLiveData<OAuthState>.getDefault() = this.apply {
    value = OAuthState.Idle
}

fun MutableLiveData<OAuthState>.setOAuthSuccess() = this.apply {
    value = OAuthState.OAuthSuccess
}

fun MutableLiveData<OAuthState>.setOAuthFailure(message: String) = this.apply {
    value = OAuthState.OAuthFailure(message)
}
