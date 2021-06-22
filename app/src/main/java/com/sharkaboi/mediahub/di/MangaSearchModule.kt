package com.sharkaboi.mediahub.di

import android.content.SharedPreferences
import com.sharkaboi.mediahub.data.api.retrofit.MangaService
import com.sharkaboi.mediahub.data.datastore.DataStoreRepository
import com.sharkaboi.mediahub.modules.manga_search.repository.MangaSearchRepository
import com.sharkaboi.mediahub.modules.manga_search.repository.MangaSearchRepositoryImpl
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
        dataStoreRepository: DataStoreRepository,
        sharedPreferences: SharedPreferences
    ): MangaSearchRepository =
        MangaSearchRepositoryImpl(mangaService, dataStoreRepository, sharedPreferences)
}