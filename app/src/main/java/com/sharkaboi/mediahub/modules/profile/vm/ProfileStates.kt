package com.sharkaboi.mediahub.modules.profile.vm

import androidx.lifecycle.MutableLiveData
import com.sharkaboi.mediahub.common.data.api.models.user.UserDetailsResponse

sealed class ProfileStates {
    object Idle : ProfileStates()
    object Loading : ProfileStates()
    data class FetchSuccess(val userDetailsResponse: UserDetailsResponse) : ProfileStates()
    data class ProfileFailure(val message: String) : ProfileStates()
}

fun MutableLiveData<ProfileStates>.setLoading() = this.apply {
    value = ProfileStates.Loading
}

fun MutableLiveData<ProfileStates>.getDefault() = this.apply {
    value = ProfileStates.Idle
}

fun MutableLiveData<ProfileStates>.setFetchSuccess(userDetailsResponse: UserDetailsResponse) =
    this.apply {
        value = ProfileStates.FetchSuccess(userDetailsResponse)
    }

fun MutableLiveData<ProfileStates>.setProfileFailure(message: String) = this.apply {
    value = ProfileStates.ProfileFailure(message)
}
