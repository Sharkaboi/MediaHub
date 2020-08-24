package com.cybershark.mediahub.workmanager

import android.content.Context
import androidx.preference.PreferenceManager
import androidx.work.Worker
import androidx.work.WorkerParameters
import java.util.*

class TraktTokenRefresher(context: Context, workerParams: WorkerParameters) : Worker(context, workerParams) {
    override fun doWork(): Result {
        val refreshTimeInLong = PreferenceManager.getDefaultSharedPreferences(applicationContext).getInt("expires_in", 0)
        val currentTime = Date()
        val refreshTimeInDate = Date(refreshTimeInLong.toLong())
        val result = currentTime.compareTo(refreshTimeInDate)
        return if (result == -1) {
            //refresh token and save to prefs
            try {
                refreshTokenFromAPIAndSaveToPrefs()
                Result.success()
            } catch (e: Exception) {
                Result.retry()
            }
        } else {
            Result.success()
        }
    }

    private fun refreshTokenFromAPIAndSaveToPrefs() {
        TODO("Refresh and save token to prefs")
    }
}