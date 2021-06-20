package com.sharkaboi.mediahub.di

import com.sharkaboi.mediahub.data.datastore.DataStoreRepository
import com.sharkaboi.mediahub.data.api.retrofit.MangaService
import com.sharkaboi.mediahub.data.api.retrofit.UserMangaService
import com.sharkaboi.mediahub.modules.manga_details.repository.MangaDetailsRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityRetainedComponent
import dagger.hilt.android.scopes.ActivityRetainedScoped

@InstallIn(ActivityRetainedComponent::class)
@Module
object MangaDetailsModule {

    @Provides
    @ActivityRetainedScoped
    fun getMangaDetailsRepository(
        mangaService: MangaService,
        userMangaService: UserMangaService,
        dataStoreRepository: DataStoreRepository
    ): MangaDetailsRepository =
        MangaDetailsRepository(mangaService, userMangaService, dataStoreRepository)
}