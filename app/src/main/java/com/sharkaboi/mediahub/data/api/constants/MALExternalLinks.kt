package com.sharkaboi.mediahub.data.api.constants

import com.sharkaboi.mediahub.common.extensions.replaceWhiteSpaceWithUnderScore
import com.sharkaboi.mediahub.data.api.models.anime.AnimeByIDResponse
import com.sharkaboi.mediahub.data.api.models.manga.MangaByIDResponse

object MALExternalLinks {
    fun getAnimeGenresLink(genre: AnimeByIDResponse.Genre): String {
        return "https://myanimelist.net/anime/genre" +
            "/${genre.id}/${genre.name.replaceWhiteSpaceWithUnderScore()}"
    }

    fun getAnimeCharactersLink(id: Int, title: String): String {
        return "https://myanimelist.net/anime" +
            "/$id/${title.replaceWhiteSpaceWithUnderScore()}/characters"
    }

    fun getAnimeStaffLink(id: Int, title: String): String {
        return "https://myanimelist.net/anime" +
            "/$id/${title.replaceWhiteSpaceWithUnderScore()}/characters#staff"
    }

    fun getAnimeReviewsLink(id: Int, title: String): String {
        return "https://myanimelist.net/anime" +
            "/$id/${title.replaceWhiteSpaceWithUnderScore()}/reviews"
    }

    fun getAnimeNewsLink(id: Int, title: String): String {
        return "https://myanimelist.net/anime" +
            "/$id/${title.replaceWhiteSpaceWithUnderScore()}/news"
    }

    fun getAnimeVideosLink(id: Int, title: String): String {
        return "https://myanimelist.net/anime" +
            "/$id/${title.replaceWhiteSpaceWithUnderScore()}/video"
    }

    fun getAnimeProducerPageLink(studio: AnimeByIDResponse.Studio): String {
        return "https://myanimelist.net/anime/producer" +
            "/${studio.id}/${studio.name.replaceWhiteSpaceWithUnderScore()}"
    }

    fun getMangaAuthorPageLink(author: MangaByIDResponse.Author): String {
        val name =
            "${author.node.firstName} ${author.node.lastName}"
                .replaceWhiteSpaceWithUnderScore()
        return "https://myanimelist.net/people" +
            "/${author.node.id}/$name"
    }

    fun getMangaGenresLink(genre: MangaByIDResponse.Genre): String {
        return "https://myanimelist.net/manga/genre" +
            "/${genre.id}/${genre.name.replaceWhiteSpaceWithUnderScore()}"
    }

    fun getMangaCharactersLink(id: Int, title: String): String {
        return "https://myanimelist.net/manga" +
            "/$id/${title.replaceWhiteSpaceWithUnderScore()}/characters"
    }

    fun getMangaReviewsLink(id: Int, title: String): String {
        return "https://myanimelist.net/manga" +
            "/$id/${title.replaceWhiteSpaceWithUnderScore()}/reviews"
    }

    fun getMangaNewsLink(id: Int, title: String): String {
        return "https://myanimelist.net/manga" +
            "/$id/${title.replaceWhiteSpaceWithUnderScore()}/news"
    }

    fun getMangaSerializationPageLink(magazine: MangaByIDResponse.Serialization): String {
        return "https://myanimelist.net/manga/magazine" +
            "/${magazine.node.id}/${magazine.node.name.replaceWhiteSpaceWithUnderScore()}"
    }

    fun getBlogsLink(name: String): String {
        return "https://myanimelist.net/blog/$name"
    }

    fun getClubsLink(name: String): String {
        return "https://myanimelist.net/profile/$name/clubs"
    }

    fun getForumTopicsLink(name: String): String {
        return "https://myanimelist.net/forum/search?u=$name&q=&uloc=1&loc=-1"
    }

    fun getFriendsLink(name: String): String {
        return "https://myanimelist.net/profile/$name/friends"
    }

    fun getHistoryLink(name: String): String {
        return "https://myanimelist.net/history/$name"
    }

    fun getRecommendationsLink(name: String): String {
        return "https://myanimelist.net/profile/$name/recommendations"
    }

    fun getReviewsLink(name: String): String {
        return "https://myanimelist.net/profile/$name/reviews"
    }

    fun getProfileLink(name: String): String {
        return "https://myanimelist.net/profile/$name"
    }

    fun getUserAnimeListLink(name: String): String {
        return "https://myanimelist.net/animelist/$name"
    }

    fun getUserMangaListLink(name: String): String {
        return "https://myanimelist.net/mangalist/$name"
    }
}
