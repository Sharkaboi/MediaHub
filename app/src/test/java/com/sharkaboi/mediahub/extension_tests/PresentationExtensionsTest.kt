package com.sharkaboi.mediahub.extension_tests

import com.sharkaboi.mediahub.common.extensions.*
import org.junit.Assert.assertEquals
import org.junit.Test
import kotlin.time.ExperimentalTime

@ExperimentalTime
class PresentationExtensionsTest {

    @Test
    fun `Double's roundOfString for double zero returns string 0`() {
        val double = 0.0
        val expectedString = "0"
        val resultString = double.roundOfString()
        assertEquals(resultString, expectedString)
    }

    @Test
    fun `Double's roundOfString for double with three decimal points returns string with only two`() {
        val double = 3.1234
        val expectedString = "3.12"
        val resultString = double.roundOfString()
        assertEquals(resultString, expectedString)
    }

    @Test
    fun `getAnimeNsfwRating for valid rating returns expected string`() {
        val testRating = "black"
        val expectedString = "NSFW"
        val resultString = testRating.getAnimeNsfwRating()
        assertEquals(resultString, expectedString)
    }

    @Test
    fun `getAnimeNsfwRating for invalid rating returns default string`() {
        val testRating = "invalid string"
        val expectedString = "SFW : N/A"
        val resultString = testRating.getAnimeNsfwRating()
        assertEquals(resultString, expectedString)
    }

    @Test
    fun `getMangaNsfwRating for valid rating returns expected string`() {
        val testRating = "black"
        val expectedString = "Not safe for work (NSFW)"
        val resultString = testRating.getMangaNsfwRating()
        assertEquals(resultString, expectedString)
    }

    @Test
    fun `getMangaNsfwRating for invalid rating returns default string`() {
        val testRating = "invalid string"
        val expectedString = "N/A"
        val resultString = testRating.getMangaNsfwRating()
        assertEquals(resultString, expectedString)
    }

    @Test
    fun `getRating for valid rating returns expected string`() {
        val testRating = "r"
        val expectedString = "R - 17+"
        val resultString = testRating.getRating()
        assertEquals(resultString, expectedString)
    }

    @Test
    fun `getRating for invalid rating returns default string`() {
        val testRating = "invalid string"
        val expectedString = "N/A"
        val resultString = testRating.getRating()
        assertEquals(resultString, expectedString)
    }

    @Test
    fun `getAnimeAiringStatus for valid status returns expected string`() {
        val testStatus = "finished_airing"
        val expectedString = "Finished airing"
        val resultString = testStatus.getAnimeAiringStatus()
        assertEquals(resultString, expectedString)
    }

    @Test
    fun `getAnimeAiringStatus for invalid status returns default string`() {
        val testStatus = "invalid string"
        val expectedString = "Airing status : N/A"
        val resultString = testStatus.getAnimeAiringStatus()
        assertEquals(resultString, expectedString)
    }

    @Test
    fun `getMangaPublishStatus for valid status returns expected string`() {
        val testStatus = "finished"
        val expectedString = "Finished publishing"
        val resultString = testStatus.getMangaPublishStatus()
        assertEquals(resultString, expectedString)
    }

    @Test
    fun `getMangaPublishStatus for invalid status returns default string`() {
        val testStatus = "invalid string"
        val expectedString = "Publishing status : N/A"
        val resultString = testStatus.getMangaPublishStatus()
        assertEquals(resultString, expectedString)
    }

    @Test
    fun `getEpisodeLengthFromSeconds for length with hours returns expected string`() {
        val testLength = 60 * 90 // 90 minutes
        val expectedString = "1h 30m per ep"
        val resultString = testLength.getEpisodeLengthFromSeconds()
        assertEquals(resultString, expectedString)
    }

    @Test
    fun `getEpisodeLengthFromSeconds for length with minutes returns expected string`() {
        val testLength = 60 * 5 // 5 minutes
        val expectedString = "5m per ep"
        val resultString = testLength.getEpisodeLengthFromSeconds()
        assertEquals(resultString, expectedString)
    }

    @Test
    fun `getEpisodeLengthFromSeconds for invalid length returns default string`() {
        val testLength = -6 // invalid length
        val expectedString = "N/A per ep"
        val resultString = testLength.getEpisodeLengthFromSeconds()
        assertEquals(resultString, expectedString)
    }

    @Test
    fun `getAiringTimeFormatted for length with days returns expected string`() {
        val testLength = 5 * 24 * 60 * 60 * 1000 // 5 days
        val expectedString = "5d 0h 0m"
        val resultString = testLength.getAiringTimeFormatted()
        assertEquals(resultString, expectedString)
    }

    @Test
    fun `getAiringTimeFormatted for length with hours returns expected string`() {
        val testLength = 60 * 90 * 1000 // 90 minutes
        val expectedString = "1h 30m"
        val resultString = testLength.getAiringTimeFormatted()
        assertEquals(resultString, expectedString)
    }

    @Test
    fun `getAiringTimeFormatted for length with minutes returns expected string`() {
        val testLength = 60 * 5 * 1000 // 5 minutes
        val expectedString = "5m"
        val resultString = testLength.getAiringTimeFormatted()
        assertEquals(resultString, expectedString)
    }

    @Test
    fun `getAiringTimeFormatted for invalid length returns default string`() {
        val testLength = -6 // invalid length
        val expectedString = "0h 0m 0s"
        val resultString = testLength.getAiringTimeFormatted()
        assertEquals(resultString, expectedString)
    }
}
