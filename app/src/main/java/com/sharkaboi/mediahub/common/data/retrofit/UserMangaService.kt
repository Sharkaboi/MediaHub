package com.sharkaboi.mediahub.common.data.retrofit

import com.haroldadmin.cnradapter.NetworkResponse
import com.sharkaboi.mediahub.common.data.api.enums.UserMangaSortType
import com.sharkaboi.mediahub.common.data.api.models.ApiError
import com.sharkaboi.mediahub.common.data.api.models.usermanga.UserMangaListResponse
import com.sharkaboi.mediahub.common.data.api.models.usermanga.UserMangaUpdateResponse
import kotlinx.coroutines.Deferred
import retrofit2.http.*

interface UserMangaService {

    @PATCH("manga/{id}/my_list_status")
    @FormUrlEncoded
    fun updateMangaStatusAsync(
        @Header("Authorization") authHeader: String,
        @Path("id") mangaId: Int,
        @Field("status") mangaStatus: String? = null,
        @Field("is_rereading") isReReading: Boolean? = null,
        @Field("score") score: Int? = null,
        @Field("num_volumes_read") numVolumesRead: Int? = null,
        @Field("num_chapters_read") numChaptersRead: Int? = null,
        @Field("priority") priority: Int? = null,
        @Field("num_times_reread") numTimeReRead: Int? = null,
        @Field("reread_value") reReadValue: Int? = null,
        @Field("tags") tags: String? = null,
        @Field("comments") comments: String? = null
    ): Deferred<NetworkResponse<UserMangaUpdateResponse, ApiError>>

    /**
     *   200 -> Successfully removed
     *   404 -> Manga not in user's list
     */
    @DELETE("manga/{id}/my_list_status")
    fun deleteMangaFromListAsync(
        @Header("Authorization") authHeader: String,
        @Path("id") mangaId: Int
    ): Deferred<NetworkResponse<Unit, ApiError>>

    /**
     *  status == null -> all status
     *  todo: request additional fields (total chaps, genres etc)
     */
    @GET("users/{username}/mangalist")
    fun getMangaListOfUserAsync(
        @Header("Authorization") authHeader: String,
        @Path("username") username: String = "@me",
        @Query("status") status: String? = null,
        @Query("sort") sort: String = UserMangaSortType.list_updated_at.name,
        @Query("limit") limit: Int = 10,
        @Query("offset") offset: Int = 0,
        @Query("fields") fields: String = "id,title,main_picture,num_volumes,num_chapters,list_status"
    ): Deferred<NetworkResponse<UserMangaListResponse, ApiError>>
}