package com.sharkaboi.mediahub.common.data.api.enums

@Suppress("EnumEntryName")
enum class MangaRankingType {
    all, //All
    manga, //Top Manga
    oneshots, //Top One-shots
    doujin, //Top Doujinshi
    lightnovels, //Top Light Novels
    novels, //Top Novels
    manhwa, //Top Manhwa
    manhua, //Top Manhua
    bypopularity, //Most Popular
    favorite, //Most Favorited
}

internal fun MangaRankingType.getMangaRanking(): String {
    return when (this) {
        MangaRankingType.all -> "All"
        MangaRankingType.manga -> "Manga"
        MangaRankingType.oneshots -> "One-shots"
        MangaRankingType.doujin -> "Doujins"
        MangaRankingType.lightnovels -> "Light novels"
        MangaRankingType.novels -> "Novels"
        MangaRankingType.manhwa -> "Manhwa"
        MangaRankingType.manhua -> "Manhua"
        MangaRankingType.bypopularity -> "By popularity"
        MangaRankingType.favorite -> "In your list"
    }
}