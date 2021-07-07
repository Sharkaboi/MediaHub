package com.sharkaboi.mediahub.di

import com.sharkaboi.mediahub.data.datastore.DataStoreRepository
import com.sharkaboi.mediahub.modules.settings.repository.SettingsRepository
import com.sharkaboi.mediahub.modules.settings.repository.SettingsRepositoryImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityRetainedComponent
import dagger.hilt.android.scopes.ActivityRetainedScoped

@InstallIn(ActivityRetainedComponent::class)
@Module
object SettingsModule {

    @Provides
    @ActivityRetainedScoped
    fun getSettingsRepository(
        dataStoreRepository: DataStoreRepository
    ): SettingsRepository =
        SettingsRepositoryImpl(dataStoreRepository)
}
