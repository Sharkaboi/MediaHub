package com.sharkaboi.mediahub.modules.splash

import android.util.Log
import com.haroldadmin.cnradapter.NetworkResponse
import com.sharkaboi.mediahub.BuildConfig
import com.sharkaboi.mediahub.common.data.datastore.DataStoreRepository
import com.sharkaboi.mediahub.common.data.retrofit.AuthService
import com.sharkaboi.mediahub.common.extensions.emptyString
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.withContext
import java.util.*

class SplashRepository(
    private val dataStoreRepository: DataStoreRepository,
    private val authService: AuthService
) {
    val accessTokenFlow: Flow<String?> = dataStoreRepository.accessTokenFlow
    val expiresInFlow: Flow<Date> = dataStoreRepository.expiresInFlow
    private val refreshTokenFlow: Flow<String> = dataStoreRepository.refreshTokenFlow

    suspend fun refreshToken(): Boolean = withContext(Dispatchers.IO) {
        val refreshToken: String? = accessTokenFlow.firstOrNull()
        if (refreshToken == null) {
            return@withContext false
        } else {
            val response = authService.refreshTokenAsync(
                refreshToken = refreshTokenFlow.firstOrNull() ?: String.emptyString,
                clientId = BuildConfig.clientId
            ).await()
            when (response) {
                is NetworkResponse.Success -> {
                    Log.d(TAG, response.body.toString())
                    dataStoreRepository.setAccessToken(response.body.accessToken)
                    dataStoreRepository.setExpireIn()
                    dataStoreRepository.setRefreshToken(response.body.refreshToken)
                    return@withContext true
                }
                is NetworkResponse.ServerError -> {
                    Log.d(TAG, response.body.toString())
                    return@withContext false
                }
                is NetworkResponse.NetworkError -> {
                    Log.d(TAG, response.error.message ?: String.emptyString)
                    return@withContext false
                }
                is NetworkResponse.UnknownError -> {
                    Log.d(TAG, response.error.message ?: String.emptyString)
                    return@withContext false
                }
            }
        }
    }

    companion object {
        private const val TAG = "SplashRepository"
    }
}