package com.sharkaboi.mediahub.modules.manga_details.repository

import android.util.Log
import com.haroldadmin.cnradapter.NetworkResponse
import com.sharkaboi.mediahub.common.extensions.emptyString
import com.sharkaboi.mediahub.data.api.ApiConstants
import com.sharkaboi.mediahub.data.api.models.manga.MangaByIDResponse
import com.sharkaboi.mediahub.data.api.retrofit.MangaService
import com.sharkaboi.mediahub.data.api.retrofit.UserMangaService
import com.sharkaboi.mediahub.data.datastore.DataStoreRepository
import com.sharkaboi.mediahub.data.wrappers.MHError
import com.sharkaboi.mediahub.data.wrappers.MHTaskState
import com.sharkaboi.mediahub.modules.manga_details.util.MangaDetailsUpdateClass
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.withContext

class MangaDetailsRepositoryImpl(
    private val mangaService: MangaService,
    private val userMangaService: UserMangaService,
    private val dataStoreRepository: DataStoreRepository
) : MangaDetailsRepository {
    override suspend fun getMangaById(mangaId: Int): MHTaskState<MangaByIDResponse> =
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
                    val result = mangaService.getMangaByIdAsync(
                        authHeader = ApiConstants.BEARER_SEPARATOR + accessToken,
                        mangaId = mangaId
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

    override suspend fun updateMangaStatus(
        mangaDetailsUpdateClass: MangaDetailsUpdateClass
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
                    val result = userMangaService.updateMangaStatusAsync(
                        authHeader = ApiConstants.BEARER_SEPARATOR + accessToken,
                        mangaId = mangaDetailsUpdateClass.mangaId,
                        mangaStatus = mangaDetailsUpdateClass.mangaStatus?.name,
                        score = mangaDetailsUpdateClass.score,
                        numChaptersRead = mangaDetailsUpdateClass.numReadChapters,
                        numVolumesRead = mangaDetailsUpdateClass.numReadVolumes
                    ).await()
                    when (result) {
                        is NetworkResponse.Success -> {
                            Log.d(TAG, result.body.toString())
                            return@withContext MHTaskState(
                                isSuccess = true,
                                data = Unit,
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

    override suspend fun removeMangaFromList(mangaId: Int): MHTaskState<Unit> =
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
                    val result = userMangaService.deleteMangaFromListAsync(
                        authHeader = ApiConstants.BEARER_SEPARATOR + accessToken,
                        mangaId = mangaId
                    ).await()
                    when (result) {
                        is NetworkResponse.Success -> {
                            Log.d(TAG, result.body.toString())
                            return@withContext MHTaskState(
                                isSuccess = true,
                                data = Unit,
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
                            if (result.code == 404) {
                                return@withContext MHTaskState(
                                    isSuccess = false,
                                    data = null,
                                    error = MHError(
                                        "Manga isn't in your list", null
                                    )
                                )
                            }
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
        private const val TAG = "MangaDetailsRepository"
    }
}