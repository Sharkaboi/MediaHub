package com.sharkaboi.mediahub.di

import android.content.Context
import android.content.SharedPreferences
import androidx.preference.PreferenceManager
import com.haroldadmin.cnradapter.NetworkResponseAdapterFactory
import com.sharkaboi.mediahub.BuildConfig
import com.sharkaboi.mediahub.data.api.retrofit.*
import com.sharkaboi.mediahub.data.datastore.DataStoreRepository
import com.sharkaboi.mediahub.data.datastore.DataStoreRepositoryImpl
import com.sharkaboi.mediahub.data.datastore.dataStore
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
object DataModule {

    @Provides
    @Singleton
    fun getLoggingInterceptor(): HttpLoggingInterceptor =
        HttpLoggingInterceptor().apply { level = HttpLoggingInterceptor.Level.BASIC }

    @Provides
    @Singleton
    fun getOkHttpClient(httpLoggingInterceptor: HttpLoggingInterceptor): OkHttpClient {
        return OkHttpClient.Builder().apply {
            if (BuildConfig.DEBUG) {
                addInterceptor(httpLoggingInterceptor)
            }
        }.build()
    }

    @Provides
    @Singleton
    fun getRetrofitBuilder(okHttpClient: OkHttpClient): Retrofit =
        Retrofit.Builder()
            .baseUrl("https://api.myanimelist.net/v2/")
            .addCallAdapterFactory(NetworkResponseAdapterFactory())
            .addConverterFactory(
                MoshiConverterFactory.create(
                    Moshi.Builder()
                        .addLast(KotlinJsonAdapterFactory())
                        .build()
                )
            )
            .client(okHttpClient)
            .build()

    @Provides
    @Singleton
    fun getDataStoreRepository(@ApplicationContext context: Context): DataStoreRepository =
        DataStoreRepositoryImpl(context.dataStore)

    @Provides
    @Singleton
    fun getSharedPreferences(@ApplicationContext context: Context): SharedPreferences =
        PreferenceManager.getDefaultSharedPreferences(context)

    @Provides
    @Singleton
    fun getAuthService(retrofit: Retrofit): AuthService =
        retrofit.create(AuthService::class.java)

    @Provides
    @Singleton
    fun getAnimeService(retrofit: Retrofit): AnimeService =
        retrofit.create(AnimeService::class.java)

    @Provides
    @Singleton
    fun getMangaService(retrofit: Retrofit): MangaService =
        retrofit.create(MangaService::class.java)

    @Provides
    @Singleton
    fun getUserAnimeService(retrofit: Retrofit): UserAnimeService =
        retrofit.create(UserAnimeService::class.java)

    @Provides
    @Singleton
    fun getUserMangaService(retrofit: Retrofit): UserMangaService =
        retrofit.create(UserMangaService::class.java)

    @Provides
    @Singleton
    fun getUserService(retrofit: Retrofit): UserService =
        retrofit.create(UserService::class.java)
}
