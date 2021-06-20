package com.sharkaboi.mediahub.modules.auth.repository

import android.util.Log
import com.haroldadmin.cnradapter.NetworkResponse
import com.sharkaboi.mediahub.BuildConfig
import com.sharkaboi.mediahub.data.datastore.DataStoreRepository
import com.sharkaboi.mediahub.data.api.retrofit.AuthService
import com.sharkaboi.mediahub.common.extensions.emptyString
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class OAuthRepository(
    private val authService: AuthService,
    private val dataStoreRepository: DataStoreRepository
) {

    suspend fun getAccessToken(code: String, codeVerifier: String): String? =
        withContext(Dispatchers.IO) {
            val response = authService.getAccessTokenAsync(
                clientId = BuildConfig.clientId,
                code = code,
                codeVerifier = codeVerifier
            ).await()
            when (response) {
                is NetworkResponse.Success -> {
                    Log.d(TAG, response.body.toString())
                    dataStoreRepository.setAccessToken(response.body.accessToken)
                    dataStoreRepository.setExpireIn()
                    dataStoreRepository.setRefreshToken(response.body.refreshToken)
                    return@withContext null
                }
                is NetworkResponse.ServerError -> {
                    Log.d(TAG, response.body.toString())
                    return@withContext response.body?.message
                        ?: "Error with status code : ${response.code}"
                }
                is NetworkResponse.NetworkError -> {
                    Log.d(TAG, response.error.message ?: String.emptyString)
                    return@withContext response.error.message ?: "Error with network"
                }
                is NetworkResponse.UnknownError -> {
                    Log.d(TAG, response.error.message ?: String.emptyString)
                    return@withContext response.error.message ?: "Error with parsing"
                }
            }
        }

    companion object {
        private const val TAG = "OAuthRepository"
    }
}