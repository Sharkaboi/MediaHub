package com.sharkaboi.mediahub.modules.discover.repository

import android.util.Log
import com.haroldadmin.cnradapter.NetworkResponse
import com.sharkaboi.mediahub.common.data.api.ApiConstants
import com.sharkaboi.mediahub.common.data.api.enums.getAnimeSeason
import com.sharkaboi.mediahub.common.data.api.models.anime.AnimeRankingResponse
import com.sharkaboi.mediahub.common.data.api.models.anime.AnimeSeasonalResponse
import com.sharkaboi.mediahub.common.data.api.models.anime.AnimeSuggestionsResponse
import com.sharkaboi.mediahub.common.data.datastore.DataStoreRepository
import com.sharkaboi.mediahub.common.data.retrofit.AnimeService
import com.sharkaboi.mediahub.common.data.wrappers.MHError
import com.sharkaboi.mediahub.common.data.wrappers.MHTaskState
import com.sharkaboi.mediahub.common.extensions.emptyString
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.withContext
import java.time.LocalDate

class DiscoverRepository(
    private val animeService: AnimeService,
    private val dataStoreRepository: DataStoreRepository
) {
    suspend fun getAnimeRecommendations(): MHTaskState<AnimeSuggestionsResponse> =
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
                    val result = animeService.getAnimeSuggestionsAsync(
                        authHeader = ApiConstants.BEARER_SEPARATOR + accessToken,
                        limit = ApiConstants.API_PAGE_LIMIT,
                        offset = ApiConstants.API_START_OFFSET
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

    suspend fun getAnimeSeasonals(): MHTaskState<AnimeSeasonalResponse> =
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
                    val today = LocalDate.now()
                    val result = animeService.getAnimeBySeasonAsync(
                        authHeader = ApiConstants.BEARER_SEPARATOR + accessToken,
                        year = today.year,
                        season = today.getAnimeSeason().name,
                        limit = ApiConstants.API_PAGE_LIMIT,
                        offset = ApiConstants.API_START_OFFSET
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

    suspend fun getAnimeRankings(): MHTaskState<AnimeRankingResponse> =
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
                    val result = animeService.getAnimeRankingAsync(
                        authHeader = ApiConstants.BEARER_SEPARATOR + accessToken,
                        limit = ApiConstants.API_PAGE_LIMIT,
                        offset = ApiConstants.API_START_OFFSET
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
        private const val TAG = "DiscoverRepository"
    }
}