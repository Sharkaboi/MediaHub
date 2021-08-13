package com.sharkaboi.mediahub.modules.manga_details.repository

import com.haroldadmin.cnradapter.NetworkResponse
import com.sharkaboi.mediahub.common.extensions.emptyString
import com.sharkaboi.mediahub.data.api.constants.ApiConstants
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
import timber.log.Timber

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
                        error = MHError.LoginExpiredError
                    )
                } else {
                    val result = mangaService.getMangaByIdAsync(
                        authHeader = ApiConstants.BEARER_SEPARATOR + accessToken,
                        mangaId = mangaId
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
                                error = result.error.message?.let { MHError(it) }
                                    ?: MHError.NetworkError
                            )
                        }
                        is NetworkResponse.ServerError -> {
                            Timber.d(result.body.toString())
                            return@withContext MHTaskState(
                                isSuccess = false,
                                data = null,
                                error = result.body?.message?.let { MHError(it) }
                                    ?: MHError.apiErrorWithCode(result.code)
                            )
                        }
                        is NetworkResponse.UnknownError -> {
                            Timber.d(result.error.message ?: String.emptyString)
                            return@withContext MHTaskState(
                                isSuccess = false,
                                data = null,
                                error = result.error.message?.let { MHError(it) }
                                    ?: MHError.ParsingError
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
                    error = e.message?.let { MHError(it) } ?: MHError.UnknownError
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
                        error = MHError.LoginExpiredError
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
                                error = result.error.message?.let { MHError(it) }
                                    ?: MHError.NetworkError
                            )
                        }
                        is NetworkResponse.ServerError -> {
                            Timber.d(result.body.toString())
                            return@withContext MHTaskState(
                                isSuccess = false,
                                data = null,
                                error = result.body?.message?.let { MHError(it) }
                                    ?: MHError.apiErrorWithCode(result.code)
                            )
                        }
                        is NetworkResponse.UnknownError -> {
                            Timber.d(result.error.message ?: String.emptyString)
                            return@withContext MHTaskState(
                                isSuccess = false,
                                data = null,
                                error = result.error.message?.let { MHError(it) }
                                    ?: MHError.ParsingError
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
                    error = e.message?.let { MHError(it) } ?: MHError.UnknownError
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
                        error = MHError.LoginExpiredError
                    )
                } else {
                    val result = userMangaService.deleteMangaFromListAsync(
                        authHeader = ApiConstants.BEARER_SEPARATOR + accessToken,
                        mangaId = mangaId
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
                                error = result.error.message?.let { MHError(it) }
                                    ?: MHError.NetworkError
                            )
                        }
                        is NetworkResponse.ServerError -> {
                            Timber.d(result.body.toString())
                            if (result.code == 404) {
                                return@withContext MHTaskState(
                                    isSuccess = false,
                                    data = null,
                                    error = MHError.MangaNotFoundError
                                )
                            }
                            return@withContext MHTaskState(
                                isSuccess = false,
                                data = null,
                                error = result.body?.message?.let { MHError(it) }
                                    ?: MHError.apiErrorWithCode(result.code)
                            )
                        }
                        is NetworkResponse.UnknownError -> {
                            Timber.d(result.error.message ?: String.emptyString)
                            return@withContext MHTaskState(
                                isSuccess = false,
                                data = null,
                                error = result.error.message?.let { MHError(it) }
                                    ?: MHError.ParsingError
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
                    error = e.message?.let { MHError(it) } ?: MHError.UnknownError
                )
            }
        }
}
