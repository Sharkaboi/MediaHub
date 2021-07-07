package com.sharkaboi.mediahub.argument_tests

import com.sharkaboi.mediahub.data.api.enums.AnimeSeason
import com.sharkaboi.mediahub.modules.anime_seasonal.util.AnimeSeasonWrapper
import org.junit.Assert
import org.junit.Test

class AnimeSeasonWrapperTest {

    @Test
    fun `Calling next on winter season returns expected output`() {
        val testSeason = AnimeSeasonWrapper(
            animeSeason = AnimeSeason.winter,
            year = 2020
        )
        val expectedOutput = AnimeSeasonWrapper(
            animeSeason = AnimeSeason.spring,
            year = 2020
        )
        val result: AnimeSeasonWrapper = testSeason.next()
        Assert.assertTrue(result == expectedOutput)
    }

    @Test
    fun `Calling next on fall season returns expected output`() {
        val testSeason = AnimeSeasonWrapper(
            animeSeason = AnimeSeason.fall,
            year = 2020
        )
        val expectedOutput = AnimeSeasonWrapper(
            animeSeason = AnimeSeason.winter,
            year = 2021
        )
        val result: AnimeSeasonWrapper = testSeason.next()
        Assert.assertTrue(result == expectedOutput)
    }

    @Test
    fun `Calling previous on winter season returns expected output`() {
        val testSeason = AnimeSeasonWrapper(
            animeSeason = AnimeSeason.winter,
            year = 2021
        )
        val expectedOutput = AnimeSeasonWrapper(
            animeSeason = AnimeSeason.fall,
            year = 2020
        )
        val result: AnimeSeasonWrapper = testSeason.prev()
        Assert.assertTrue(result == expectedOutput)
    }

    @Test
    fun `Calling previous on fall season returns expected output`() {
        val testSeason = AnimeSeasonWrapper(
            animeSeason = AnimeSeason.fall,
            year = 2021
        )
        val expectedOutput = AnimeSeasonWrapper(
            animeSeason = AnimeSeason.summer,
            year = 2021
        )
        val result: AnimeSeasonWrapper = testSeason.prev()
        Assert.assertTrue(result == expectedOutput)
    }
}
