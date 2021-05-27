package com.sharkaboi.mediahub.di

import com.sharkaboi.mediahub.common.data.datastore.DataStoreRepository
import com.sharkaboi.mediahub.common.data.retrofit.MangaService
import com.sharkaboi.mediahub.modules.manga_ranking.repository.MangaRankingRepository
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
        dataStoreRepository: DataStoreRepository
    ): MangaRankingRepository =
        MangaRankingRepository(mangaService, dataStoreRepository)
}