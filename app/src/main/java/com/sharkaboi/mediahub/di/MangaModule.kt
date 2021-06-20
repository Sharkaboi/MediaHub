package com.sharkaboi.mediahub.di

import com.sharkaboi.mediahub.data.datastore.DataStoreRepository
import com.sharkaboi.mediahub.data.api.retrofit.UserMangaService
import com.sharkaboi.mediahub.modules.manga.repository.MangaRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityRetainedComponent
import dagger.hilt.android.scopes.ActivityRetainedScoped

@InstallIn(ActivityRetainedComponent::class)
@Module
object MangaModule {

    @Provides
    @ActivityRetainedScoped
    fun getMangaRepository(
        userMangaService: UserMangaService,
        dataStoreRepository: DataStoreRepository
    ): MangaRepository =
        MangaRepository(userMangaService, dataStoreRepository)
}