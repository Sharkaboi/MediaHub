package com.sharkaboi.mediahub.modules.anime_details.util

import com.sharkaboi.mediahub.data.api.enums.AnimeStatus

data class AnimeDetailsUpdateClass(
    val animeStatus: AnimeStatus?,
    val score: Int?,
    val numWatchedEpisode: Int?,
    val totalEps: Int,
    val animeId: Int
)
