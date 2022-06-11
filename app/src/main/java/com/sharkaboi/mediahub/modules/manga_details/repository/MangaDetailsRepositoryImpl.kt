package com.sharkaboi.mediahub.modules.manga_details.repository

import com.haroldadmin.cnradapter.NetworkResponse
import com.sharkaboi.mediahub.common.extensions.getCatching
import com.sharkaboi.mediahub.data.api.constants.ApiConstants
import com.sharkaboi.mediahub.data.api.models.manga.MangaByIDResponse
import com.sharkaboi.mediahub.data.api.retrofit.MangaService
import com.sharkaboi.mediahub.data.api.retrofit.UserMangaService
import com.sharkaboi.mediahub.data.datastore.DataStoreRepository
import com.sharkaboi.mediahub.data.wrappers.MHError
import com.sharkaboi.mediahub.data.wrappers.MHTaskState
import com.sharkaboi.mediahub.modules.manga_details.util.MangaDetailsUpdateClass
import kotlinx.coroutines.flow.firstOrNull
import timber.log.Timber

class MangaDetailsRepositoryImpl(
    private val mangaService: MangaService,
    private val userMangaService: UserMangaService,
    private val dataStoreRepository: DataStoreRepository
) : MangaDetailsRepository {
    override suspend fun getMangaById(
        mangaId: Int
    ): MHTaskState<MangaByIDResponse> = getCatching {
        val accessToken: String = dataStoreRepository.accessTokenFlow.firstOrNull()
            ?: return@getCatching MHTaskState(
                isSuccess = false,
                data = null,
                error = MHError.LoginExpiredError
            )

        val result = mangaService.getMangaByIdAsync(
            authHeader = ApiConstants.BEARER_SEPARATOR + accessToken,
            mangaId = mangaId
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

    override suspend fun updateMangaStatus(
        mangaDetailsUpdateClass: MangaDetailsUpdateClass
    ): MHTaskState<Unit> = getCatching {
        val accessToken: String = dataStoreRepository.accessTokenFlow.firstOrNull()
            ?: return@getCatching MHTaskState(
                isSuccess = false,
                data = null,
                error = MHError.LoginExpiredError
            )

        val result = userMangaService.updateMangaStatusAsync(
            authHeader = ApiConstants.BEARER_SEPARATOR + accessToken,
            mangaId = mangaDetailsUpdateClass.mangaId,
            mangaStatus = mangaDetailsUpdateClass.mangaStatus?.name,
            score = mangaDetailsUpdateClass.score,
            numChaptersRead = mangaDetailsUpdateClass.numReadChapters,
            numVolumesRead = mangaDetailsUpdateClass.numReadVolumes
        ).await()
        Timber.d(result.toString())

        return@getCatching when (result) {
            is NetworkResponse.Success -> {
                MHTaskState(
                    isSuccess = true,
                    data = Unit,
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

    override suspend fun removeMangaFromList(
        mangaId: Int
    ): MHTaskState<Unit> =
        getCatching {
            val accessToken: String = dataStoreRepository.accessTokenFlow.firstOrNull()
                ?: return@getCatching MHTaskState(
                    isSuccess = false,
                    data = null,
                    error = MHError.LoginExpiredError
                )

            val result = userMangaService.deleteMangaFromListAsync(
                authHeader = ApiConstants.BEARER_SEPARATOR + accessToken,
                mangaId = mangaId
            ).await()
            Timber.d(result.toString())

            return@getCatching when (result) {
                is NetworkResponse.Success -> {
                    MHTaskState(
                        isSuccess = true,
                        data = Unit,
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
                    val error = if (result.code == 404) {
                        MHError.MangaNotFoundError
                    } else {
                        MHError.getError(
                            result.body?.message,
                            MHError.apiErrorWithCode(result.code)
                        )
                    }

                    MHTaskState(
                        isSuccess = false,
                        data = null,
                        error = error
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
