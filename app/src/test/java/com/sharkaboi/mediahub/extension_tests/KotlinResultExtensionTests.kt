package com.sharkaboi.mediahub.extension_tests

import com.sharkaboi.mediahub.common.extensions.getOrNullWithStackTrace
import org.junit.Assert.assertNotNull
import org.junit.Assert.assertNull
import org.junit.Test

class KotlinResultExtensionTests {

    @Test
    fun `getOrNullWithStackTrace on valid block returns expected output`() {
        val result: Int? = runCatching {
            Int.MAX_VALUE
        }.getOrNullWithStackTrace()
        assertNotNull(result)
    }

    @Test
    fun `getOrNullWithStackTrace on invalid block returns null`() {
        val result: Int? = runCatching<Int> {
            throw Exception()
        }.getOrNullWithStackTrace()
        assertNull(result)
    }
}