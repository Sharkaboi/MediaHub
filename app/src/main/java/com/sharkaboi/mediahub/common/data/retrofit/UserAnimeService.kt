package com.sharkaboi.mediahub.common.data.retrofit

import com.haroldadmin.cnradapter.NetworkResponse
import com.sharkaboi.mediahub.common.data.api.ApiConstants
import com.sharkaboi.mediahub.common.data.api.enums.UserAnimeSortType
import com.sharkaboi.mediahub.common.data.api.models.ApiError
import com.sharkaboi.mediahub.common.data.api.models.useranime.UserAnimeListResponse
import com.sharkaboi.mediahub.common.data.api.models.useranime.UserAnimeUpdateResponse
import kotlinx.coroutines.Deferred
import retrofit2.http.*

interface UserAnimeService {

    @PATCH("anime/{id}/my_list_status")
    @FormUrlEncoded
    fun updateAnimeStatusAsync(
        @Header("Authorization") authHeader: String,
        @Path("id") animeId: Int,
        @Field("status") animeStatus: String? = null,
        @Field("is_rewatching") isReWatching: Boolean? = null,
        @Field("score") score: Int? = null,
        @Field("num_watched_episodes") numWatchedEps: Int? = null,
        @Field("priority") priority: Int? = null,
        @Field("num_times_rewatched") numReWatchedEps: Int? = null,
        @Field("rewatch_value") reWatchValue: Int? = null,
        @Field("tags") tags: String? = null,
        @Field("comments") comments: String? = null
    ): Deferred<NetworkResponse<UserAnimeUpdateResponse, ApiError>>

    /**
     *   200 -> Successfully removed
     *   404 -> Anime not in user's list
     */
    @DELETE("anime/{id}/my_list_status")
    fun deleteAnimeFromListAsync(
        @Header("Authorization") authHeader: String,
        @Path("id") animeId: Int
    ): Deferred<NetworkResponse<Unit, ApiError>>

    /**
     *  status == null -> all status
     *  todo: request additional fields (total eps, media_type etc)
     */
    @GET("users/{username}/animelist")
    fun getAnimeListOfUserAsync(
        @Header("Authorization") authHeader: String,
        @Path("username") username: String = "@me",
        @Query("status") status: String? = null,
        @Query("sort") sort: String = UserAnimeSortType.list_updated_at.name,
        @Query("limit") limit: Int = ApiConstants.API_PAGE_LIMIT,
        @Query("offset") offset: Int = ApiConstants.API_START_OFFSET,
        @Query("fields") fields: String = "list_status,num_episodes"
    ): Deferred<NetworkResponse<UserAnimeListResponse, ApiError>>
}