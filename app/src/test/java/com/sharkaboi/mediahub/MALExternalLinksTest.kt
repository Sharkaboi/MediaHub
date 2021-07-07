package com.sharkaboi.mediahub

import com.sharkaboi.mediahub.common.constants.MALExternalLinks
import com.sharkaboi.mediahub.data.api.models.anime.AnimeByIDResponse
import com.sharkaboi.mediahub.data.api.models.manga.MangaByIDResponse
import org.junit.Assert.assertTrue
import org.junit.Test

class MALExternalLinksTest {

    @Test
    fun `getAnimeGenresLink with valid genre returns expected string`() {
        val genre = AnimeByIDResponse.Genre(
            id = 1,
            name = "Action"
        )
        val expectedString = "https://myanimelist.net/anime/genre/1/Action"
        val resultString = MALExternalLinks.getAnimeGenresLink(genre)
        assertTrue(resultString == expectedString)
    }

    @Test
    fun `getMangaGenresLink with valid genre returns expected string`() {
        val genre = MangaByIDResponse.Genre(
            id = 1,
            name = "Action"
        )
        val expectedString = "https://myanimelist.net/manga/genre/1/Action"
        val resultString = MALExternalLinks.getMangaGenresLink(genre)
        assertTrue(resultString == expectedString)
    }

    @Test
    fun `getAnimeProducerPageLink with valid studio returns expected string`() {
        val studio = AnimeByIDResponse.Studio(
            id = 1,
            name = "Bones"
        )
        val expectedString = "https://myanimelist.net/anime/producer/1/Bones"
        val resultString = MALExternalLinks.getAnimeProducerPageLink(studio)
        assertTrue(resultString == expectedString)
    }

    @Test
    fun `getMangaAuthorPageLink with valid author returns expected string`() {
        val studio = MangaByIDResponse.Author(
            node = MangaByIDResponse.Author.Node(
                id = 1,
                firstName = "Mikio",
                lastName = "Ikemoto"
            ),
            role = "Writer"
        )
        val expectedString = "https://myanimelist.net/people/1/Mikio_Ikemoto"
        val resultString = MALExternalLinks.getMangaAuthorPageLink(studio)
        assertTrue(resultString == expectedString)
    }

    @Test
    fun `getMangaSerializationPageLink with valid serialization returns expected string`() {
        val studio = MangaByIDResponse.Serialization(
            node = MangaByIDResponse.Serialization.Node(
                id = 1,
                name = "Shounen Jump"
            )
        )
        val expectedString = "https://myanimelist.net/manga/magazine/1/Shounen_Jump"
        val resultString = MALExternalLinks.getMangaSerializationPageLink(studio)
        assertTrue(resultString == expectedString)
    }

    @Test
    fun `getAnimeCharactersLink with valid id and title returns expected string`() {
        val id = 1
        val title = "My title"
        val expectedString = "https://myanimelist.net/anime/1/My_title/characters"
        val resultString = MALExternalLinks.getAnimeCharactersLink(id, title)
        assertTrue(resultString == expectedString)
    }

    @Test
    fun `getAnimeStaffLink with valid id and title returns expected string`() {
        val id = 1
        val title = "My title"
        val expectedString = "https://myanimelist.net/anime/1/My_title/characters#staff"
        val resultString = MALExternalLinks.getAnimeStaffLink(id, title)
        assertTrue(resultString == expectedString)
    }

    @Test
    fun `getAnimeReviewsLink with valid id and title returns expected string`() {
        val id = 1
        val title = "My title"
        val expectedString = "https://myanimelist.net/anime/1/My_title/reviews"
        val resultString = MALExternalLinks.getAnimeReviewsLink(id, title)
        assertTrue(resultString == expectedString)
    }

    @Test
    fun `getAnimeNewsLink with valid id and title returns expected string`() {
        val id = 1
        val title = "My title"
        val expectedString = "https://myanimelist.net/anime/1/My_title/news"
        val resultString = MALExternalLinks.getAnimeNewsLink(id, title)
        assertTrue(resultString == expectedString)
    }

    @Test
    fun `getAnimeVideosLink with valid id and title returns expected string`() {
        val id = 1
        val title = "My title"
        val expectedString = "https://myanimelist.net/anime/1/My_title/video"
        val resultString = MALExternalLinks.getAnimeVideosLink(id, title)
        assertTrue(resultString == expectedString)
    }

    @Test
    fun `getMangaCharactersLink with valid id and title returns expected string`() {
        val id = 1
        val title = "My title"
        val expectedString = "https://myanimelist.net/manga/1/My_title/characters"
        val resultString = MALExternalLinks.getMangaCharactersLink(id, title)
        assertTrue(resultString == expectedString)
    }

    @Test
    fun `getMangaReviewsLink with valid id and title returns expected string`() {
        val id = 1
        val title = "My title"
        val expectedString = "https://myanimelist.net/manga/1/My_title/reviews"
        val resultString = MALExternalLinks.getMangaReviewsLink(id, title)
        assertTrue(resultString == expectedString)
    }

    @Test
    fun `getMangaNewsLink with valid id and title returns expected string`() {
        val id = 1
        val title = "My title"
        val expectedString = "https://myanimelist.net/manga/1/My_title/news"
        val resultString = MALExternalLinks.getMangaNewsLink(id, title)
        assertTrue(resultString == expectedString)
    }

    @Test
    fun `getBlogsLink returns expected string`() {
        val testName = "Cyber_Shark"
        val expectedString = "https://myanimelist.net/blog/Cyber_Shark"
        val resultString = MALExternalLinks.getBlogsLink(testName)
        assertTrue(resultString == expectedString)
    }

    @Test
    fun `getClubsLink returns expected string`() {
        val testName = "Cyber_Shark"
        val expectedString = "https://myanimelist.net/profile/Cyber_Shark/clubs"
        val resultString = MALExternalLinks.getClubsLink(testName)
        assertTrue(resultString == expectedString)
    }

    @Test
    fun `getForumTopicsLink returns expected string`() {
        val testName = "Cyber_Shark"
        val expectedString = "https://myanimelist.net/forum/search?u=Cyber_Shark&q=&uloc=1&loc=-1"
        val resultString = MALExternalLinks.getForumTopicsLink(testName)
        assertTrue(resultString == expectedString)
    }

    @Test
    fun `getFriendsLink returns expected string`() {
        val testName = "Cyber_Shark"
        val expectedString = "https://myanimelist.net/profile/Cyber_Shark/friends"
        val resultString = MALExternalLinks.getFriendsLink(testName)
        assertTrue(resultString == expectedString)
    }

    @Test
    fun `getHistoryLink returns expected string`() {
        val testName = "Cyber_Shark"
        val expectedString = "https://myanimelist.net/history/Cyber_Shark"
        val resultString = MALExternalLinks.getHistoryLink(testName)
        assertTrue(resultString == expectedString)
    }

    @Test
    fun `getRecommendationsLink returns expected string`() {
        val testName = "Cyber_Shark"
        val expectedString = "https://myanimelist.net/profile/Cyber_Shark/recommendations"
        val resultString = MALExternalLinks.getRecommendationsLink(testName)
        assertTrue(resultString == expectedString)
    }

    @Test
    fun `getReviewsLink returns expected string`() {
        val testName = "Cyber_Shark"
        val expectedString = "https://myanimelist.net/profile/Cyber_Shark/reviews"
        val resultString = MALExternalLinks.getReviewsLink(testName)
        assertTrue(resultString == expectedString)
    }
}
