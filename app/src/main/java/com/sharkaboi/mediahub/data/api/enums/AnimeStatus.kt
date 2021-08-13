package com.sharkaboi.mediahub.data.api.enums

import android.content.Context
import com.sharkaboi.mediahub.R

@Suppress("EnumEntryName")
enum class AnimeStatus {
    watching,
    plan_to_watch,
    completed,
    on_hold,
    dropped,
    all;

    fun getFormattedString(context: Context): String {
        return when (this) {
            watching -> context.getString(R.string.anime_status_watching)
            plan_to_watch -> context.getString(R.string.anime_status_planned)
            completed -> context.getString(R.string.anime_status_completed)
            on_hold -> context.getString(R.string.anime_status_on_hold)
            dropped -> context.getString(R.string.anime_status_dropped)
            all -> context.getString(R.string.anime_status_all)
        }
    }

    companion object {
        val malStatuses = values().filter { it != all }
    }
}

fun String.animeStatusFromString(): AnimeStatus? {
    return when (this) {
        AnimeStatus.watching.name -> AnimeStatus.watching
        AnimeStatus.plan_to_watch.name -> AnimeStatus.plan_to_watch
        AnimeStatus.completed.name -> AnimeStatus.completed
        AnimeStatus.on_hold.name -> AnimeStatus.on_hold
        AnimeStatus.dropped.name -> AnimeStatus.dropped
        else -> null // AnimeStatus.all
    }
}
