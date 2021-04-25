package com.sharkaboi.mediahub.common.extensions

import android.content.Context
import android.graphics.Color
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.google.android.material.snackbar.Snackbar
import com.sharkaboi.mediahub.common.data.api.models.anime.AnimeByIDResponse
import java.text.DecimalFormat
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.ZonedDateTime
import java.time.format.DateTimeFormatter

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
    if (this.startTime == null) {
        return "On ${this.dayOfTheWeek}"
    }
    val string = "${this.dayOfTheWeek} ${this.startTime} +01:00"
    val format = DateTimeFormatter.ofPattern("E H:mm X")
    val japanTime = ZonedDateTime.parse(string, format)
    return ""
}