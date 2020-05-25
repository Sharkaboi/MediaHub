package com.cybershark.mediahub

import com.cybershark.mediahub.util.InternetConnectionManager
import junit.framework.TestCase.assertEquals
import org.junit.Test

class UtilClassesTests {

    @Test
    fun testInternetConnectManagerIsInternetActive(){
        assertEquals(true, InternetConnectionManager.isInternetActive())
    }
}