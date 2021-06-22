package com.sharkaboi.mediahub.modules.settings.repository

import android.util.Log
import com.sharkaboi.mediahub.common.extensions.emptyString
import com.sharkaboi.mediahub.data.datastore.DataStoreRepository
import com.sharkaboi.mediahub.data.wrappers.MHError
import com.sharkaboi.mediahub.data.wrappers.MHTaskState
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class SettingsRepositoryImpl(
    private val dataStoreRepository: DataStoreRepository
) : SettingsRepository {

    override suspend fun logOutUser(): MHTaskState<Unit> = withContext(Dispatchers.IO) {
        try {
            dataStoreRepository.clearDataStore()
            return@withContext MHTaskState(
                isSuccess = true,
                data = null,
                error = MHError.nullError
            )
        } catch (e: Exception) {
            e.printStackTrace()
            Log.d(TAG, e.message ?: String.emptyString)
            return@withContext MHTaskState(
                isSuccess = false,
                data = null,
                error = MHError(e.message ?: String.emptyString, e)
            )
        }
    }

    companion object {
        private const val TAG = "SettingsRepository"
    }
}