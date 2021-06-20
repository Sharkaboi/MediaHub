package com.sharkaboi.mediahub.modules.settings.vm

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sharkaboi.mediahub.data.wrappers.MHTaskState
import com.sharkaboi.mediahub.modules.settings.repository.SettingsRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SettingsViewModel
@Inject constructor(
    private val settingsRepository: SettingsRepository
) : ViewModel() {

    private val _uiState: MutableLiveData<SettingsStates> =
        MutableLiveData<SettingsStates>().getDefault()
    val uiState: LiveData<SettingsStates> = _uiState

    fun logOutUser() {
        viewModelScope.launch {
            _uiState.setLoading()
            val result: MHTaskState<Unit> = settingsRepository.logOutUser()
            if (result.isSuccess) {
                _uiState.setLogOutSuccess()
            } else {
                _uiState.setLogOutFailure(result.error.errorMessage)
            }
        }
    }
}