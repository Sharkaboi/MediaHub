package com.sharkaboi.mediahub.enum_tests

import com.sharkaboi.mediahub.data.api.enums.MangaStatus
import com.sharkaboi.mediahub.data.api.enums.mangaStatusFromString
import org.junit.Assert.assertFalse
import org.junit.Assert.assertNull
import org.junit.Assert.assertTrue
import org.junit.Test

class MangaStatusTest {

    @Test
    fun `Calling mangaStatusFromString on valid string returns expected Manga status`() {
        val testString = "plan_to_read"
        val expectedAnimeStatus = MangaStatus.plan_to_read
        val result = testString.mangaStatusFromString()
        assertTrue(expectedAnimeStatus == result)
    }

    @Test
    fun `Calling mangaStatusFromString on invalid string returns null`() {
        val testString = "invalid string"
        val result = testString.mangaStatusFromString()
        assertNull(result)
    }

    @Test
    fun `list of malStatues must not contain all entry`() {
        assertFalse(MangaStatus.malStatuses.contains(MangaStatus.all))
    }
}
