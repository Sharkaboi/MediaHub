package com.sharkaboi.mediahub.modules.splash.repository

import kotlinx.coroutines.flow.Flow
import java.util.*

interface SplashRepository {
    val accessTokenFlow: Flow<String?>
    val expiresInFlow: Flow<Date>
    val refreshTokenFlow: Flow<String>

    suspend fun refreshToken(): Boolean
}
