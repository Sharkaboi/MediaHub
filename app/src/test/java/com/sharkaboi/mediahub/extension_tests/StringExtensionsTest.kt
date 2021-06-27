package com.sharkaboi.mediahub.extension_tests

import com.sharkaboi.mediahub.common.extensions.*
import com.sharkaboi.mediahub.data.api.enums.AnimeSeason
import org.junit.Assert.assertNull
import org.junit.Assert.assertTrue
import org.junit.Test
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

class StringExtensionsTest {

    @Test
    fun `String's emptyString property returns empty string`() {
        assertTrue(String.emptyString == "")
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
        assertTrue(expectedString == resultString)
    }

    @Test
    fun `replaceWhiteSpaceWithUnderScore replaces nothing in string with no whitespace`() {
        val testString = "\n\t"
        val expectedString = "\n\t"
        val resultString = testString.replaceWhiteSpaceWithUnderScore()
        assertTrue(expectedString == resultString)
    }

    @Test
    fun `getAnimeSeason with valid season string returns correct season`() {
        val testSeason = "spring_2021"
        val parsedSeason = testSeason.getAnimeSeason()
        assertTrue(parsedSeason == AnimeSeason.spring)
    }

    @Test
    fun `getAnimeSeasonYear with valid season string returns correct year`() {
        val testSeason = "spring_2021"
        val parsedYear = testSeason.getAnimeSeasonYear()
        assertTrue(parsedYear == 2021)
    }

    @Test
    fun `capitalizeFirst with lowercase string returns with first char uppercased`() {
        val testString = "sprinG"
        val expectedString = "Spring"
        val resultString = testString.capitalizeFirst()
        assertTrue(expectedString == resultString)
    }
}