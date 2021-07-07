package com.sharkaboi.mediahub.di

import android.content.SharedPreferences
import com.sharkaboi.mediahub.data.api.retrofit.AnimeService
import com.sharkaboi.mediahub.data.datastore.DataStoreRepository
import com.sharkaboi.mediahub.modules.anime_search.repository.AnimeSearchRepository
import com.sharkaboi.mediahub.modules.anime_search.repository.AnimeSearchRepositoryImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityRetainedComponent
import dagger.hilt.android.scopes.ActivityRetainedScoped

@InstallIn(ActivityRetainedComponent::class)
@Module
object AnimeSearchModule {

    @Provides
    @ActivityRetainedScoped
    fun getAnimeSearchRepository(
        animeService: AnimeService,
        dataStoreRepository: DataStoreRepository,
        sharedPreferences: SharedPreferences
    ): AnimeSearchRepository =
        AnimeSearchRepositoryImpl(animeService, dataStoreRepository, sharedPreferences)
}
