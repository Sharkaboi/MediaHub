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

        fun parse(string: String?): AnimeStatus? {
            if (string == null) {
                return null
            }
            return runCatching {
                valueOf(string)
            }.getOrNull()
        }
    }
}
