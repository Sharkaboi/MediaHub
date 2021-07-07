package com.sharkaboi.mediahub.enum_tests

import com.sharkaboi.mediahub.data.api.enums.UserAnimeSortType
import com.sharkaboi.mediahub.data.api.enums.UserMangaSortType
import org.junit.Assert.assertEquals
import org.junit.Test

class SortTypeEnumTest {

    @Test
    fun `User Anime sort type formattedString must contain same elements as enum values`() {
        assertEquals(
            UserAnimeSortType.values().size,
            UserAnimeSortType.getFormattedArray().size
        )
    }

    @Test
    fun `User Manga sort type formattedString must contain same elements as enum values`() {
        assertEquals(
            UserMangaSortType.values().size,
            UserMangaSortType.getFormattedArray().size
        )
    }
}
