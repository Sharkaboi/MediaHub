package com.sharkaboi.mediahub.di

import com.sharkaboi.mediahub.common.data.datastore.DataStoreRepository
import com.sharkaboi.mediahub.common.data.retrofit.AuthService
import com.sharkaboi.mediahub.modules.auth.OAuthRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityRetainedComponent
import dagger.hilt.android.scopes.ActivityRetainedScoped

@InstallIn(ActivityRetainedComponent::class)
@Module
object AuthModule {

    @Provides
    @ActivityRetainedScoped
    fun getOAuthRepository(
        authService: AuthService,
        dataStoreRepository: DataStoreRepository
    ): OAuthRepository =
        OAuthRepository(authService, dataStoreRepository)
}