package com.sharkaboi.mediahub

import com.sharkaboi.mediahub.data.api.constants.ApiConstants
import org.junit.Assert
import org.junit.Test

class AppAcceptedDeepLinkRegexTest {

    companion object {
        val validDeepLinks = listOf(
            "https://myanimelist.net/anime/41587/Boku_no_Hero_Academia_5th_Season",
            "https://myanimelist.net/manga/13492/Ao_no_Exorcist"
        )
        val invalidDeepLinks = listOf(
            "https://myanimelist.net/anime/41587/Boku_no_Hero_Academia_5th_Season/",
            "https://myanimelist.net/anime/41587/Boku_no_Hero_Academia_5th_Season/#",
            "https://myanimelist.net/anime/41587/Boku_no_Hero_Academia_5th_Season/stats",
            "https://myanimelist.net/anime/41587/Boku_no_Hero_Academia_5th_Season/charcters",
            "https://myanimelist.net/manga/13492/Ao_no_Exorcist/stats",
            "https://myanimelist.net/manga/13492/Ao_no_Exorcist/characters",
            "https://myanimelist.net/manga/13492/Ao_no_Exorcist/",
            "https://myanimelist.net/manga/13492/Ao_no_Exorcist/#",
            "https://myanimelist.net/manga/genre/1/Action",
            "https://myanimelist.net/anime/genre/1/Action",
            "https://myanimelist.net/blog/dummy",
            "https://myanimelist.net/profile/Cyber_Shark/clubs",
            "https://myanimelist.net/forum/search?u=dummy&q=&uloc=1&loc=-1",
            "https://myanimelist.net/history/Cyber_Shark"
        )
    }

    @Test
    fun `Regex on valid deep links return true match`() {
        validDeepLinks.forEach { deepLink ->
            Assert.assertTrue(ApiConstants.appAcceptedDeepLinkRegex.matches(deepLink))
        }
    }

    @Test
    fun `Regex on invalid deep links return true match`() {
        invalidDeepLinks.forEach { deepLink ->
            Assert.assertFalse(ApiConstants.appAcceptedDeepLinkRegex.matches(deepLink))
        }
    }
}
