package com.sharkaboi.mediahub.enum_tests

import com.sharkaboi.mediahub.data.api.enums.MangaStatus
import org.junit.Assert.*
import org.junit.Test

class MangaStatusTest {

    @Test
    fun `Calling mangaStatusFromString on valid string returns expected Manga status`() {
        val testString = "plan_to_read"
        val expectedAnimeStatus = MangaStatus.plan_to_read
        val result = MangaStatus.parse(testString)
        assertTrue(expectedAnimeStatus == result)
    }

    @Test
    fun `Calling mangaStatusFromString on invalid string returns null`() {
        val testString = "invalid string"
        val result = MangaStatus.parse(testString)
        assertNull(result)
    }

    @Test
    fun `list of malStatues must not contain all entry`() {
        assertFalse(MangaStatus.malStatuses.contains(MangaStatus.all))
    }
}
