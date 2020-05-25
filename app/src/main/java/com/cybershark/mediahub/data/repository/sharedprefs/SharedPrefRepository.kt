package com.cybershark.mediahub.data.repository.sharedprefs

import android.content.Context

class SharedPrefRepository(context: Context) {
    private val sharedPreferences by lazy {
        context.getSharedPreferences(
            SharedPrefConstants.spFileName,
            Context.MODE_PRIVATE
        )
    }

    suspend fun getThemeOption(): Boolean {
        return sharedPreferences.getBoolean("darkMode", false)
    }

}