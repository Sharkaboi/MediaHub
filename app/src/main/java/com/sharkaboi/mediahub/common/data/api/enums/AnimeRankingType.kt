package com.sharkaboi.mediahub.common.data.api.enums

@Suppress("EnumEntryName")
enum class AnimeRankingType {
    all, //Top Anime Series
    airing, //Top Airing Anime
    upcoming, //Top Upcoming Anime
    tv, //Top Anime TV Series
    ova, //Top Anime OVA Series
    movie, //Top Anime Movies
    special, //Top Anime Specials
    bypopularity, //Top Anime by Popularity
    favorite //Top Favorited Anime
}

internal fun AnimeRankingType.getAnimeRanking(): String {
    return when (this) {
        AnimeRankingType.all -> "All"
        AnimeRankingType.airing -> "Airing"
        AnimeRankingType.upcoming -> "Upcoming"
        AnimeRankingType.tv -> "TV"
        AnimeRankingType.ova -> "OVA"
        AnimeRankingType.movie -> "Movie"
        AnimeRankingType.special -> "Specials"
        AnimeRankingType.bypopularity -> "By popularity"
        AnimeRankingType.favorite -> "In your list"
    }
}