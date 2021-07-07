package com.sharkaboi.mediahub.di

import android.content.SharedPreferences
import com.sharkaboi.mediahub.data.api.retrofit.UserAnimeService
import com.sharkaboi.mediahub.data.datastore.DataStoreRepository
import com.sharkaboi.mediahub.modules.anime.repository.AnimeRepository
import com.sharkaboi.mediahub.modules.anime.repository.AnimeRepositoryImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityRetainedComponent
import dagger.hilt.android.scopes.ActivityRetainedScoped

@InstallIn(ActivityRetainedComponent::class)
@Module
object AnimeModule {

    @Provides
    @ActivityRetainedScoped
    fun getAnimeRepository(
        userAnimeService: UserAnimeService,
        dataStoreRepository: DataStoreRepository,
        sharedPreferences: SharedPreferences
    ): AnimeRepository =
        AnimeRepositoryImpl(userAnimeService, dataStoreRepository, sharedPreferences)
}
