package com.sharkaboi.mediahub.di

import com.sharkaboi.mediahub.common.data.datastore.DataStoreRepository
import com.sharkaboi.mediahub.common.data.retrofit.AnimeService
import com.sharkaboi.mediahub.modules.anime_suggestions.repository.AnimeSuggestionsRepository
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
        dataStoreRepository: DataStoreRepository
    ): AnimeSuggestionsRepository =
        AnimeSuggestionsRepository(animeService, dataStoreRepository)
}