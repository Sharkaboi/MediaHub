package com.sharkaboi.mediahub.modules.settings.vm

import androidx.lifecycle.MutableLiveData

sealed class SettingsStates {
    object Idle : SettingsStates()
    object Loading : SettingsStates()
    object LogOutSuccess : SettingsStates()
    data class LogOutFailure(val message: String) : SettingsStates()
}

fun MutableLiveData<SettingsStates>.setLoading() = this.apply {
    value = SettingsStates.Loading
}

fun MutableLiveData<SettingsStates>.getDefault() = this.apply {
    value = SettingsStates.Idle
}

fun MutableLiveData<SettingsStates>.setLogOutSuccess() =
    this.apply {
        value = SettingsStates.LogOutSuccess
    }

fun MutableLiveData<SettingsStates>.setLogOutFailure(message: String) = this.apply {
    value = SettingsStates.LogOutFailure(message)
}
