package com.sharkaboi.mediahub.modules.anime_details.di

import com.apollographql.apollo.ApolloClient
import com.sharkaboi.mediahub.data.api.retrofit.AnimeService
import com.sharkaboi.mediahub.data.api.retrofit.UserAnimeService
import com.sharkaboi.mediahub.data.datastore.DataStoreRepository
import com.sharkaboi.mediahub.modules.anime_details.repository.AnimeDetailsRepository
import com.sharkaboi.mediahub.modules.anime_details.repository.AnimeDetailsRepositoryImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityRetainedComponent
import dagger.hilt.android.scopes.ActivityRetainedScoped

@InstallIn(ActivityRetainedComponent::class)
@Module
object AnimeDetailsModule {

    @Provides
    @ActivityRetainedScoped
    fun getAnimeDetailsRepository(
        animeService: AnimeService,
        userAnimeService: UserAnimeService,
        apolloClient: ApolloClient,
        dataStoreRepository: DataStoreRepository
    ): AnimeDetailsRepository =
        AnimeDetailsRepositoryImpl(
            animeService,
            userAnimeService,
            apolloClient,
            dataStoreRepository
        )
}
