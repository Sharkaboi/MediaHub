package com.sharkaboi.mediahub.common.extensions

import android.text.Html
import android.text.Spanned
import com.sharkaboi.mediahub.common.data.api.models.anime.AnimeByIDResponse
import com.sharkaboi.mediahub.common.data.api.models.manga.MangaByIDResponse
import java.text.DecimalFormat
import java.time.*
import java.time.format.DateTimeFormatter
import java.time.temporal.WeekFields
import java.util.*

internal fun Double.roundOfString(): String {
    if (this == 0.0) {
        return "0"
    }
    val format = DecimalFormat("#.##")
    return format.format(this)
}

internal fun String.getNsfwRating(): String {
    return when {
        this.trim() == "white" -> {
            "SFW"
        }
        this.trim() == "gray" -> {
            "NSFW?"
        }
        this.trim() == "black" -> {
            "NSFW"
        }
        else -> {
            "SFW : N/A"
        }
    }
}

internal fun String.getRating(): String {
    return when {
        this.trim() == "g" -> {
            "G - All ages"
        }
        this.trim() == "pg" -> {
            "PG"
        }
        this.trim() == "pg_13" -> {
            "PG 13"
        }
        this.trim() == "r" -> {
            "R - 17+."
        }
        this.trim() == "r+" -> {
            "R+"
        }
        this.trim() == "rx" -> {
            "Rx - Hentai."
        }
        else -> {
            "N/A"
        }
    }
}

internal fun String.getAnimeAiringStatus(): String {
    return when {
        this.trim() == "finished_airing" -> {
            "Finished airing"
        }
        this.trim() == "currently_airing" -> {
            "Currently airing"
        }
        this.trim() == "not_yet_aired" -> {
            "Yet to be aired"
        }
        else -> {
            "N/A"
        }
    }
}

internal fun String.getMangaPublishStatus(): String {
    return when {
        this.trim() == "finished" -> {
            "Finished publishing"
        }
        this.trim() == "currently_publishing" -> {
            "Currently publishing"
        }
        this.trim() == "not_yet_published" -> {
            "Yet to be published"
        }
        else -> {
            "N/A"
        }
    }
}

internal fun AnimeByIDResponse.Broadcast.getBroadcastTime(): String {
    try {
        if (this.startTime == null) {
            return "On ${this.dayOfTheWeek}"
        }
        val dayOfWeek = DayOfWeek.valueOf(this.dayOfTheWeek.toUpperCase(Locale.ROOT))
        val fieldIso = WeekFields.of(Locale.FRANCE).dayOfWeek()
        val now = OffsetDateTime.now().with(fieldIso, dayOfWeek.value.toLong())
        val (hour, mins) = this.startTime.split(":").map { it.toInt() }
        val japanTime = ZonedDateTime.of(
            now.year,
            now.month.value,
            now.dayOfMonth,
            hour,
            mins,
            0,
            0,
            ZoneOffset.ofHoursMinutes(9, 0)
        )
        val localTimeZone = ZoneId.systemDefault()
        val localTime = japanTime.withZoneSameInstant(localTimeZone)
        return localTime.format(DateTimeFormatter.ofPattern("EEEE h:mm a"))
    } catch (e: Exception) {
        e.printStackTrace()
        return "N/A"
    }
}

internal fun AnimeByIDResponse.AlternativeTitles.getFormattedString(): Spanned {
    return if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.N) {
        Html.fromHtml(
            """
            <b>English title</b> : ${
                this.en?.let {
                    if (it.isBlank()) {
                        "N/A"
                    } else {
                        it
                    }
                } ?: "N/A"
            }<br>
            <b>Japanese title</b> : ${
                this.ja?.let {
                    if (it.isBlank()) {
                        "N/A"
                    } else {
                        it
                    }
                } ?: "N/A"
            }<br>
            <b>Synonyms</b> : ${
                this.synonyms?.let {
                    if (it.isEmpty()) {
                        "N/A"
                    } else {
                        it.joinToString()
                    }
                } ?: "N/A"
            }
        """.trimIndent(), Html.FROM_HTML_MODE_COMPACT
        )
    } else {
        Html.fromHtml(
            """
            <b>English title</b> : ${
                this.en?.let {
                    if (it.isBlank()) {
                        "N/A"
                    } else {
                        it
                    }
                } ?: "N/A"
            }<br>
            <b>Japanese title</b> : ${
                this.ja?.let {
                    if (it.isBlank()) {
                        "N/A"
                    } else {
                        it
                    }
                } ?: "N/A"
            }<br>
            <b>Synonyms</b> : ${
                this.synonyms?.let {
                    if (it.isEmpty()) {
                        "N/A"
                    } else {
                        it.joinToString()
                    }
                } ?: "N/A"
            }
        """.trimIndent()
        )
    }
}

