package com.sharkaboi.mediahub.common.data.retrofit

import com.haroldadmin.cnradapter.NetworkResponse
import com.sharkaboi.mediahub.common.data.api.models.ApiError
import com.sharkaboi.mediahub.common.data.api.models.UserDetailsResponse
import kotlinx.coroutines.Deferred
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Path
import retrofit2.http.Query

interface UserService {

    @GET("users/{username}")
    fun getUserDetailsAsync(
            @Header("Authorization") authHeader: String,
            @Path("username") username: String = "@me",
            @Query("fields") fields: String = "anime_statistics"
    ): Deferred<NetworkResponse<UserDetailsResponse, ApiError>>
}