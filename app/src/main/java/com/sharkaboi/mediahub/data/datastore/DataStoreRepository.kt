package com.sharkaboi.mediahub.data.datastore

import kotlinx.coroutines.flow.Flow
import java.util.*

interface DataStoreRepository {
    val accessTokenFlow: Flow<String?>
    val refreshTokenFlow: Flow<String>
    val expiresInFlow: Flow<Date>

    suspend fun setAccessToken(token: String)
    suspend fun setRefreshToken(token: String)
    suspend fun setExpireIn()
    suspend fun clearDataStore()
}