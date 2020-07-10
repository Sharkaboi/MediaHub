package com.cybershark.mediahub.ui.getstarted

import android.app.Application
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import androidx.preference.PreferenceManager
import com.cybershark.mediahub.R
import com.cybershark.mediahub.data.models.TraktTokenResultModel
import com.cybershark.mediahub.data.repository.GetStartedRepository
import com.cybershark.mediahub.util.Status
import kotlinx.coroutines.launch
import retrofit2.awaitResponse

class GetStartedViewModel(application: Application) : AndroidViewModel(application) {

    companion object {
        const val TAG = "GetStartedViewModel"
    }

    private val getStartedRepository by lazy { GetStartedRepository() }
    private val _authStatus = MutableLiveData<Int>().apply { value = Status.IDLE }
    val authStatus: LiveData<Int> = _authStatus

    fun getTokensFromAuthCode(code: String?) {
        if (code != null) {
            _authStatus.value = Status.LOADING
            viewModelScope.launch {
                val response = getStartedRepository.getTokenFromAuthCode(code, getClientID(), getClientSecret()).awaitResponse()
                if (response.isSuccessful) {
                    response.body()?.let { setTokenInSharedPrefs(it) }
                    _authStatus.value = Status.COMPLETED
                } else {
                    Log.e(TAG, "getTokensFromAuthCode: ${response.raw()}")
                    _authStatus.value = Status.ERROR
                }
            }
        } else {
            Toast.makeText(getApplication(), "Error : Invalid auth code received!", Toast.LENGTH_SHORT).show()
        }
    }

    private fun getClientSecret() = getApplication<Application>().getString(R.string.TRAKT_API_CLIENT_SECRET)
    private fun getClientID() = getApplication<Application>().getString(R.string.TRAKT_API_CLIENT_ID)

    private fun setTokenInSharedPrefs(tokenRequestResult: TraktTokenResultModel) {
        Log.e(GetStartedRepository.TAG, "setTokenInSharedPrefs: $tokenRequestResult")
        PreferenceManager.getDefaultSharedPreferences(getApplication())
            .edit()
            .putString("access_token", tokenRequestResult.access_token)
            .putString("refresh_token", tokenRequestResult.refresh_token)
            .putInt("expires_in", tokenRequestResult.expires_in)
            .putInt("created_at", tokenRequestResult.created_at)
            .putBoolean("traktTokenSet", true)
            .apply()
    }

}