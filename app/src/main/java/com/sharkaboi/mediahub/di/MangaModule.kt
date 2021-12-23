package com.sharkaboi.mediahub.di

import android.content.SharedPreferences
import com.sharkaboi.mediahub.data.api.retrofit.UserMangaService
import com.sharkaboi.mediahub.data.datastore.DataStoreRepository
import com.sharkaboi.mediahub.modules.manga_list.repository.MangaRepository
import com.sharkaboi.mediahub.modules.manga_list.repository.MangaRepositoryImpl
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
        dataStoreRepository: DataStoreRepository,
        sharedPreferences: SharedPreferences
    ): MangaRepository =
        MangaRepositoryImpl(userMangaService, dataStoreRepository, sharedPreferences)
}
