package com.sharkaboi.mediahub.data.api.enums

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
    fall;

    fun next(): Pair<AnimeSeason, Boolean> {
        return when (this) {
            winter -> Pair(spring, false)
            spring -> Pair(summer, false)
            summer -> Pair(fall, false)
            fall -> Pair(winter, true)
        }
    }

    fun previous(): Pair<AnimeSeason, Boolean> {
        return when (this) {
            winter -> Pair(fall, true)
            spring -> Pair(winter, false)
            summer -> Pair(spring, false)
            fall -> Pair(summer, false)
        }
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