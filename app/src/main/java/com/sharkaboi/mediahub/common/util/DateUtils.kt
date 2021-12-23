package com.sharkaboi.mediahub.common.util

import java.time.DayOfWeek
import java.time.OffsetDateTime
import java.time.ZoneId
import java.time.ZonedDateTime
import java.time.temporal.WeekFields

object DateUtils {
    fun getLocalDateFromDayAndTime(dayOfTheWeek: String, startTime: String): ZonedDateTime? {
        val dayOfWeek = DayOfWeek.valueOf(dayOfTheWeek.uppercase())
        val fieldIso = WeekFields.ISO.dayOfWeek()
        val now = OffsetDateTime.now().with(fieldIso, dayOfWeek.value.toLong())
        val (hour, mins) = startTime.split(":").map { it.toInt() }
        val japanTime = ZonedDateTime.of(
            now.year,
            now.month.value,
            now.dayOfMonth,
            hour,
            mins,
            0,
            0,
            ZoneId.of("Asia/Tokyo")
        )
        val localTimeZone = ZoneId.systemDefault()
        return japanTime.withZoneSameInstant(localTimeZone)
    }
}

