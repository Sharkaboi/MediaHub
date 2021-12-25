package com.sharkaboi.mediahub.modules.anime_seasonal.di

import android.content.SharedPreferences
import com.sharkaboi.mediahub.data.api.retrofit.AnimeService
import com.sharkaboi.mediahub.data.datastore.DataStoreRepository
import com.sharkaboi.mediahub.modules.anime_seasonal.repository.AnimeSeasonalRepository
import com.sharkaboi.mediahub.modules.anime_seasonal.repository.AnimeSeasonalRepositoryImpl
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
        dataStoreRepository: DataStoreRepository,
        sharedPreferences: SharedPreferences
    ): AnimeSeasonalRepository =
        AnimeSeasonalRepositoryImpl(animeService, dataStoreRepository, sharedPreferences)
}
