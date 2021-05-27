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
    fall

}

internal fun AnimeSeason.next(): AnimeSeason {
    return when (this) {
        AnimeSeason.winter -> AnimeSeason.spring
        AnimeSeason.spring -> AnimeSeason.summer
        AnimeSeason.summer -> AnimeSeason.fall
        AnimeSeason.fall -> AnimeSeason.winter
    }
}

internal fun AnimeSeason.previous(): AnimeSeason {
    return when (this) {
        AnimeSeason.winter -> AnimeSeason.fall
        AnimeSeason.spring -> AnimeSeason.winter
        AnimeSeason.summer -> AnimeSeason.spring
        AnimeSeason.fall -> AnimeSeason.summer
    }
}

internal fun LocalDate.getAnimeSeason(): AnimeSeason {
    return when (this.month) {
        in listOf(Month.JANUARY, Month.FEBRUARY, Month.MARCH) -> AnimeSeason.winter
        in listOf(Month.APRIL, Month.MAY, Month.JUNE) -> AnimeSeason.spring
        in listOf(Month.JULY, Month.AUGUST, Month.SEPTEMBER) -> AnimeSeason.summer
        else -> AnimeSeason.fall
    }
}