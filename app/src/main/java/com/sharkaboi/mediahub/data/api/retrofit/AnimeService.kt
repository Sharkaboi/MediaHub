package com.sharkaboi.mediahub.data.api.retrofit

import com.haroldadmin.cnradapter.NetworkResponse
import com.sharkaboi.mediahub.data.api.ApiConstants
import com.sharkaboi.mediahub.data.api.enums.AnimeRankingType
import com.sharkaboi.mediahub.data.api.enums.AnimeSortType
import com.sharkaboi.mediahub.data.api.models.ApiError
import com.sharkaboi.mediahub.data.api.models.anime.*
import kotlinx.coroutines.Deferred
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Path
import retrofit2.http.Query

interface AnimeService {

    @GET("anime")
    fun getAnimeAsync(
        @Header("Authorization") authHeader: String,
        @Query("q") searchQuery: String,
        @Query("limit") limit: Int = ApiConstants.API_PAGE_LIMIT,
        @Query("offset") offset: Int = ApiConstants.API_START_OFFSET,
        @Query("nsfw") nsfw: Int = ApiConstants.NSFW_ALSO,
        @Query("fields") fields: String? = ApiConstants.ANIME_LESS_FIELDS
    ): Deferred<NetworkResponse<AnimeSearchResponse, ApiError>>

    @GET("anime/{id}")
    fun getAnimeByIdAsync(
        @Header("Authorization") authHeader: String,
        @Path("id") animeId: Int,
        @Query("fields") fields: String =ApiConstants.ANIME_ALL_FIELDS
    ): Deferred<NetworkResponse<AnimeByIDResponse, ApiError>>

    @GET("anime/ranking")
    fun getAnimeRankingAsync(
        @Header("Authorization") authHeader: String,
        @Query("limit") limit: Int = ApiConstants.API_PAGE_LIMIT,
        @Query("offset") offset: Int = ApiConstants.API_START_OFFSET,
        @Query("nsfw") nsfw: Int = ApiConstants.NSFW_ALSO,
        @Query("ranking_type") rankingType: String = AnimeRankingType.all.name,
        @Query("fields") fields: String? = ApiConstants.ANIME_LESS_FIELDS
    ): Deferred<NetworkResponse<AnimeRankingResponse, ApiError>>

    @GET("anime/season/{year}/{season}")
    fun getAnimeBySeasonAsync(
        @Header("Authorization") authHeader: String,
        @Path("year") year: Int,
        @Path("season") season: String,
        @Query("limit") limit: Int = ApiConstants.API_PAGE_LIMIT,
        @Query("offset") offset: Int = ApiConstants.API_START_OFFSET,
        @Query("nsfw") nsfw: Int = ApiConstants.NSFW_ALSO,
        @Query("sort") sortType: String = AnimeSortType.anime_score.name,
        @Query("fields") fields: String? = ApiConstants.ANIME_LESS_FIELDS
    ): Deferred<NetworkResponse<AnimeSeasonalResponse, ApiError>>

    //empty list if new user
    @GET("anime/suggestions")
    fun getAnimeSuggestionsAsync(
        @Header("Authorization") authHeader: String,
        @Query("limit") limit: Int = ApiConstants.API_PAGE_LIMIT,
        @Query("offset") offset: Int = ApiConstants.API_START_OFFSET,
        @Query("nsfw") nsfw: Int = ApiConstants.NSFW_ALSO,
        @Query("fields") fields: String? = ApiConstants.ANIME_LESS_FIELDS
    ): Deferred<NetworkResponse<AnimeSuggestionsResponse, ApiError>>
}