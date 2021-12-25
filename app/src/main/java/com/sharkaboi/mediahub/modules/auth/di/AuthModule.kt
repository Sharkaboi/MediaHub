package com.sharkaboi.mediahub.modules.auth.di

import com.sharkaboi.mediahub.data.api.retrofit.AuthService
import com.sharkaboi.mediahub.data.datastore.DataStoreRepository
import com.sharkaboi.mediahub.modules.auth.repository.OAuthRepository
import com.sharkaboi.mediahub.modules.auth.repository.OAuthRepositoryImpl
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
        OAuthRepositoryImpl(authService, dataStoreRepository)
}
