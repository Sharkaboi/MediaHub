package com.sharkaboi.mediahub.data.datastore

import androidx.datastore.preferences.core.longPreferencesKey
import androidx.datastore.preferences.core.stringPreferencesKey

object DataStoreConstants {
    val ACCESS_TOKEN = stringPreferencesKey("access_token")
    val REFRESH_TOKEN = stringPreferencesKey("refresh_token")
    val EXPIRES_IN = longPreferencesKey("expires_in")

    const val PREFERENCES_NAME = "mediahub_preferences"
}