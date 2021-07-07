package com.sharkaboi.mediahub.modules.settings.repository

import com.sharkaboi.mediahub.data.wrappers.MHTaskState

interface SettingsRepository {

    suspend fun logOutUser(): MHTaskState<Unit>
}
