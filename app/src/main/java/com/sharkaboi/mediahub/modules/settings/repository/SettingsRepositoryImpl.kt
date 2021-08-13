package com.sharkaboi.mediahub.modules.settings.repository

import com.sharkaboi.mediahub.common.extensions.emptyString
import com.sharkaboi.mediahub.data.datastore.DataStoreRepository
import com.sharkaboi.mediahub.data.wrappers.MHError
import com.sharkaboi.mediahub.data.wrappers.MHTaskState
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import timber.log.Timber

class SettingsRepositoryImpl(
    private val dataStoreRepository: DataStoreRepository
) : SettingsRepository {

    override suspend fun logOutUser(): MHTaskState<Unit> = withContext(Dispatchers.IO) {
        try {
            dataStoreRepository.clearDataStore()
            return@withContext MHTaskState(
                isSuccess = true,
                data = null,
                error = MHError.EmptyError
            )
        } catch (e: Exception) {
            e.printStackTrace()
            Timber.d(e.message ?: String.emptyString)
            return@withContext MHTaskState(
                isSuccess = false,
                data = null,
                error = e.message?.let { MHError(it) } ?: MHError.UnknownError
            )
        }
    }
}
