package com.sharkaboi.mediahub.common.extensions

import android.graphics.Color
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

internal inline var String.Companion.emptyString: String
    get() = ""
    private set(_) {}

internal fun String?.ifNullOrBlank(block: () -> String): String {
    if (this == null || this.isBlank()) {
        return block()
    }
    return this
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

internal fun String.replaceWhiteSpaceWithUnderScore(): String {
    return this.replace(' ', '_')
}

internal fun String.replaceUnderScoreWithWhiteSpace(): String {
    return this.replace('_', ' ')
}

internal fun String.capitalizeFirst(): String {
    return this.lowercase().replaceFirstChar {
        it.uppercase()
    }
}

internal fun String.tryParseDate(): LocalDate? {
    return runCatching {
        val format = DateTimeFormatter.ISO_DATE
        LocalDate.parse(this, format)
    }.getOrNullWithStackTrace()
}

internal fun String.parseRGB(): Int {
    val color = this.replace("#", "").toLong(16).toInt()
    val r = color shr 16 and 0xFF
    val g = color shr 8 and 0xFF
    val b = color shr 0 and 0xFF
    return Color.rgb(r, g, b)
}
