package com.sharkaboi.mediahub.data.api.enums

import android.content.Context
import com.sharkaboi.mediahub.R

@Suppress("EnumEntryName")
enum class MangaStatus {
    reading,
    plan_to_read,
    completed,
    on_hold,
    dropped,
    all;

    fun getFormattedString(context: Context): String {
        return when (this) {
            reading -> context.getString(R.string.manga_status_reading)
            plan_to_read -> context.getString(R.string.manga_status_planned)
            completed -> context.getString(R.string.manga_status_completed)
            on_hold -> context.getString(R.string.manga_status_on_hold)
            dropped -> context.getString(R.string.manga_status_dropped)
            all -> context.getString(R.string.manga_status_all)
        }
    }

    companion object {
        val malStatuses = values().filter { it != all }
    }
}

fun String.mangaStatusFromString(): MangaStatus? {
    return when (this) {
        MangaStatus.reading.name -> MangaStatus.reading
        MangaStatus.plan_to_read.name -> MangaStatus.plan_to_read
        MangaStatus.completed.name -> MangaStatus.completed
        MangaStatus.on_hold.name -> MangaStatus.on_hold
        MangaStatus.dropped.name -> MangaStatus.dropped
        else -> null // MangaStatus.all
    }
}
