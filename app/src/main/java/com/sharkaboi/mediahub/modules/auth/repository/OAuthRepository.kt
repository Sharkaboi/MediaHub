package com.sharkaboi.mediahub.modules.auth.repository

interface OAuthRepository {
    suspend fun getAccessToken(code: String, codeVerifier: String): String?
}
