package com.sharkaboi.mediahub.di

import com.sharkaboi.mediahub.data.datastore.DataStoreRepository
import com.sharkaboi.mediahub.data.api.retrofit.AnimeService
import com.sharkaboi.mediahub.modules.anime_seasonal.repository.AnimeSeasonalRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityRetainedComponent
import dagger.hilt.android.scopes.ActivityRetainedScoped

@InstallIn(ActivityRetainedComponent::class)
@Module
object AnimeSeasonalModule {

    @Provides
    @ActivityRetainedScoped
    fun getAnimeSeasonalRepository(
        animeService: AnimeService,
        dataStoreRepository: DataStoreRepository
    ): AnimeSeasonalRepository =
        AnimeSeasonalRepository(animeService, dataStoreRepository)
}