package com.sharkaboi.mediahub.modules.profile.repository

import com.haroldadmin.cnradapter.NetworkResponse
import com.sharkaboi.mediahub.common.extensions.getCatching
import com.sharkaboi.mediahub.data.api.constants.ApiConstants
import com.sharkaboi.mediahub.data.api.models.user.UserDetailsResponse
import com.sharkaboi.mediahub.data.api.retrofit.UserService
import com.sharkaboi.mediahub.data.datastore.DataStoreRepository
import com.sharkaboi.mediahub.data.wrappers.MHError
import com.sharkaboi.mediahub.data.wrappers.MHTaskState
import kotlinx.coroutines.flow.firstOrNull
import timber.log.Timber

class ProfileRepositoryImpl(
    private val userService: UserService,
    private val dataStoreRepository: DataStoreRepository
) : ProfileRepository {

    override suspend fun getUserDetails(): MHTaskState<UserDetailsResponse> =
        getCatching {
            val accessToken: String = dataStoreRepository.accessTokenFlow.firstOrNull()
                ?: return@getCatching MHTaskState(
                    isSuccess = false,
                    data = null,
                    error = MHError.LoginExpiredError
                )

            val result = userService.getUserDetailsAsync(
                authHeader = ApiConstants.BEARER_SEPARATOR + accessToken
            ).await()

            Timber.d(result.toString())
            return@getCatching when (result) {
                is NetworkResponse.Success -> {
                    MHTaskState(
                        isSuccess = true,
                        data = result.body,
                        error = MHError.EmptyError
                    )
                }
                is NetworkResponse.NetworkError -> {
                    MHTaskState(
                        isSuccess = false,
                        data = null,
                        error = MHError.getError(result.error.message, MHError.NetworkError)
                    )
                }
                is NetworkResponse.ServerError -> {
                    MHTaskState(
                        isSuccess = false,
                        data = null,
                        error = MHError.getError(
                            result.body?.message,
                            MHError.apiErrorWithCode(result.code)
                        )
                    )
                }
                is NetworkResponse.UnknownError -> {
                    MHTaskState(
                        isSuccess = false,
                        data = null,
                        error = MHError.getError(result.error.message, MHError.ParsingError)
                    )
                }
            }
        }
}
