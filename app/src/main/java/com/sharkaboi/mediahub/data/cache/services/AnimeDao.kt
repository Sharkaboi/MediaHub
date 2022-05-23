package com.sharkaboi.mediahub.data.cache.services

import androidx.lifecycle.LiveData
import androidx.room.*
import com.sharkaboi.mediahub.data.cache.models.anime.AnimeWithStudios

@Dao
interface AnimeDao {

    @Transaction
    @Query("SELECT * FROM AnimeDetails")
    fun getAnimeDetails(animeId: Int): LiveData<AnimeWithStudios>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insertAnime(animeWithStudios: AnimeWithStudios): Long

    @Update(onConflict = OnConflictStrategy.IGNORE)
    fun updateAnime(animeWithStudios: AnimeWithStudios)

    @Transaction
    fun upsertAnime(animeWithStudios: AnimeWithStudios) {
        val id = insertAnime(animeWithStudios)
        if (id == -1L) {
            updateAnime(animeWithStudios)
        }
    }
}