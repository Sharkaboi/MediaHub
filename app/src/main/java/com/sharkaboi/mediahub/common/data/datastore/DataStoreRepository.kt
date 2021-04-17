package com.sharkaboi.mediahub.common.data.datastore

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.*
import androidx.datastore.preferences.preferencesDataStore
import com.sharkaboi.mediahub.common.data.datastore.DataStoreConstants.ACCESS_TOKEN
import com.sharkaboi.mediahub.common.data.datastore.DataStoreConstants.EXPIRES_IN
import com.sharkaboi.mediahub.common.data.datastore.DataStoreConstants.REFRESH_TOKEN
import com.sharkaboi.mediahub.common.extensions.emptyString
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.withContext
import java.io.IOException
import java.util.*

private const val PREFERENCES_NAME = "mediahub_preferences"

internal val Context.dataStore by preferencesDataStore(
    name = PREFERENCES_NAME
)

object DataStoreConstants {
    val ACCESS_TOKEN = stringPreferencesKey("access_token")
    val REFRESH_TOKEN = stringPreferencesKey("refresh_token")
    val EXPIRES_IN = longPreferencesKey("expires_in")
}

class DataStoreRepository(
    private val dataStore: DataStore<Preferences>
) {
    val accessTokenFlow: Flow<String?> = dataStore.data.catch { exception ->
        if (exception is IOException) {
            emit(emptyPreferences())
        } else {
            throw exception
        }
    }.map { preferences ->
        preferences[ACCESS_TOKEN]
    }

    val refreshTokenFlow: Flow<String> = dataStore.data.catch { exception ->
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
    }

    val expiresInFlow: Flow<Date> = dataStore.data.catch { exception ->
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
    }

    suspend fun setAccessToken(token: String) = withContext(Dispatchers.IO) {
        dataStore.edit { preferences ->
            preferences[ACCESS_TOKEN] = token
        }
    }

    suspend fun setRefreshToken(token: String) = withContext(Dispatchers.IO) {
        dataStore.edit { preferences ->
            preferences[REFRESH_TOKEN] = token
        }
    }

    suspend fun setExpireIn() = withContext(Dispatchers.IO) {
        val date = Calendar.getInstance().apply {
            add(Calendar.DATE, 31)
        }
        dataStore.edit { preferences ->
            preferences[EXPIRES_IN] = date.timeInMillis
        }
    }

    suspend fun clearDataStore() {
        dataStore.edit { preferences ->
            preferences.clear()
        }
    }
}
