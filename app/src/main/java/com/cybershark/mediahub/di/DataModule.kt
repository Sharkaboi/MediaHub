package com.cybershark.mediahub.di

import android.content.Context
import com.cybershark.mediahub.data.api.APIConstants
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import dagger.hilt.android.qualifiers.ApplicationContext
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(ApplicationComponent::class)
object DataModule {

    @Provides
    @Singleton
    fun getTraktRetrofitService(@ApplicationContext context: Context): Retrofit {
        return Retrofit.Builder()
            .baseUrl(APIConstants.TRAKT_STAGING_API_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }
}