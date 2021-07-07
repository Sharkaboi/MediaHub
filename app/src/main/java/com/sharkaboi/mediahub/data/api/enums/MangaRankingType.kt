package com.sharkaboi.mediahub.data.api.enums

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

    fun getFormattedString(): String {
        return when (this) {
            all -> "All"
            manga -> "Manga"
            oneshots -> "One-shots"
            doujin -> "Doujins"
            lightnovels -> "Light novels"
            novels -> "Novels"
            manhwa -> "Manhwa"
            manhua -> "Manhua"
            bypopularity -> "By popularity"
            favorite -> "In your list"
        }
    }
}
