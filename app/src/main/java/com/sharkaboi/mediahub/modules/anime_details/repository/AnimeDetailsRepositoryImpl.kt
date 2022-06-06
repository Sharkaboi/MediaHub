package com.sharkaboi.mediahub.modules.anime_details.repository

import com.apollographql.apollo3.ApolloClient
import com.apollographql.apollo3.exception.ApolloException
import com.haroldadmin.cnradapter.NetworkResponse
import com.sharkaboi.mediahub.GetNextAiringAnimeEpisodeQuery
import com.sharkaboi.mediahub.common.extensions.getCatching
import com.sharkaboi.mediahub.data.api.constants.ApiConstants
import com.sharkaboi.mediahub.data.api.models.anime.AnimeByIDResponse
import com.sharkaboi.mediahub.data.api.retrofit.AnimeService
import com.sharkaboi.mediahub.data.api.retrofit.UserAnimeService
import com.sharkaboi.mediahub.data.datastore.DataStoreRepository
import com.sharkaboi.mediahub.data.wrappers.MHError
import com.sharkaboi.mediahub.data.wrappers.MHTaskState
import kotlinx.coroutines.flow.firstOrNull
import timber.log.Timber

class AnimeDetailsRepositoryImpl(
    private val animeService: AnimeService,
    private val userAnimeService: UserAnimeService,
    private val apolloClient: ApolloClient,
    private val dataStoreRepository: DataStoreRepository
) : AnimeDetailsRepository {

    override suspend fun getAnimeById(
        animeId: Int
    ): MHTaskState<AnimeByIDResponse> = getCatching {
        val accessToken: String = dataStoreRepository.accessTokenFlow.firstOrNull()
            ?: return@getCatching MHTaskState(
                isSuccess = false,
                data = null,
                error = MHError.LoginExpiredError
            )

        val result = animeService.getAnimeByIdAsync(
            authHeader = ApiConstants.BEARER_SEPARATOR + accessToken,
            animeId = animeId
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

    override suspend fun getNextAiringEpisodeById(
        animeId: Int
    ): MHTaskState<GetNextAiringAnimeEpisodeQuery.ReturnedMedia> = getCatching {
        val response = try {
            apolloClient.query(GetNextAiringAnimeEpisodeQuery(idMal = animeId)).execute()
        } catch (e: ApolloException) {
            return@getCatching MHTaskState(
                isSuccess = false,
                data = null,
                error = MHError.getError(e.message, MHError.ProtocolError)
            )
        }

        val mediaDetails = response.data?.returnedMedia
        if (mediaDetails == null || response.hasErrors()) {
            val errorMessage = response.errors?.first()?.message
            return@getCatching MHTaskState(
                isSuccess = false,
                data = null,
                error = MHError.getError(errorMessage, MHError.ApplicationError)
            )
        }

        return@getCatching MHTaskState(
            isSuccess = true,
            data = mediaDetails,
            error = MHError.EmptyError
        )
    }

    override suspend fun updateAnimeStatus(
        animeId: Int,
        animeStatus: String?,
        score: Int?,
        numWatchedEps: Int?
    ): MHTaskState<Unit> = getCatching {
        val accessToken: String = dataStoreRepository.accessTokenFlow.firstOrNull()
            ?: return@getCatching MHTaskState(
                isSuccess = false,
                data = null,
                error = MHError.LoginExpiredError
            )

        val result = userAnimeService.updateAnimeStatusAsync(
            authHeader = ApiConstants.BEARER_SEPARATOR + accessToken,
            animeId = animeId,
            animeStatus = animeStatus,
            score = score,
            numWatchedEps = numWatchedEps
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
                return@getCatching MHTaskState(
                    isSuccess = false,
                    data = null,
                    error = MHError.getError(result.error.message, MHError.NetworkError)
                )
            }
            is NetworkResponse.ServerError -> {
                return@getCatching MHTaskState(
                    isSuccess = false,
                    data = null,
                    error = MHError.getError(
                        result.body?.message,
                        MHError.apiErrorWithCode(result.code)
                    )
                )
            }
            is NetworkResponse.UnknownError -> {
                return@getCatching MHTaskState(
                    isSuccess = false,
                    data = null,
                    error = MHError.getError(result.error.message, MHError.ParsingError)
                )
            }
        }
    }

    override suspend fun removeAnimeFromList(
        animeId: Int
    ): MHTaskState<Unit> =
        getCatching {
            val accessToken: String = dataStoreRepository.accessTokenFlow.firstOrNull()
                ?: return@getCatching MHTaskState(
                    isSuccess = false,
                    data = null,
                    error = MHError.LoginExpiredError
                )

            val result = userAnimeService.deleteAnimeFromListAsync(
                authHeader = ApiConstants.BEARER_SEPARATOR + accessToken,
                animeId = animeId
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
                    if (result.code == 404) {
                        MHTaskState(
                            isSuccess = false,
                            data = null,
                            error = MHError.AnimeNotFoundError
                        )
                    }
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
