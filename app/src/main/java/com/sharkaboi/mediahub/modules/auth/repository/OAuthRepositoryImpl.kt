package com.sharkaboi.mediahub.modules.auth.repository

import com.haroldadmin.cnradapter.NetworkResponse
import com.sharkaboi.mediahub.BuildConfig
import com.sharkaboi.mediahub.common.extensions.getCatching
import com.sharkaboi.mediahub.data.api.retrofit.AuthService
import com.sharkaboi.mediahub.data.datastore.DataStoreRepository
import com.sharkaboi.mediahub.data.wrappers.MHError
import com.sharkaboi.mediahub.data.wrappers.MHTaskState
import timber.log.Timber

class OAuthRepositoryImpl(
    private val authService: AuthService,
    private val dataStoreRepository: DataStoreRepository
) : OAuthRepository {

    override suspend fun getAccessToken(
        code: String,
        codeVerifier: String
    ): MHTaskState<Unit> = getCatching {
        val response = authService.getAccessTokenAsync(
            clientId = BuildConfig.clientId,
            code = code,
            codeVerifier = codeVerifier
        ).await()
        Timber.d(response.toString())

        return@getCatching when (response) {
            is NetworkResponse.Success -> {
                dataStoreRepository.setAccessToken(response.body.accessToken)
                dataStoreRepository.setExpireIn()
                dataStoreRepository.setRefreshToken(response.body.refreshToken)
                MHTaskState(
                    isSuccess = true,
                    data = null,
                    error = MHError.EmptyError
                )
            }
            is NetworkResponse.ServerError -> {
                MHTaskState(
                    isSuccess = false,
                    data = null,
                    error = MHError.getError(
                        response.body?.message,
                        MHError.apiErrorWithCode(response.code)
                    )
                )
            }
            is NetworkResponse.NetworkError -> {
                MHTaskState(
                    isSuccess = false,
                    data = null,
                    error = MHError.getError(response.error.message, MHError.NetworkError)
                )
            }
            is NetworkResponse.UnknownError -> {
                MHTaskState(
                    isSuccess = false,
                    data = null,
                    error = MHError.getError(response.error.message, MHError.ParsingError)
                )
            }
        }
    }
}
