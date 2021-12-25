package com.sharkaboi.mediahub.modules.manga_list.di

import android.content.SharedPreferences
import com.sharkaboi.mediahub.data.api.retrofit.UserMangaService
import com.sharkaboi.mediahub.data.datastore.DataStoreRepository
import com.sharkaboi.mediahub.modules.manga_list.repository.MangaListRepository
import com.sharkaboi.mediahub.modules.manga_list.repository.MangaListRepositoryImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityRetainedComponent
import dagger.hilt.android.scopes.ActivityRetainedScoped

@InstallIn(ActivityRetainedComponent::class)
@Module
object MangaListModule {

    @Provides
    @ActivityRetainedScoped
    fun getMangaRepository(
        userMangaService: UserMangaService,
        dataStoreRepository: DataStoreRepository,
        sharedPreferences: SharedPreferences
    ): MangaListRepository =
        MangaListRepositoryImpl(userMangaService, dataStoreRepository, sharedPreferences)
}
