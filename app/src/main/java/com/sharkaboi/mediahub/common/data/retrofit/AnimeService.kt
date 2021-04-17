package com.sharkaboi.mediahub.common.data.retrofit

import com.haroldadmin.cnradapter.NetworkResponse
import com.sharkaboi.mediahub.common.data.api.models.AnimeByIDResponse
import com.sharkaboi.mediahub.common.data.api.models.AnimeSearchResponse
import com.sharkaboi.mediahub.common.data.api.models.ApiError
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
        @Query("limit") limit: Int = 10,
        @Query("offset") offset: Int = 0,
        @Query("fields") fields: String
    ): Deferred<NetworkResponse<AnimeSearchResponse, ApiError>>

    @GET("anime/{id}")
    fun getAnimeByIdAsync(
        @Header("Authorization") authHeader: String,
        @Path("id") animeId: Int,
        @Query("fields") fields: String = "id,title,main_picture,alternative_titles,start_date,end_date,synopsis,mean,rank,popularity,num_list_users,num_scoring_users,nsfw,created_at,updated_at,media_type,status,genres,my_list_status,num_episodes,start_season,broadcast,source,average_episode_duration,rating,pictures,background,related_anime,related_manga,recommendations,studios,statistics"
    ): Deferred<NetworkResponse<AnimeByIDResponse, ApiError>>

    @GET("anime/ranking")
    fun getAnimeRankingAsync(
        @Header("Authorization") authHeader: String,
        @Query("fields") fields: String = "id,title,main_picture,alternative_titles,start_date,end_date,synopsis,mean,rank,popularity,num_list_users,num_scoring_users,nsfw,created_at,updated_at,media_type,status,genres,my_list_status,num_episodes,start_season,broadcast,source,average_episode_duration,rating,pictures,background,related_anime,related_manga,recommendations,studios,statistics"
    ): Deferred<NetworkResponse<AnimeByIDResponse, ApiError>>
}