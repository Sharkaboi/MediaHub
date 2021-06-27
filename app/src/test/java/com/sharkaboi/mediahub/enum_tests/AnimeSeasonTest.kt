package com.sharkaboi.mediahub.enum_tests

import com.sharkaboi.mediahub.data.api.enums.AnimeSeason
import com.sharkaboi.mediahub.data.api.enums.getAnimeSeason
import org.junit.Assert.assertNull
import org.junit.Assert.assertTrue
import org.junit.Test
import java.time.LocalDate

class AnimeSeasonTest {

    @Test
    fun `Calling next on winter season returns expected output`() {
        val testSeason = AnimeSeason.winter
        val expectedOutput = Pair(AnimeSeason.spring, false)
        val result: Pair<AnimeSeason, Boolean> = testSeason.next()
        assertTrue(result == expectedOutput)
    }

    @Test
    fun `Calling next on fall season returns expected output`() {
        val testSeason = AnimeSeason.fall
        val expectedOutput = Pair(AnimeSeason.winter, true)
        val result: Pair<AnimeSeason, Boolean> = testSeason.next()
        assertTrue(result == expectedOutput)
    }

    @Test
    fun `Calling previous on winter season returns expected output`() {
        val testSeason = AnimeSeason.winter
        val expectedOutput = Pair(AnimeSeason.fall, true)
        val result: Pair<AnimeSeason, Boolean> = testSeason.previous()
        assertTrue(result == expectedOutput)
    }

    @Test
    fun `Calling previous on fall season returns expected output`() {
        val testSeason = AnimeSeason.fall
        val expectedOutput = Pair(AnimeSeason.summer, false)
        val result: Pair<AnimeSeason, Boolean> = testSeason.previous()
        assertTrue(result == expectedOutput)
    }

    @Test
    fun `getAnimeSeason of local date returns expected output`() {
        val testDate = LocalDate.of(2021, 6, 27)
        val expectedOutput = AnimeSeason.spring
        val result = testDate.getAnimeSeason()
        assertTrue(result == expectedOutput)
    }

    @Test
    fun `getAnimeSeason of null string returns null`() {
        val testString: String? = null
        val result = testString.getAnimeSeason()
        assertNull(result)
    }
}