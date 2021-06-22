package com.sharkaboi.mediahub.di

import com.sharkaboi.mediahub.data.api.retrofit.UserService
import com.sharkaboi.mediahub.data.datastore.DataStoreRepository
import com.sharkaboi.mediahub.modules.profile.repository.ProfileRepository
import com.sharkaboi.mediahub.modules.profile.repository.ProfileRepositoryImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityRetainedComponent
import dagger.hilt.android.scopes.ActivityRetainedScoped

@InstallIn(ActivityRetainedComponent::class)
@Module
object ProfileModule {

    @Provides
    @ActivityRetainedScoped
    fun getProfileRepository(
        userService: UserService,
        dataStoreRepository: DataStoreRepository
    ): ProfileRepository =
        ProfileRepositoryImpl(userService, dataStoreRepository)
}