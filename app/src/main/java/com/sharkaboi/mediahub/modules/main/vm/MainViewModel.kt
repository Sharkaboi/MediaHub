package com.sharkaboi.mediahub.modules.main.vm

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sharkaboi.mediahub.data.datastore.DataStoreRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel
@Inject constructor(
    dataStoreRepository: DataStoreRepository
) : ViewModel() {
    private val _uiState = MutableLiveData<MainViewState>()
    val uiState: LiveData<MainViewState> = _uiState

    private val authTokenFlow = dataStoreRepository.accessTokenFlow

    init {
        viewModelScope.launch {
            authTokenFlow.collectLatest {
                if (it != null) {
                    _uiState.setValidAuthToken()
                } else {
                    _uiState.setInValidAuthToken()
                }
            }
        }
    }
}
