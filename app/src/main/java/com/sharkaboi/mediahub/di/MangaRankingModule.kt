package com.sharkaboi.mediahub.di

import android.content.SharedPreferences
import com.sharkaboi.mediahub.data.api.retrofit.MangaService
import com.sharkaboi.mediahub.data.datastore.DataStoreRepository
import com.sharkaboi.mediahub.modules.manga_ranking.repository.MangaRankingRepository
import com.sharkaboi.mediahub.modules.manga_ranking.repository.MangaRankingRepositoryImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityRetainedComponent
import dagger.hilt.android.scopes.ActivityRetainedScoped

@InstallIn(ActivityRetainedComponent::class)
@Module
object MangaRankingModule {

    @Provides
    @ActivityRetainedScoped
    fun getMangaRankingRepository(
        mangaService: MangaService,
        dataStoreRepository: DataStoreRepository,
        sharedPreferences: SharedPreferences
    ): MangaRankingRepository =
        MangaRankingRepositoryImpl(mangaService, dataStoreRepository, sharedPreferences)
}