package com.sharkaboi.mediahub

import com.sharkaboi.mediahub.common.util.getLocalDateFromDayAndTime
import org.junit.Assert.assertEquals
import org.junit.Test
import java.time.DayOfWeek
import java.time.ZonedDateTime
import java.time.temporal.WeekFields
import java.util.*

class UtilsTest {

    @Test
    fun `getLocalDateFromDayAndTime get expected local date when valid japan time passed`() {
        TimeZone.setDefault(TimeZone.getTimeZone("UTC"))
        val inputStartTime = "17:30"
        val inputDayOfWeek = "saturday"
        val result = getLocalDateFromDayAndTime(inputDayOfWeek, inputStartTime)
        val expectedResult = ZonedDateTime.now().withHour(8).withMinute(30)
            .with(WeekFields.ISO.dayOfWeek(), DayOfWeek.SATURDAY.value.toLong())
            .withSecond(0).withNano(0)
        assertEquals(result, expectedResult)
    }
}
