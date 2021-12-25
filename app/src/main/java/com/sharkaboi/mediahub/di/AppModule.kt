package com.sharkaboi.mediahub.di

import android.content.Context
import android.content.SharedPreferences
import androidx.preference.PreferenceManager
import com.apollographql.apollo.ApolloClient
import com.haroldadmin.cnradapter.NetworkResponseAdapterFactory
import com.sharkaboi.mediahub.BuildConfig
import com.sharkaboi.mediahub.data.api.retrofit.*
import com.sharkaboi.mediahub.data.datastore.DataStoreRepository
import com.sharkaboi.mediahub.data.datastore.DataStoreRepositoryImpl
import com.sharkaboi.mediahub.data.datastore.dataStore
import com.squareup.moshi.Moshi
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import javax.inject.Qualifier
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
object AppModule {

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
    fun getMoshi(): Moshi = Moshi.Builder().build()

    @Provides
    @Singleton
    @MALRetrofitClient
    fun getRetrofitBuilder(okHttpClient: OkHttpClient, moshi: Moshi): Retrofit =
        Retrofit.Builder()
            .baseUrl("https://api.myanimelist.net/v2/")
            .addCallAdapterFactory(NetworkResponseAdapterFactory())
            .addConverterFactory(
                MoshiConverterFactory.create(moshi)
            )
            .client(okHttpClient)
            .build()

    @Provides
    @Singleton
    @AniListApolloClient
    fun getApolloClient(okHttpClient: OkHttpClient): ApolloClient =
        ApolloClient.builder()
            .serverUrl("https://graphql.anilist.co")
            .okHttpClient(okHttpClient)
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
    fun getAuthService(@AniListApolloClient retrofit: Retrofit): AuthService =
        retrofit.create(AuthService::class.java)

    @Provides
    @Singleton
    fun getAnimeService(@AniListApolloClient retrofit: Retrofit): AnimeService =
        retrofit.create(AnimeService::class.java)

    @Provides
    @Singleton
    fun getMangaService(@AniListApolloClient retrofit: Retrofit): MangaService =
        retrofit.create(MangaService::class.java)

    @Provides
    @Singleton
    fun getUserAnimeService(@AniListApolloClient retrofit: Retrofit): UserAnimeService =
        retrofit.create(UserAnimeService::class.java)

    @Provides
    @Singleton
    fun getUserMangaService(@AniListApolloClient retrofit: Retrofit): UserMangaService =
        retrofit.create(UserMangaService::class.java)

    @Provides
    @Singleton
    fun getUserService(@AniListApolloClient retrofit: Retrofit): UserService =
        retrofit.create(UserService::class.java)
}

@Qualifier
@Retention(AnnotationRetention.BINARY)
annotation class MALRetrofitClient

@Qualifier
@Retention(AnnotationRetention.BINARY)
annotation class AniListApolloClient
