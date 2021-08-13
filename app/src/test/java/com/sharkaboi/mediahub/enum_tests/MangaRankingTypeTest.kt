package com.sharkaboi.mediahub.enum_tests

import com.sharkaboi.mediahub.data.api.enums.MangaRankingType
import org.junit.Assert
import org.junit.Test

class MangaRankingTypeTest {

    @Test
    fun `getMangaRankingFromString on null returns all`() {
        val input = null
        val expected = MangaRankingType.all
        val result = MangaRankingType.getMangaRankingFromString(input)
        Assert.assertEquals(expected, result)
    }

    @Test
    fun `getMangaRankingFromString on light novel slug returns light novel`() {
        val input = "light_novel"
        val expected = MangaRankingType.lightnovels
        val result = MangaRankingType.getMangaRankingFromString(input)
        Assert.assertEquals(expected, result)
    }

    @Test
    fun `getMangaRankingFromString on doujin slug returns doujinshi`() {
        val input = "doujinshi"
        val expected = MangaRankingType.doujin
        val result = MangaRankingType.getMangaRankingFromString(input)
        Assert.assertEquals(expected, result)
    }

    @Test
    fun `getMangaRankingFromString on one shot slug returns one shot`() {
        val input = "one_shot"
        val expected = MangaRankingType.oneshots
        val result = MangaRankingType.getMangaRankingFromString(input)
        Assert.assertEquals(expected, result)
    }

    @Test
    fun `getMangaRankingFromString on novels slug returns novels`() {
        val input = "novels"
        val expected = MangaRankingType.novels
        val result = MangaRankingType.getMangaRankingFromString(input)
        Assert.assertEquals(expected, result)
    }

    @Test
    fun `getMangaRankingFromString on invalid slug returns all`() {
        val input = "invalid_ranking"
        val expected = MangaRankingType.all
        val result = MangaRankingType.getMangaRankingFromString(input)
        Assert.assertEquals(expected, result)
    }
}
