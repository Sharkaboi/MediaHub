package com.sharkaboi.mediahub.di

import android.content.SharedPreferences
import com.sharkaboi.mediahub.data.api.retrofit.AnimeService
import com.sharkaboi.mediahub.data.datastore.DataStoreRepository
import com.sharkaboi.mediahub.modules.anime_suggestions.repository.AnimeSuggestionsRepository
import com.sharkaboi.mediahub.modules.anime_suggestions.repository.AnimeSuggestionsRepositoryImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityRetainedComponent
import dagger.hilt.android.scopes.ActivityRetainedScoped

@InstallIn(ActivityRetainedComponent::class)
@Module
object AnimeSuggestionsModule {

    @Provides
    @ActivityRetainedScoped
    fun getAnimeSuggestionsRepository(
        animeService: AnimeService,
        dataStoreRepository: DataStoreRepository,
        sharedPreferences: SharedPreferences
    ): AnimeSuggestionsRepository =
        AnimeSuggestionsRepositoryImpl(animeService, dataStoreRepository, sharedPreferences)
}