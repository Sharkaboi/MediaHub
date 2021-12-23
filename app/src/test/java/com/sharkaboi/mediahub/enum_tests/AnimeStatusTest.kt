package com.sharkaboi.mediahub.enum_tests

import com.sharkaboi.mediahub.data.api.enums.AnimeStatus
import org.junit.Assert.*
import org.junit.Test

class AnimeStatusTest {

    @Test
    fun `Calling animeStatusFromString on valid string returns expected Anime status`() {
        val testString = "plan_to_watch"
        val expectedAnimeStatus = AnimeStatus.plan_to_watch
        val result = AnimeStatus.parse(testString)
        assertTrue(expectedAnimeStatus == result)
    }

    @Test
    fun `Calling animeStatusFromString on invalid string returns null`() {
        val testString = "invalid string"
        val result = AnimeStatus.parse(testString)
        assertNull(result)
    }

    @Test
    fun `list of malStatues must not contain all entry`() {
        assertFalse(AnimeStatus.malStatuses.contains(AnimeStatus.all))
    }
}
