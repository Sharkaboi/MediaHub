package com.sharkaboi.mediahub.modules.profile.repository

import android.util.Log
import com.haroldadmin.cnradapter.NetworkResponse
import com.sharkaboi.mediahub.data.api.ApiConstants
import com.sharkaboi.mediahub.data.api.models.user.UserDetailsResponse
import com.sharkaboi.mediahub.data.datastore.DataStoreRepository
import com.sharkaboi.mediahub.data.api.retrofit.UserService
import com.sharkaboi.mediahub.data.wrappers.MHError
import com.sharkaboi.mediahub.data.wrappers.MHTaskState
import com.sharkaboi.mediahub.common.extensions.emptyString
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.withContext

class ProfileRepository(
    private val userService: UserService,
    private val dataStoreRepository: DataStoreRepository
) {
    suspend fun getUserDetails(): MHTaskState<UserDetailsResponse> = withContext(Dispatchers.IO) {
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
                        Log.d(TAG, result.body.toString())
                        return@withContext MHTaskState(
                            isSuccess = true,
                            data = result.body,
                            error = MHError.nullError
                        )
                    }
                    is NetworkResponse.NetworkError -> {
                        Log.d(TAG, result.error.message ?: String.emptyString)
                        return@withContext MHTaskState(
                            isSuccess = false,
                            data = null,
                            error = MHError(result.error.message ?: "Error with network", null)
                        )
                    }
                    is NetworkResponse.ServerError -> {
                        Log.d(TAG, result.body.toString())
                        return@withContext MHTaskState(
                            isSuccess = false,
                            data = null,
                            error = MHError(
                                result.body?.message
                                    ?: "Error with status code : ${result.code}", null
                            )
                        )
                    }
                    is NetworkResponse.UnknownError -> {
                        Log.d(TAG, result.error.message ?: String.emptyString)
                        return@withContext MHTaskState(
                            isSuccess = false,
                            data = null,
                            error = MHError(result.error.message ?: "Error with parsing", null)
                        )
                    }
                }
            }
        } catch (e: Exception) {
            e.printStackTrace()
            Log.d(TAG, e.message ?: String.emptyString)
            return@withContext MHTaskState(
                isSuccess = false,
                data = null,
                error = MHError(e.message ?: String.emptyString, e)
            )
        }
    }

    companion object {
        private const val TAG = "ProfileRepository"
    }
}