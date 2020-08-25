package com.cybershark.mediahub.data.repository

import com.cybershark.mediahub.data.api.APIConstants
import com.cybershark.mediahub.data.api.TraktAuthClient
import com.cybershark.mediahub.data.models.retrofit.token.TraktTokenRequestModel
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class GetStartedRepository {

    companion object {
        const val TAG = "GetStartedRepository"
    }

    private val traktStagingRetrofitAPIClient by lazy {
        Retrofit.Builder()
            .baseUrl(APIConstants.TRAKT_STAGING_API_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(TraktAuthClient::class.java)
    }

    suspend fun getTokenFromAuthCode(code: String, client_id: String, client_secret: String) =
        traktStagingRetrofitAPIClient.getTokensFromCode(
            TraktTokenRequestModel(
                code = code,
                client_id = client_id,
                client_secret = client_secret,
                redirect_uri = APIConstants.DEEP_LINK_CALLBACK_URI,
                grant_type = APIConstants.GRANT_TYPE
            )
        )

}