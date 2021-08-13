package com.sharkaboi.mediahub.data.datastore

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.*
import androidx.datastore.preferences.preferencesDataStore
import com.sharkaboi.mediahub.common.extensions.emptyString
import com.sharkaboi.mediahub.data.datastore.DataStoreConstants.ACCESS_TOKEN
import com.sharkaboi.mediahub.data.datastore.DataStoreConstants.EXPIRES_IN
import com.sharkaboi.mediahub.data.datastore.DataStoreConstants.REFRESH_TOKEN
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.withContext
import timber.log.Timber
import java.io.IOException
import java.util.*
import kotlin.coroutines.coroutineContext

internal val Context.dataStore by preferencesDataStore(
    name = DataStoreConstants.PREFERENCES_NAME
)

class DataStoreRepositoryImpl(
    private val dataStore: DataStore<Preferences>
) : DataStoreRepository {
    override val accessTokenFlow: Flow<String?> = dataStore.data.catch { exception ->
        if (exception is IOException) {
            emit(emptyPreferences())
        } else {
            throw exception
        }
    }.map { preferences ->
        Timber.d("Collected access token on $coroutineContext")
        preferences[ACCESS_TOKEN]
    }.flowOn(Dispatchers.IO)

    override val refreshTokenFlow: Flow<String> = dataStore.data.catch { exception ->
        if (exception is IOException) {
            emit(emptyPreferences())
        } else {
            throw exception
        }
    }.map { preferences ->
        preferences[REFRESH_TOKEN]?.let {
            return@map it
        } ?: run {
            return@map String.emptyString
        }
    }.flowOn(Dispatchers.IO)

    override val expiresInFlow: Flow<Date> = dataStore.data.catch { exception ->
        if (exception is IOException) {
            emit(emptyPreferences())
        } else {
            throw exception
        }
    }.map { preferences ->
        val expiredIn = preferences[EXPIRES_IN] ?: 0
        val date = Calendar.getInstance().apply {
            timeInMillis = expiredIn
        }
        date.time
    }.flowOn(Dispatchers.IO)

    override suspend fun setAccessToken(token: String): Unit = withContext(Dispatchers.IO) {
        dataStore.edit { preferences ->
            preferences[ACCESS_TOKEN] = token
        }
    }

    override suspend fun setRefreshToken(token: String): Unit = withContext(Dispatchers.IO) {
        dataStore.edit { preferences ->
            preferences[REFRESH_TOKEN] = token
        }
    }

    override suspend fun setExpireIn(): Unit = withContext(Dispatchers.IO) {
        val date = Calendar.getInstance().apply {
            add(Calendar.DATE, 31)
        }
        dataStore.edit { preferences ->
            preferences[EXPIRES_IN] = date.timeInMillis
        }
    }

    override suspend fun clearDataStore(): Unit = withContext(Dispatchers.IO) {
        dataStore.edit { preferences ->
            preferences.clear()
        }
    }
}
