package com.sharkaboi.mediahub.common.data.api.enums

import java.time.LocalDate
import java.time.Month

@Suppress("EnumEntryName")
enum class AnimeSeason {
    // January, February, March
    winter,

    // April, May, June
    spring,

    // July, August, September
    summer,

    // October, November, December
    fall,
}

internal fun LocalDate.getAnimeSeason(): AnimeSeason {
    return when (this.month) {
        in listOf(Month.JANUARY, Month.FEBRUARY, Month.MARCH) -> AnimeSeason.winter
        in listOf(Month.APRIL, Month.MAY, Month.JUNE) -> AnimeSeason.spring
        in listOf(Month.JULY, Month.AUGUST, Month.SEPTEMBER) -> AnimeSeason.summer
        else -> AnimeSeason.fall
    }
}