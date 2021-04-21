package com.sharkaboi.mediahub.di

import com.sharkaboi.mediahub.common.data.datastore.DataStoreRepository
import com.sharkaboi.mediahub.common.data.retrofit.UserAnimeService
import com.sharkaboi.mediahub.modules.anime.repository.AnimeRepository
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
        dataStoreRepository: DataStoreRepository
    ): AnimeRepository =
        AnimeRepository(userAnimeService, dataStoreRepository)
}