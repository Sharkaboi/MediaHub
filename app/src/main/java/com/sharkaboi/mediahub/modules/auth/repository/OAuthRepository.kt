package com.sharkaboi.mediahub.modules.auth.repository

import com.sharkaboi.mediahub.data.wrappers.MHTaskState

interface OAuthRepository {
    suspend fun getAccessToken(code: String, codeVerifier: String): MHTaskState<Unit>
}
