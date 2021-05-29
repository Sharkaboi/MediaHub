package com.sharkaboi.mediahub.modules.splash.vm

import android.content.SharedPreferences
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sharkaboi.mediahub.common.data.sharedpref.SharedPreferencesKeys
import com.sharkaboi.mediahub.modules.splash.repository.SplashRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.launch
import java.util.*
import javax.inject.Inject

@HiltViewModel
class SplashViewModel
@Inject constructor(
    private val splashRepository: SplashRepository,
    sharedPreferences: SharedPreferences
) : ViewModel() {
    private val accessTokenFlow = splashRepository.accessTokenFlow
    private val expiresInFlow = splashRepository.expiresInFlow
    private val _splashState = MutableLiveData<SplashState>().getDefault()
    private val isDarkMode = sharedPreferences.getBoolean(SharedPreferencesKeys.DARK_MODE, false)
    val splashState: LiveData<SplashState> = _splashState

    init {
        _splashState.setLoading()
        viewModelScope.launch {
            val accessToken: String? = accessTokenFlow.firstOrNull()
            val expiresIn: Date = expiresInFlow.firstOrNull() ?: Date(0)
            val nowWithBuffer = Calendar.getInstance().apply {
                add(Calendar.DAY_OF_MONTH, 3)
            }.time
            _splashState.setFetchComplete(
                isAccessTokenValid = accessToken != null,
                hasExpired = expiresIn <= nowWithBuffer,
                isDarkMode = isDarkMode
            )
        }
    }

    fun refreshToken() {
        viewModelScope.launch {
            val result = splashRepository.refreshToken()
            if (result) {
                _splashState.setFetchComplete(
                    isAccessTokenValid = true,
                    hasExpired = false,
                    isDarkMode = isDarkMode
                )
            } else {
                _splashState.setLoginExpired()
            }
        }
    }
}