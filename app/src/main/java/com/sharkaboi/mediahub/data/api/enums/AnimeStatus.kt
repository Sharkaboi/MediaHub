package com.sharkaboi.mediahub.data.api.enums

@Suppress("EnumEntryName")
enum class AnimeStatus {
    watching,
    plan_to_watch,
    completed,
    on_hold,
    dropped,
    all;

    fun getFormattedString(): String {
        return when (this) {
            watching -> "Watching"
            plan_to_watch -> "Planned"
            completed -> "Completed"
            on_hold -> "On hold"
            dropped -> "Dropped"
            all -> "All"
        }
    }

    companion object {
        val malStatuses = values().filter { it != all }
    }
}

fun String.animeStatusFromString(): AnimeStatus? {
    return when (this) {
        "watching" -> AnimeStatus.watching
        "plan_to_watch" -> AnimeStatus.plan_to_watch
        "completed" -> AnimeStatus.completed
        "on_hold" -> AnimeStatus.on_hold
        "dropped" -> AnimeStatus.dropped
        else -> null // AnimeStatus.all
    }
}
