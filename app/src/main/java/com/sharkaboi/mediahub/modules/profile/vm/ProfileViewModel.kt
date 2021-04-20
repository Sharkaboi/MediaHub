package com.sharkaboi.mediahub.modules.profile.vm

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sharkaboi.mediahub.common.data.api.models.user.UserDetailsResponse
import com.sharkaboi.mediahub.common.data.wrappers.MHTaskState
import com.sharkaboi.mediahub.modules.profile.repository.ProfileRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProfileViewModel
@Inject constructor(
    private val profileRepository: ProfileRepository
) : ViewModel() {

    private val _uiState: MutableLiveData<ProfileStates> =
        MutableLiveData<ProfileStates>().getDefault()
    val uiState: LiveData<ProfileStates> = _uiState

    fun getUserDetails() {
        viewModelScope.launch {
            _uiState.setLoading()
            val result: MHTaskState<UserDetailsResponse> = profileRepository.getUserDetails()
            if (result.isSuccess) {
                _uiState.setFetchSuccess(result.data!!)
            } else {
                _uiState.setProfileFailure(result.error.errorMessage)
            }
        }
    }

    fun logOutUser() {
        viewModelScope.launch {
            _uiState.setLoading()
            val result: MHTaskState<Unit> = profileRepository.logOutUser()
            if (result.isSuccess) {
                _uiState.setLogOutSuccess()
            } else {
                _uiState.setProfileFailure(result.error.errorMessage)
            }
        }
    }
}