internal fun MangaByIDResponse.AlternativeTitles.getFormattedString(): Spanned {
    return if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.N) {
        Html.fromHtml(
            """
            <b>English title</b> : ${
                this.en?.let {
                    if (it.isBlank()) {
                        "N/A"
                    } else {
                        it
                    }
                } ?: "N/A"
            }<br>
            <b>Japanese title</b> : ${
                this.ja?.let {
                    if (it.isBlank()) {
                        "N/A"
                    } else {
                        it
                    }
                } ?: "N/A"
            }<br>
            <b>Synonyms</b> : ${
                this.synonyms?.let {
                    if (it.isEmpty()) {
                        "N/A"
                    } else {
                        it.joinToString()
                    }
                } ?: "N/A"
            }
        """.trimIndent(), Html.FROM_HTML_MODE_COMPACT
        )
    } else {
        Html.fromHtml(
            """
            <b>English title</b> : ${
                this.en?.let {
                    if (it.isBlank()) {
                        "N/A"
                    } else {
                        it
                    }
                } ?: "N/A"
            }<br>
            <b>Japanese title</b> : ${
                this.ja?.let {
                    if (it.isBlank()) {
                        "N/A"
                    } else {
                        it
                    }
                } ?: "N/A"
            }<br>
            <b>Synonyms</b> : ${
                this.synonyms?.let {
                    if (it.isEmpty()) {
                        "N/A"
                    } else {
                        it.joinToString()
                    }
                } ?: "N/A"
            }
        """.trimIndent()
        )
    }
}

internal fun AnimeByIDResponse.Statistics.getStats(): Spanned {
    return if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.N) {
        Html.fromHtml(
            """
            <b>Number of users with this anime in list</b> : ${
                this.numListUsers
            }<br>
            <b>Watching count</b> : ${
                this.status.watching
            }<br>
            <b>Planned count</b> : ${
                this.status.planToWatch
            }<br>
            <b>Completed count</b> : ${
                this.status.completed
            }<br>
            <b>Dropped count</b> : ${
                this.status.dropped
            }<br>
            <b>On hold count</b> : ${
                this.status.onHold
            }
        """.trimIndent(), Html.FROM_HTML_MODE_COMPACT
        )
    } else {
        Html.fromHtml(
            """
            <b>Number of users with this anime in list</b> : ${
                this.numListUsers
            }<br>
            <b>Watching count</b> : ${
                this.status.watching
            }<br>
            <b>Planned count</b> : ${
                this.status.planToWatch
            }<br>
            <b>Completed count</b> : ${
                this.status.completed
            }<br>
            <b>Dropped count</b> : ${
                this.status.dropped
            }<br>
            <b>On hold count</b> : ${
                this.status.onHold
            }
        """.trimIndent()
        )
    }
}

internal fun Int.getEpisodeLengthFromSeconds(): String {
    try {
        if (this <= 0) {
            return "N/A"
        }
        val duration = Duration.ofSeconds(this.toLong())
        val hours = duration.seconds / (60 * 60)
        val minutes = (duration.seconds % (60 * 60) / 60).toInt()
        return if (hours <= 0) "${minutes}m" else "${hours.toInt()}h ${minutes}m"
    } catch (e: Exception) {
        e.printStackTrace()
        return "N/A"
    }
}