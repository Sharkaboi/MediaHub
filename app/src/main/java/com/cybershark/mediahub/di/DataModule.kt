package com.cybershark.mediahub.di

import android.content.Context
import com.cybershark.mediahub.data.api.APIConstants
import com.cybershark.mediahub.data.api.TraktAuthClient
import com.cybershark.mediahub.data.api.TraktGeneralMediaClient
import com.cybershark.mediahub.data.api.TraktUserMediaClient
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
    fun getTraktRetrofitBuilder(@ApplicationContext context: Context): Retrofit.Builder {
        return Retrofit.Builder()
            .baseUrl(APIConstants.TRAKT_STAGING_API_URL)
            .addConverterFactory(GsonConverterFactory.create())
    }

    @Provides
    @Singleton
    fun getTraktAuthService(retrofit: Retrofit.Builder) : TraktAuthClient{
        return retrofit
            .build()
            .create(TraktAuthClient::class.java)
    }

    @Provides
    @Singleton
    fun getTraktUserMediaService(retrofit: Retrofit.Builder) : TraktUserMediaClient {
        return retrofit
            .build()
            .create(TraktUserMediaClient::class.java)
    }

    @Provides
    @Singleton
    fun getTraktGeneralMediaService(retrofit: Retrofit.Builder) : TraktGeneralMediaClient {
        return retrofit
            .build()
            .create(TraktGeneralMediaClient::class.java)
    }
}