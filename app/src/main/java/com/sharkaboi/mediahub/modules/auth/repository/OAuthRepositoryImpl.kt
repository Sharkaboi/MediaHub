package com.sharkaboi.mediahub.modules.auth.repository

import com.haroldadmin.cnradapter.NetworkResponse
import com.sharkaboi.mediahub.BuildConfig
import com.sharkaboi.mediahub.common.extensions.emptyString
import com.sharkaboi.mediahub.data.api.retrofit.AuthService
import com.sharkaboi.mediahub.data.datastore.DataStoreRepository
import com.sharkaboi.mediahub.data.wrappers.MHError
import com.sharkaboi.mediahub.data.wrappers.MHTaskState
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import timber.log.Timber

class OAuthRepositoryImpl(
    private val authService: AuthService,
    private val dataStoreRepository: DataStoreRepository
) : OAuthRepository {

    override suspend fun getAccessToken(code: String, codeVerifier: String): MHTaskState<Unit> =
        withContext(Dispatchers.IO) {
            val response = authService.getAccessTokenAsync(
                clientId = BuildConfig.clientId,
                code = code,
                codeVerifier = codeVerifier
            ).await()
            when (response) {
                is NetworkResponse.Success -> {
                    Timber.d(response.body.toString())
                    dataStoreRepository.setAccessToken(response.body.accessToken)
                    dataStoreRepository.setExpireIn()
                    dataStoreRepository.setRefreshToken(response.body.refreshToken)
                    return@withContext MHTaskState(
                        isSuccess = true,
                        data = null,
                        error = MHError.EmptyError
                    )
                }
                is NetworkResponse.ServerError -> {
                    Timber.d(response.body.toString())
                    return@withContext MHTaskState(
                        isSuccess = false,
                        data = null,
                        error = response.body?.message?.let { MHError(it) }
                            ?: MHError.apiErrorWithCode(response.code)
                    )
                }
                is NetworkResponse.NetworkError -> {
                    Timber.d(response.error.message ?: String.emptyString)
                    return@withContext MHTaskState(
                        isSuccess = false,
                        data = null,
                        error = response.error.message?.let { MHError(it) }
                            ?: MHError.NetworkError
                    )
                }
                is NetworkResponse.UnknownError -> {
                    Timber.d(response.error.message ?: String.emptyString)
                    return@withContext MHTaskState(
                        isSuccess = false,
                        data = null,
                        error = response.error.message?.let { MHError(it) }
                            ?: MHError.ParsingError
                    )
                }
            }
        }
}
