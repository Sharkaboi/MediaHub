package com.sharkaboi.mediahub.modules.anime_details.repository

import com.haroldadmin.cnradapter.NetworkResponse
import com.sharkaboi.mediahub.common.extensions.emptyString
import com.sharkaboi.mediahub.common.extensions.ifNullOrBlank
import com.sharkaboi.mediahub.data.api.ApiConstants
import com.sharkaboi.mediahub.data.api.models.anime.AnimeByIDResponse
import com.sharkaboi.mediahub.data.api.retrofit.AnimeService
import com.sharkaboi.mediahub.data.api.retrofit.UserAnimeService
import com.sharkaboi.mediahub.data.datastore.DataStoreRepository
import com.sharkaboi.mediahub.data.wrappers.MHError
import com.sharkaboi.mediahub.data.wrappers.MHTaskState
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.withContext
import timber.log.Timber

class AnimeDetailsRepositoryImpl(
    private val animeService: AnimeService,
    private val userAnimeService: UserAnimeService,
    private val dataStoreRepository: DataStoreRepository
) : AnimeDetailsRepository {

    override suspend fun getAnimeById(animeId: Int): MHTaskState<AnimeByIDResponse> =
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
                    val result = animeService.getAnimeByIdAsync(
                        authHeader = ApiConstants.BEARER_SEPARATOR + accessToken,
                        animeId = animeId
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
                    error = MHError(e.message.ifNullOrBlank { "Unknown error" }, e)
                )
            }
        }

    override suspend fun updateAnimeStatus(
        animeId: Int,
        animeStatus: String?,
        score: Int?,
        numWatchedEps: Int?
    ): MHTaskState<Unit> =
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
                    val result = userAnimeService.updateAnimeStatusAsync(
                        authHeader = ApiConstants.BEARER_SEPARATOR + accessToken,
                        animeId = animeId,
                        animeStatus = animeStatus,
                        score = score,
                        numWatchedEps = numWatchedEps
                    ).await()
                    when (result) {
                        is NetworkResponse.Success -> {
                            Timber.d(result.body.toString())
                            return@withContext MHTaskState(
                                isSuccess = true,
                                data = Unit,
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

    override suspend fun removeAnimeFromList(
        animeId: Int
    ): MHTaskState<Unit> =
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
                    val result = userAnimeService.deleteAnimeFromListAsync(
                        authHeader = ApiConstants.BEARER_SEPARATOR + accessToken,
                        animeId = animeId
                    ).await()
                    when (result) {
                        is NetworkResponse.Success -> {
                            Timber.d(result.body.toString())
                            return@withContext MHTaskState(
                                isSuccess = true,
                                data = Unit,
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
                            if (result.code == 404) {
                                return@withContext MHTaskState(
                                    isSuccess = false,
                                    data = null,
                                    error = MHError(
                                        "Anime isn't in your list", null
                                    )
                                )
                            }
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
                    error = MHError(e.message.ifNullOrBlank { "Unknown error" }, e)
                )
            }
        }
}
