package com.cybershark.mediahub.data.models.retrofit.token

data class TraktRefreshTokenRequestModel(
    val refresh_token: String,
    val client_id: String,
    val client_secret: String,
    val redirect_uri: String,
    val grant_type: String
)