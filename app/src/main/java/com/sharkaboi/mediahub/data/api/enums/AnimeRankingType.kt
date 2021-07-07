package com.sharkaboi.mediahub.data.api.enums

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

    fun getAnimeRanking(): String {
        return when (this) {
            all -> "All"
            airing -> "Airing"
            upcoming -> "Upcoming"
            tv -> "TV"
            ova -> "OVA"
            movie -> "Movie"
            special -> "Specials"
            bypopularity -> "By popularity"
            favorite -> "In your list"
        }
    }
}
