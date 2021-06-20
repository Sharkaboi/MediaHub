package com.sharkaboi.mediahub.common.constants

import com.sharkaboi.mediahub.data.api.models.anime.AnimeByIDResponse
import com.sharkaboi.mediahub.common.extensions.replaceWhiteSpaceWithUnderScore

class MALExternalLinks {
    companion object {
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
    }
}