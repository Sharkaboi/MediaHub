package com.sharkaboi.mediahub.di

import com.sharkaboi.mediahub.common.data.datastore.DataStoreRepository
import com.sharkaboi.mediahub.common.data.retrofit.MangaService
import com.sharkaboi.mediahub.modules.manga_search.repository.MangaSearchRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityRetainedComponent
import dagger.hilt.android.scopes.ActivityRetainedScoped

@InstallIn(ActivityRetainedComponent::class)
@Module
object MangaSearchModule {

    @Provides
    @ActivityRetainedScoped
    fun getMangaSearchRepository(
        mangaService: MangaService,
        dataStoreRepository: DataStoreRepository
    ): MangaSearchRepository =
        MangaSearchRepository(mangaService, dataStoreRepository)
}