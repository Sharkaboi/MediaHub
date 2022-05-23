package com.sharkaboi.mediahub.data.cache

import androidx.room.Database
import androidx.room.RoomDatabase
import com.sharkaboi.mediahub.data.cache.models.anime.*
import com.sharkaboi.mediahub.data.cache.services.AnimeDao

@Database(
    entities = [
        AnimeWithStudios::class,
        AnimeGenre::class,
        AnimePicture::class,
        AnimeRecommendations::class,
        AnimeStudio::class,
        RelatedAnime::class,
        RelatedManga::class
    ],
    version = 1
)
abstract class MediaHubDb : RoomDatabase() {
    abstract fun getAnimeDao(): AnimeDao
}