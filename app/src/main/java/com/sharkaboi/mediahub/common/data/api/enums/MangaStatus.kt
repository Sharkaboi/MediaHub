package com.sharkaboi.mediahub.common.data.api.enums

@Suppress("EnumEntryName")
enum class MangaStatus {
    reading,
    plan_to_read,
    completed,
    on_hold,
    dropped,
    all;

    fun getFormattedString(): String {
        return when (this) {
            reading -> "Reading"
            plan_to_read -> "Planned"
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

fun String.mangaStatusFromString(): MangaStatus? {
    return when (this) {
        "reading" -> MangaStatus.reading
        "plan_to_read" -> MangaStatus.plan_to_read
        "completed" -> MangaStatus.completed
        "on_hold" -> MangaStatus.on_hold
        "dropped" -> MangaStatus.dropped
        else -> null //MangaStatus.all
    }
}
