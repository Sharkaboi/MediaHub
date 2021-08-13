@file:Suppress("EnumEntryName")

package com.sharkaboi.mediahub.data.api.enums

import android.content.Context
import com.sharkaboi.mediahub.R

enum class AnimeAiringStatus {
    finished_airing,
    currently_airing,
    not_yet_aired
}

internal fun Context.getAnimeAiringStatus(status: String): String {
    return when {
        status.trim() == AnimeAiringStatus.finished_airing.name -> {
            getString(R.string.anime_airing_finished)
        }
        status.trim() == AnimeAiringStatus.currently_airing.name -> {
            getString(R.string.anime_airing_ongoing)
        }
        status.trim() == AnimeAiringStatus.not_yet_aired.name -> {
            getString(R.string.anime_airing_not_yet_aired)
        }
        else -> {
            getString(R.string.anime_airing_unknown)
        }
    }
}
