package com.sharkaboi.mediahub.modules.profile.repository

import com.haroldadmin.cnradapter.NetworkResponse
import com.sharkaboi.mediahub.common.extensions.emptyString
import com.sharkaboi.mediahub.common.extensions.ifNullOrBlank
import com.sharkaboi.mediahub.data.api.ApiConstants
import com.sharkaboi.mediahub.data.api.models.user.UserDetailsResponse
import com.sharkaboi.mediahub.data.api.retrofit.UserService
import com.sharkaboi.mediahub.data.datastore.DataStoreRepository
import com.sharkaboi.mediahub.data.wrappers.MHError
import com.sharkaboi.mediahub.data.wrappers.MHTaskState
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.withContext
import timber.log.Timber

class ProfileRepositoryImpl(
    private val userService: UserService,
    private val dataStoreRepository: DataStoreRepository
) : ProfileRepository {

    override suspend fun getUserDetails(): MHTaskState<UserDetailsResponse> =
        withContext(Dispatchers.IO) {
            try {
                val accessToken: String? = dataStoreRepository.accessTokenFlow.firstOrNull()
                if (accessToken == null) {
                    return@withContext MHTaskState(
                        isSuccess = false,
                        data = null,
                        error = MHError("Log in has expired, Log in again.", null)
                    )
                } else {
                    val result = userService.getUserDetailsAsync(
                        authHeader = ApiConstants.BEARER_SEPARATOR + accessToken
                    ).await()
                    when (result) {
                        is NetworkResponse.Success -> {
                            Timber.d(result.body.toString())
                            return@withContext MHTaskState(
                                isSuccess = true,
                                data = result.body,
                                error = MHError.EmptyError
                            )
                        }
                        is NetworkResponse.NetworkError -> {
                            Timber.d(result.error.message ?: String.emptyString)
                            return@withContext MHTaskState(
                                isSuccess = false,
                                data = null,
                                error = MHError(
                                    result.error.message.ifNullOrBlank { "Error with network" },
                                    null
                                )
                            )
                        }
                        is NetworkResponse.ServerError -> {
                            Timber.d(result.body.toString())
                            return@withContext MHTaskState(
                                isSuccess = false,
                                data = null,
                                error = MHError(
                                    result.body?.message.ifNullOrBlank { "Error with status code : ${result.code}" },
                                    null
                                )
                            )
                        }
                        is NetworkResponse.UnknownError -> {
                            Timber.d(result.error.message ?: String.emptyString)
                            return@withContext MHTaskState(
                                isSuccess = false,
                                data = null,
                                error = MHError(
                                    result.error.message.ifNullOrBlank { "Error with parsing" },
                                    null
                                )
                            )
                        }
                    }
                }
            } catch (e: Exception) {
                e.printStackTrace()
                Timber.d(e.message ?: String.emptyString)
                return@withContext MHTaskState(
                    isSuccess = false,
                    data = null,
                    error = MHError(e.message.ifNullOrBlank { "Unknown Error" }, e)
                )
            }
        }
}
