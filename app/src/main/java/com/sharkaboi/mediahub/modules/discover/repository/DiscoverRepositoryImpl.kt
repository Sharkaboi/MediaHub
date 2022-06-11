package com.sharkaboi.mediahub.modules.discover.repository

import android.content.SharedPreferences
import com.haroldadmin.cnradapter.NetworkResponse
import com.sharkaboi.mediahub.common.extensions.getCatching
import com.sharkaboi.mediahub.data.api.constants.ApiConstants
import com.sharkaboi.mediahub.data.api.enums.getAnimeSeason
import com.sharkaboi.mediahub.data.api.models.anime.AnimeRankingResponse
import com.sharkaboi.mediahub.data.api.models.anime.AnimeSeasonalResponse
import com.sharkaboi.mediahub.data.api.models.anime.AnimeSuggestionsResponse
import com.sharkaboi.mediahub.data.api.retrofit.AnimeService
import com.sharkaboi.mediahub.data.datastore.DataStoreRepository
import com.sharkaboi.mediahub.data.sharedpref.SharedPreferencesKeys
import com.sharkaboi.mediahub.data.wrappers.MHError
import com.sharkaboi.mediahub.data.wrappers.MHTaskState
import kotlinx.coroutines.flow.firstOrNull
import timber.log.Timber
import java.time.LocalDate

class DiscoverRepositoryImpl(
    private val animeService: AnimeService,
    private val dataStoreRepository: DataStoreRepository,
    private val sharedPreferences: SharedPreferences
) : DiscoverRepository {

    override suspend fun getAnimeRecommendations(): MHTaskState<AnimeSuggestionsResponse> =
        getCatching {
            val showNsfw =
                sharedPreferences.getBoolean(SharedPreferencesKeys.NSFW_OPTION, false)
            val accessToken: String = dataStoreRepository.accessTokenFlow.firstOrNull()
                ?: return@getCatching MHTaskState(
                    isSuccess = false,
                    data = null,
                    error = MHError.LoginExpiredError
                )
            val result = animeService.getAnimeSuggestionsAsync(
                authHeader = ApiConstants.BEARER_SEPARATOR + accessToken,
                limit = ApiConstants.API_PAGE_LIMIT,
                offset = ApiConstants.API_START_OFFSET,
                nsfw = if (showNsfw) ApiConstants.NSFW_ALSO else ApiConstants.SFW_ONLY
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

    override suspend fun getAnimeSeasonals(): MHTaskState<AnimeSeasonalResponse> =
        getCatching {
            val showNsfw =
                sharedPreferences.getBoolean(SharedPreferencesKeys.NSFW_OPTION, false)
            val accessToken: String = dataStoreRepository.accessTokenFlow.firstOrNull()
                ?: return@getCatching MHTaskState(
                    isSuccess = false,
                    data = null,
                    error = MHError.LoginExpiredError
                )

            val today = LocalDate.now()
            val result = animeService.getAnimeBySeasonAsync(
                authHeader = ApiConstants.BEARER_SEPARATOR + accessToken,
                year = today.year,
                season = today.getAnimeSeason().name,
                limit = ApiConstants.API_PAGE_LIMIT,
                offset = ApiConstants.API_START_OFFSET,
                nsfw = if (showNsfw) ApiConstants.NSFW_ALSO else ApiConstants.SFW_ONLY
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

    override suspend fun getAnimeRankings(): MHTaskState<AnimeRankingResponse> =
        getCatching {
            val showNsfw =
                sharedPreferences.getBoolean(SharedPreferencesKeys.NSFW_OPTION, false)
            val accessToken: String = dataStoreRepository.accessTokenFlow.firstOrNull()
                ?: return@getCatching MHTaskState(
                    isSuccess = false,
                    data = null,
                    error = MHError.LoginExpiredError
                )
            val result = animeService.getAnimeRankingAsync(
                authHeader = ApiConstants.BEARER_SEPARATOR + accessToken,
                limit = ApiConstants.API_PAGE_LIMIT,
                offset = ApiConstants.API_START_OFFSET,
                nsfw = if (showNsfw) ApiConstants.NSFW_ALSO else ApiConstants.SFW_ONLY
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
