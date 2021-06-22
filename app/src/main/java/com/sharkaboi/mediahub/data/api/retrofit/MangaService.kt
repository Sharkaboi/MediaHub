package com.sharkaboi.mediahub.data.api.retrofit

import com.haroldadmin.cnradapter.NetworkResponse
import com.sharkaboi.mediahub.data.api.ApiConstants
import com.sharkaboi.mediahub.data.api.enums.MangaRankingType
import com.sharkaboi.mediahub.data.api.models.ApiError
import com.sharkaboi.mediahub.data.api.models.manga.MangaByIDResponse
import com.sharkaboi.mediahub.data.api.models.manga.MangaRankingResponse
import com.sharkaboi.mediahub.data.api.models.manga.MangaSearchResponse
import kotlinx.coroutines.Deferred
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Path
import retrofit2.http.Query

interface MangaService {

    @GET("manga")
    fun getMangaAsync(
        @Header("Authorization") authHeader: String,
        @Query("q") searchQuery: String,
        @Query("limit") limit: Int = ApiConstants.API_PAGE_LIMIT,
        @Query("offset") offset: Int = ApiConstants.API_START_OFFSET,
        @Query("nsfw") nsfw: Int = ApiConstants.NSFW_ALSO,
        @Query("fields") fields: String = ApiConstants.MANGA_LESS_FIELDS
    ): Deferred<NetworkResponse<MangaSearchResponse, ApiError>>

    @GET("manga/{id}")
    fun getMangaByIdAsync(
        @Header("Authorization") authHeader: String,
        @Path("id") mangaId: Int,
        @Query("fields") fields: String = ApiConstants.MANGA_ALL_FIELDS
    ): Deferred<NetworkResponse<MangaByIDResponse, ApiError>>

    @GET("manga/ranking")
    fun getMangaRankingAsync(
        @Header("Authorization") authHeader: String,
        @Query("ranking_type") rankingType: String = MangaRankingType.all.name,
        @Query("limit") limit: Int = ApiConstants.API_PAGE_LIMIT,
        @Query("offset") offset: Int = ApiConstants.API_START_OFFSET,
        @Query("nsfw") nsfw: Int = ApiConstants.NSFW_ALSO,
        @Query("fields") fields: String = ApiConstants.MANGA_LESS_FIELDS
    ): Deferred<NetworkResponse<MangaRankingResponse, ApiError>>

}