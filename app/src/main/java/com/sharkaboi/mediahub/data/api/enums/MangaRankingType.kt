package com.sharkaboi.mediahub.data.api.enums

import android.content.Context
import com.sharkaboi.mediahub.R

@Suppress("EnumEntryName")
enum class MangaRankingType {
    all, // All
    manga, // Top Manga
    oneshots, // Top One-shots
    doujin, // Top Doujinshi
    lightnovels, // Top Light Novels
    novels, // Top Novels
    manhwa, // Top Manhwa
    manhua, // Top Manhua
    bypopularity, // Most Popular
    favorite; // Most Favorited

    fun getFormattedString(context: Context): String {
        return when (this) {
            all -> context.getString(R.string.manga_ranking_all)
            manga -> context.getString(R.string.manga_ranking_manga)
            oneshots -> context.getString(R.string.manga_ranking_one_shot)
            doujin -> context.getString(R.string.manga_ranking_doujins)
            lightnovels -> context.getString(R.string.manga_ranking_light_novels)
            novels -> context.getString(R.string.manga_ranking_novels)
            manhwa -> context.getString(R.string.manga_ranking_manhwa)
            manhua -> context.getString(R.string.manga_ranking_manhua)
            bypopularity -> context.getString(R.string.manga_ranking_by_popularity)
            favorite -> context.getString(R.string.manga_ranking_in_your_list)
        }
    }

    companion object {
        private const val lightNovelSlug = "light_novel"
        private const val oneShotSlug = "one_shot"
        private const val doujinshiSlug = "doujinshi"
        fun getMangaRankingFromString(ranking: String?): MangaRankingType {
            return when (ranking?.lowercase()) {
                null -> all
                lightNovelSlug -> lightnovels
                oneShotSlug -> oneshots
                doujinshiSlug -> doujin
                else -> runCatching { valueOf(ranking.lowercase()) }.getOrElse { all }
            }
        }
    }
}
