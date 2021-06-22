package com.sharkaboi.mediahub.di

import android.content.SharedPreferences
import com.sharkaboi.mediahub.data.api.retrofit.AnimeService
import com.sharkaboi.mediahub.data.datastore.DataStoreRepository
import com.sharkaboi.mediahub.modules.anime_ranking.repository.AnimeRankingRepository
import com.sharkaboi.mediahub.modules.anime_ranking.repository.AnimeRankingRepositoryImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityRetainedComponent
import dagger.hilt.android.scopes.ActivityRetainedScoped

@InstallIn(ActivityRetainedComponent::class)
@Module
object AnimeRankingModule {

    @Provides
    @ActivityRetainedScoped
    fun getAnimeRankingRepository(
        animeService: AnimeService,
        dataStoreRepository: DataStoreRepository,
        sharedPreferences: SharedPreferences
    ): AnimeRankingRepository =
        AnimeRankingRepositoryImpl(animeService, dataStoreRepository, sharedPreferences)
}