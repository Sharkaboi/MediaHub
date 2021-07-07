package com.sharkaboi.mediahub.data.api.retrofit

import com.haroldadmin.cnradapter.NetworkResponse
import com.sharkaboi.mediahub.data.api.enums.OAuthGrantType
import com.sharkaboi.mediahub.data.api.models.ApiError
import com.sharkaboi.mediahub.data.api.models.auth.AccessTokenResponse
import kotlinx.coroutines.Deferred
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.POST

interface AuthService {
    companion object {
        const val AUTH_BASE_URL: String = "https://myanimelist.net/v1"

        fun getAuthTokenLink(
            clientId: String,
            state: String,
            codeChallenge: String
        ) = "$AUTH_BASE_URL/oauth2/authorize?response_type=code" +
            "&client_id=$clientId&state=$state&code_challenge=$codeChallenge"
    }

    @POST("$AUTH_BASE_URL/oauth2/token")
    @FormUrlEncoded
    fun refreshTokenAsync(
        @Field("client_id") clientId: String,
        @Field("refresh_token") refreshToken: String,
        @Field("grant_type") grantType: String = OAuthGrantType.refresh_token.name
    ): Deferred<NetworkResponse<AccessTokenResponse, ApiError>>

    @POST("$AUTH_BASE_URL/oauth2/token")
    @FormUrlEncoded
    fun getAccessTokenAsync(
        @Field("client_id") clientId: String,
        @Field("code") code: String,
        @Field("code_verifier") codeVerifier: String,
        @Field("grant_type") grantType: String = OAuthGrantType.authorization_code.name
    ): Deferred<NetworkResponse<AccessTokenResponse, ApiError>>
}
