package com.sharkaboi.mediahub.modules.splash.repository

import com.haroldadmin.cnradapter.NetworkResponse
import com.sharkaboi.mediahub.BuildConfig
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
        val refreshToken: String = refreshTokenFlow.firstOrNull() ?: return@withContext false

        val response = authService.refreshTokenAsync(
            refreshToken = refreshToken,
            clientId = BuildConfig.clientId
        ).await()

        Timber.d(response.toString())
        return@withContext when (response) {
            is NetworkResponse.Success -> {
                dataStoreRepository.setAccessToken(response.body.accessToken)
                dataStoreRepository.setExpireIn()
                dataStoreRepository.setRefreshToken(response.body.refreshToken)
                true
            }
            else -> false
        }
    }
}
