package com.sharkaboi.mediahub.modules.anime_seasonal.util

import com.sharkaboi.mediahub.data.api.enums.AnimeSeason
import com.sharkaboi.mediahub.data.api.enums.getAnimeSeason
import java.time.LocalDate

data class AnimeSeasonWrapper(
    val animeSeason: AnimeSeason,
    val year: Int
) {
    fun next(): AnimeSeasonWrapper {
        val (season, shouldYearChange) = animeSeason.next()
        return AnimeSeasonWrapper(
            animeSeason = season,
            year = if (shouldYearChange) {
                LocalDate.of(year, 1, 1).plusYears(1L).year
            } else {
                year
            }
        )
    }

    fun prev(): AnimeSeasonWrapper {
        val (season, shouldYearChange) = animeSeason.previous()
        return AnimeSeasonWrapper(
            animeSeason = season,
            year = if (shouldYearChange) {
                LocalDate.of(year, 1, 1).minusYears(1L).year
            } else {
                year
            }
        )
    }

    companion object {
        fun currentSeason(): AnimeSeasonWrapper {
            val now = LocalDate.now()
            return AnimeSeasonWrapper(
                animeSeason = now.getAnimeSeason(),
                year = now.year
            )
        }

        fun parseFrom(season: AnimeSeason?, year: Int): AnimeSeasonWrapper? {
            return when {
                season == null -> null
                year == 0 -> null
                else -> AnimeSeasonWrapper(season, year)
            }
        }
    }
}
