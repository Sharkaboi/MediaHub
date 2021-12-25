package com.sharkaboi.mediahub.modules.anime_list.di

import android.content.SharedPreferences
import com.sharkaboi.mediahub.data.api.retrofit.UserAnimeService
import com.sharkaboi.mediahub.data.datastore.DataStoreRepository
import com.sharkaboi.mediahub.modules.anime_list.repository.AnimeListRepository
import com.sharkaboi.mediahub.modules.anime_list.repository.AnimeListRepositoryImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityRetainedComponent
import dagger.hilt.android.scopes.ActivityRetainedScoped

@InstallIn(ActivityRetainedComponent::class)
@Module
object AnimeListModule {

    @Provides
    @ActivityRetainedScoped
    fun getAnimeRepository(
        userAnimeService: UserAnimeService,
        dataStoreRepository: DataStoreRepository,
        sharedPreferences: SharedPreferences
    ): AnimeListRepository =
        AnimeListRepositoryImpl(userAnimeService, dataStoreRepository, sharedPreferences)
}
