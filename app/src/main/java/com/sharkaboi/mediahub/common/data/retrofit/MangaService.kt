package com.sharkaboi.mediahub.common.data.retrofit

import com.haroldadmin.cnradapter.NetworkResponse
import com.sharkaboi.mediahub.common.data.api.enums.MangaRankingType
import com.sharkaboi.mediahub.common.data.api.models.ApiError
import com.sharkaboi.mediahub.common.data.api.models.anime.AnimeRankingResponse
import com.sharkaboi.mediahub.common.data.api.models.manga.MangaByIDResponse
import com.sharkaboi.mediahub.common.data.api.models.manga.MangaRankingResponse
import com.sharkaboi.mediahub.common.data.api.models.manga.MangaSearchResponse
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
        @Query("limit") limit: Int = 10,
        @Query("offset") offset: Int = 0,
        @Query("fields") fields: String? = null
    ): Deferred<NetworkResponse<MangaSearchResponse, ApiError>>

    @GET("manga/{id}")
    fun getMangaByIdAsync(
        @Header("Authorization") authHeader: String,
        @Path("id") mangaId: Int,
        @Query("fields") fields: String = "id,title,main_picture,alternative_titles,start_date,end_date,synopsis,mean,rank,popularity,num_list_users,num_scoring_users,nsfw,created_at,updated_at,media_type,status,genres,my_list_status,num_volumes,num_chapters,authors{first_name,last_name},pictures,background,related_anime,related_manga,recommendations,serialization{name}"
    ): Deferred<NetworkResponse<MangaByIDResponse, ApiError>>

    @GET("manga/ranking")
    fun getMangaRankingAsync(
        @Header("Authorization") authHeader: String,
        @Query("ranking_type") rankingType: String = MangaRankingType.all.name,
        @Query("limit") limit: Int = 10,
        @Query("offset") offset: Int = 0,
        @Query("fields") fields: String? = null
    ): Deferred<NetworkResponse<MangaRankingResponse, ApiError>>

}