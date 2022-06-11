package com.sharkaboi.mediahub.modules.settings.repository

import com.sharkaboi.mediahub.common.extensions.getCatching
import com.sharkaboi.mediahub.data.datastore.DataStoreRepository
import com.sharkaboi.mediahub.data.wrappers.MHError
import com.sharkaboi.mediahub.data.wrappers.MHTaskState

class SettingsRepositoryImpl(
    private val dataStoreRepository: DataStoreRepository
) : SettingsRepository {

    override suspend fun logOutUser(): MHTaskState<Unit> = getCatching {
        dataStoreRepository.clearDataStore()
        return@getCatching MHTaskState(
            isSuccess = true,
            data = null,
            error = MHError.EmptyError
        )
    }
}
