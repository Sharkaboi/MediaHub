package com.sharkaboi.mediahub.extension_tests

import com.sharkaboi.mediahub.common.extensions.formatDateDMY
import org.junit.Assert.assertTrue
import org.junit.Test
import java.time.LocalDate
import java.time.LocalDateTime

class DateExtensionsTest {

    @Test
    fun `formatDateDMY with local date time returns correct formatted string`() {
        val testDateTime = LocalDateTime.of(2021, 6, 27, 0, 0)
        val expectedDateString = "27 Jun 2021"
        val formattedDateString: String = testDateTime.formatDateDMY()
        assertTrue(expectedDateString == formattedDateString)
    }

    @Test
    fun `formatDateDMY with local date returns correct formatted string`() {
        val testDate = LocalDate.of(2021, 6, 27)
        val expectedDateString = "27 Jun 2021"
        val formattedDateString: String = testDate.formatDateDMY()
        assertTrue(expectedDateString == formattedDateString)
    }
}
