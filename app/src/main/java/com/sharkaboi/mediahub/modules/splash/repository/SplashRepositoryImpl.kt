package com.sharkaboi.mediahub.modules.splash.repository

import com.haroldadmin.cnradapter.NetworkResponse
import com.sharkaboi.mediahub.BuildConfig
import com.sharkaboi.mediahub.common.extensions.emptyString
import com.sharkaboi.mediahub.data.api.retrofit.AuthService
import com.sharkaboi.mediahub.data.datastore.DataStoreRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.withContext
import timber.log.Timber
import java.util.*

class SplashRepositoryImpl(
    private val dataStoreRepository: DataStoreRepository,
    private val authService: AuthService
) : SplashRepository {
    override val accessTokenFlow: Flow<String?> = dataStoreRepository.accessTokenFlow
    override val expiresInFlow: Flow<Date> = dataStoreRepository.expiresInFlow
    override val refreshTokenFlow: Flow<String> = dataStoreRepository.refreshTokenFlow

    override suspend fun refreshToken(): Boolean = withContext(Dispatchers.IO) {
        val refreshToken: String? = refreshTokenFlow.firstOrNull()
        if (refreshToken == null) {
            return@withContext false
        } else {
            val response = authService.refreshTokenAsync(
                refreshToken = refreshToken,
                clientId = BuildConfig.clientId
            ).await()
            when (response) {
                is NetworkResponse.Success -> {
                    Timber.d(response.body.toString())
                    dataStoreRepository.setAccessToken(response.body.accessToken)
                    dataStoreRepository.setExpireIn()
                    dataStoreRepository.setRefreshToken(response.body.refreshToken)
                    return@withContext true
                }
                is NetworkResponse.ServerError -> {
                    Timber.d(response.body.toString())
                    return@withContext false
                }
                is NetworkResponse.NetworkError -> {
                    Timber.d(response.error.message ?: String.emptyString)
                    return@withContext false
                }
                is NetworkResponse.UnknownError -> {
                    Timber.d(response.error.message ?: String.emptyString)
                    return@withContext false
                }
            }
        }
    }
}
