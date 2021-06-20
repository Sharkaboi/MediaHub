package com.sharkaboi.mediahub.common.extensions

import android.graphics.Color
import com.sharkaboi.mediahub.data.api.enums.AnimeSeason
import com.sharkaboi.mediahub.data.api.enums.getAnimeSeason
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.util.*

internal inline var String.Companion.emptyString: String
    get() = ""
    private set(_) {}

internal fun String.tryParseDateTime(): LocalDateTime? {
    return try {
        val format = DateTimeFormatter.ISO_DATE_TIME
        LocalDateTime.parse(this, format)
    } catch (e: Exception) {
        e.printStackTrace()
        null
    }
}

internal fun String.replaceWhiteSpaceWithUnderScore(): String {
    return this.replace(' ', '_')
}

internal fun String?.getAnimeSeason(): AnimeSeason {
    return this?.let {
        AnimeSeason.valueOf(it.split('_').first().lowercase())
    } ?: LocalDate.now().getAnimeSeason()
}

internal fun String?.getAnimeSeasonYear(): Int {
    return this?.split('_')?.last()?.toInt() ?: LocalDate.now().year
}

internal fun String.capitalizeFirst(): String {
    return this.replaceFirstChar {
        if (it.isLowerCase()) it.titlecase(
            Locale.getDefault()
        ) else it.toString()
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

internal fun String.parseRGB(): Int {
    val color = this.replace("#", "").toLong(16).toInt()
    val r = color shr 16 and 0xFF
    val g = color shr 8 and 0xFF
    val b = color shr 0 and 0xFF
    return Color.rgb(r, g, b)
}