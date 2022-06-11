package com.sharkaboi.mediahub.modules.discover.di

import android.content.SharedPreferences
import com.sharkaboi.mediahub.data.api.retrofit.AnimeService
import com.sharkaboi.mediahub.data.datastore.DataStoreRepository
import com.sharkaboi.mediahub.modules.discover.repository.DiscoverRepository
import com.sharkaboi.mediahub.modules.discover.repository.DiscoverRepositoryImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityRetainedComponent
import dagger.hilt.android.scopes.ActivityRetainedScoped

@InstallIn(ActivityRetainedComponent::class)
@Module
object DiscoverModule {

    @Provides
    @ActivityRetainedScoped
    fun getDiscoverRepository(
        animeService: AnimeService,
        dataStoreRepository: DataStoreRepository,
        sharedPreferences: SharedPreferences
    ): DiscoverRepository =
        DiscoverRepositoryImpl(animeService, dataStoreRepository, sharedPreferences)
}
