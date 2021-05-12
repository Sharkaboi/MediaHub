package com.sharkaboi.mediahub.common.extensions

import android.content.Context
import android.graphics.Color
import android.text.Html
import android.text.Spanned
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.google.android.material.snackbar.Snackbar
import com.sharkaboi.mediahub.common.data.api.models.anime.AnimeByIDResponse
import java.text.DecimalFormat
import java.time.*
import java.time.format.DateTimeFormatter
import java.time.temporal.WeekFields
import java.util.*

internal fun AppCompatActivity.showToast(message: String, length: Int = Toast.LENGTH_SHORT) =
    Toast.makeText(this, message, length).show()

internal fun Fragment.showToast(message: String, length: Int = Toast.LENGTH_SHORT) =
    Toast.makeText(context, message, length).show()

internal fun Context.showToast(message: String, length: Int = Toast.LENGTH_SHORT) =
    Toast.makeText(this, message, length).show()

internal fun View.shortSnackBar(
    message: String, action: (Snackbar.() -> Unit)? = null
) {
    val snackbar = Snackbar.make(this, message, Snackbar.LENGTH_SHORT)
    action?.let { snackbar.it() }
    snackbar.show()
}

internal fun View.longSnackBar(
    message: String, action: (Snackbar.() -> Unit)? = null
) {
    val snackbar = Snackbar.make(this, message, Snackbar.LENGTH_LONG)
    action?.let { snackbar.it() }
    snackbar.show()
}

internal fun View.indefiniteSnackBar(
    message: String,
    action: (Snackbar.() -> Unit)? = null
) {
    val snackbar = Snackbar.make(this, message, Snackbar.LENGTH_INDEFINITE)
    action?.let { snackbar.it() }
    snackbar.show()
}

internal fun Snackbar.action(message: String, action: (View) -> Unit) {
    this.setAction(message, action)
}

internal fun String.tryParseDateTime(): LocalDateTime? {
    return try {
        val format = DateTimeFormatter.ISO_DATE_TIME
        LocalDateTime.parse(this, format)
    } catch (e: Exception) {
        e.printStackTrace()
        null
    }
}

internal fun String.tryParseDate(): LocalDate? {
    return try {
        val format = DateTimeFormatter.ISO_DATE
        LocalDate.parse(this, format)
    } catch (e: Exception) {
        e.printStackTrace()
        null
    }
}

internal fun LocalDateTime.formatDate(): String {
    val format = DateTimeFormatter.ofPattern("d MMM yyyy")
    return this.format(format)
}

internal fun LocalDate.formatDate(): String {
    val format = DateTimeFormatter.ofPattern("d MMM yyyy")
    return this.format(format)
}

internal fun Double.roundOfString(): String {
    val format = DecimalFormat("#.##")
    return format.format(this)
}

fun String.parseRGB(): Int {
    val color = this.replace("#", "").toLong(16).toInt()
    val r = color shr 16 and 0xFF
    val g = color shr 8 and 0xFF
    val b = color shr 0 and 0xFF
    return Color.rgb(r, g, b)
}

fun String.getNsfwRating(): String {
    return when {
        this.trim() == "white" -> {
            "This anime is safe for work (SFW)."
        }
        this.trim() == "gray" -> {
            "This anime may not be safe for work."
        }
        this.trim() == "black" -> {
            "This anime is not safe for work (NSFW)."
        }
        else -> {
            "N/A"
        }
    }
}

fun String.getRating(): String {
    return when {
        this.trim() == "g" -> {
            "G - All Ages."
        }
        this.trim() == "pg" -> {
            "PG - Children."
        }
        this.trim() == "pg_13" -> {
            "PG 13 - Teens 13 and Older."
        }
        this.trim() == "r" -> {
            "R - 17+ (violence & profanity)."
        }
        this.trim() == "r+" -> {
            "R+ - Profanity & Mild Nudity."
        }
        this.trim() == "rx" -> {
            "Rx - Hentai."
        }
        else -> {
            "N/A"
        }
    }
}

fun String.getStatus(): String {
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

fun AnimeByIDResponse.Broadcast.getBroadcastTime(): String {
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

fun Int.getLengthFromSeconds(): String {
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

fun AnimeByIDResponse.AlternativeTitles.getFormattedString(): Spanned {
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

fun AnimeByIDResponse.Statistics.getStats(): Spanned {
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