package com.sharkaboi.mediahub.modules.auth

import android.util.Log
import androidx.lifecycle.*
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import java.util.*
import javax.inject.Inject
import kotlin.random.Random

@HiltViewModel
class OAuthViewModel
@Inject constructor(
    private val oAuthRepository: OAuthRepository,
    savedStateHandle: SavedStateHandle,
) : ViewModel() {
    private val _oAuthState = MutableLiveData<OAuthState>().getDefault()
    val oAuthState: LiveData<OAuthState> = _oAuthState
    private var codeChallenge: String
    private var state: String

    init {
        val savedCodeChallenge = savedStateHandle.get<String>(CODE_CHALLENGE)
        val savedState = savedStateHandle.get<String>(STATE)
        Log.d(TAG, "challenge : $savedCodeChallenge")
        Log.d(TAG, "state : $savedState")

        if (savedCodeChallenge == null || savedState == null) {
            codeChallenge = getCodeChallengeString()
            savedStateHandle[CODE_CHALLENGE] = codeChallenge
            state = "Request${Calendar.getInstance().timeInMillis}"
            savedStateHandle[STATE] = state
        } else {
            codeChallenge = savedCodeChallenge
            state = savedState
        }
    }

    private fun getCodeChallengeString(): String {
        val charPool: List<Char> = ('a'..'z') + ('A'..'Z') + ('0'..'9')
        return (1..128)
            .map { charPool[Random.nextInt(0, charPool.size)] }
            .joinToString("")
    }

    fun receivedAuthToken(code: String) {
        _oAuthState.setLoading()
        viewModelScope.launch {
            Log.d(TAG, "challenge retrieved: $codeChallenge")
            Log.d(TAG, "code received: $code")
            val result = oAuthRepository.getAccessToken(code, codeChallenge)
            if (result == null) {
                _oAuthState.setOAuthSuccess()
            } else {
                _oAuthState.setOAuthFailure(result)
            }
        }
    }

    fun redirectToAuth() {
        _oAuthState.setRedirectToAuth(
            codeChallenge = codeChallenge,
            state = state
        )
    }

    companion object {
        private const val CODE_CHALLENGE = "codeChallenge"
        private const val STATE = "state"
        private const val TAG = "OAuthViewModel"
    }
}