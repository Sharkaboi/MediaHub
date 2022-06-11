package com.sharkaboi.mediahub.data.api.enums

import android.content.Context
import com.sharkaboi.mediahub.R

@Suppress("EnumEntryName")
enum class AnimeRankingType {
    all, // Top Anime Series
    airing, // Top Airing Anime
    upcoming, // Top Upcoming Anime
    tv, // Top Anime TV Series
    ova, // Top Anime OVA Series
    movie, // Top Anime Movies
    special, // Top Anime Specials
    bypopularity, // Top Anime by Popularity
    favorite; // Top Favorited Anime

    fun getFormattedString(context: Context): String {
        return when (this) {
            all -> context.getString(R.string.anime_ranking_all)
            airing -> context.getString(R.string.anime_ranking_airing)
            upcoming -> context.getString(R.string.anime_ranking_upcoming)
            tv -> context.getString(R.string.anime_ranking_tv)
            ova -> context.getString(R.string.anime_ranking_ova)
            movie -> context.getString(R.string.anime_ranking_movie)
            special -> context.getString(R.string.anime_ranking_specials)
            bypopularity -> context.getString(R.string.anime_ranking_by_popularity)
            favorite -> context.getString(R.string.anime_ranking_in_your_list)
        }
    }

    companion object {
        fun getAnimeRankingFromString(ranking: String?): AnimeRankingType {
            return when (ranking) {
                null -> all
                else -> runCatching { valueOf(ranking.lowercase()) }.getOrElse { all }
            }
        }
    }
}
