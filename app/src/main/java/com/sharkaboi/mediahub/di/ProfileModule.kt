package com.sharkaboi.mediahub.di

import com.sharkaboi.mediahub.common.data.datastore.DataStoreRepository
import com.sharkaboi.mediahub.common.data.retrofit.UserService
import com.sharkaboi.mediahub.modules.profile.repository.ProfileRepository
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
        ProfileRepository(userService, dataStoreRepository)
}