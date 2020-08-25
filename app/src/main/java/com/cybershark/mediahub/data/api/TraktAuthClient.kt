package com.cybershark.mediahub.data.api

import com.cybershark.mediahub.data.models.retrofit.token.TraktTokenRequestModel
import com.cybershark.mediahub.data.models.retrofit.token.TraktTokenResultModel
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.Headers
import retrofit2.http.POST

interface TraktAuthClient {

    @Headers("Content-Type: ${APIConstants.RESULT_TYPE}")
    @POST(APIConstants.AUTH_TOKEN_URL_PART)
    fun getTokensFromCode(@Body data: TraktTokenRequestModel): Call<TraktTokenResultModel>
}