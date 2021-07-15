package com.sharkaboi.mediahub.extension_tests

import com.sharkaboi.mediahub.common.extensions.*
import org.junit.Assert.*
import org.junit.Test
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

class StringExtensionsTest {

    @Test
    fun `String's emptyString property returns empty string`() {
        assertEquals(String.emptyString, "")
    }

    @Test
    fun `String's ifNullOrBlank returns expected output on null or blank string`() {
        val testString1: String? = null
        val testString2 = "  "
        val testString3 = "test"
        val expectedOutput1 = "output"
        val expectedOutput2 = "output"
        val expectedOutput3 = "test"
        assertEquals(testString1.ifNullOrBlank { expectedOutput1 }, expectedOutput1)
        assertEquals(testString2.ifNullOrBlank { expectedOutput2 }, expectedOutput2)
        assertEquals(testString3.ifNullOrBlank { "" }, expectedOutput3)
    }

    @Test
    fun `Parsing valid date time string returns non null LocalDateTime`() {
        val date = LocalDateTime.of(2021, 6, 27, 0, 0)
        val isoString = date.format(DateTimeFormatter.ISO_DATE_TIME)
        val parsedDate: LocalDateTime? = isoString.tryParseDateTime()
        assertTrue(date.isEqual(parsedDate))
    }

    @Test
    fun `Parsing invalid date time string returns null`() {
        val invalidDateString = "invalid string"
        val parsedDate: LocalDateTime? = invalidDateString.tryParseDateTime()
        assertNull(parsedDate)
    }

    @Test
    fun `Parsing valid date string returns non null LocalDate`() {
        val date = LocalDate.of(2021, 6, 27)
        val isoString = date.format(DateTimeFormatter.ISO_DATE)
        val parsedDate: LocalDate? = isoString.tryParseDate()
        assertTrue(date.isEqual(parsedDate))
    }

    @Test
    fun `Parsing invalid date string returns null`() {
        val invalidDateString = "invalid string"
        val parsedDate: LocalDate? = invalidDateString.tryParseDate()
        assertNull(parsedDate)
    }

    @Test
    fun `replaceWhiteSpaceWithUnderScore replaces whitespace with underscore`() {
        val testString = "a b  c   d ee ffff_g"
        val expectedString = "a_b__c___d_ee_ffff_g"
        val resultString = testString.replaceWhiteSpaceWithUnderScore()
        assertEquals(expectedString, resultString)
    }

    @Test
    fun `replaceUnderScoreWithWhiteSpace replaces underscore with whitespace`() {
        val testString = "a_b__c___d_ee_ffff g"
        val expectedString = "a b  c   d ee ffff g"
        val resultString = testString.replaceUnderScoreWithWhiteSpace()
        assertEquals(expectedString, resultString)
    }

    @Test
    fun `replaceWhiteSpaceWithUnderScore replaces nothing in string with no whitespace`() {
        val testString = "\n\t"
        val expectedString = "\n\t"
        val resultString = testString.replaceWhiteSpaceWithUnderScore()
        assertEquals(expectedString, resultString)
    }

    @Test
    fun `capitalizeFirst with lowercase string returns with first char uppercased`() {
        val testString = "sprinG"
        val expectedString = "Spring"
        val resultString = testString.capitalizeFirst()
        assertEquals(expectedString, resultString)
    }
}
