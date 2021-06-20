package com.sharkaboi.mediahub.data.api.models.manga


import androidx.annotation.Keep
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@Keep
@JsonClass(generateAdapter = true)
data class MangaByIDResponse(
    @Json(name = "alternative_titles")
    val alternativeTitles: AlternativeTitles?,
    @Json(name = "authors")
    val authors: List<Author>,
    @Json(name = "background")
    val background: String?,
    @Json(name = "created_at")
    val createdAt: String,
    @Json(name = "end_date")
    val endDate: String?,
    @Json(name = "genres")
    val genres: List<Genre>,
    @Json(name = "id")
    val id: Int,
    @Json(name = "main_picture")
    val mainPicture: MainPicture?,
    @Json(name = "mean")
    val mean: Double?,
    @Json(name = "media_type")
    val mediaType: String,
    @Json(name = "my_list_status")
    val myListStatus: MyListStatus?,
    @Json(name = "nsfw")
    val nsfw: String?,
    @Json(name = "num_chapters")
    val numChapters: Int,
    @Json(name = "num_list_users")
    val numListUsers: Int,
    @Json(name = "num_scoring_users")
    val numScoringUsers: Int,
    @Json(name = "num_volumes")
    val numVolumes: Int,
    @Json(name = "pictures")
    val pictures: List<Picture>,
    @Json(name = "popularity")
    val popularity: Int?,
    @Json(name = "rank")
    val rank: Int?,
    @Json(name = "recommendations")
    val recommendations: List<Recommendation>,
    @Json(name = "related_anime")
    val relatedAnime: List<RelatedAnime>,
    @Json(name = "related_manga")
    val relatedManga: List<RelatedManga>,
    @Json(name = "serialization")
    val serialization: List<Serialization>,
    @Json(name = "start_date")
    val startDate: String?,
    @Json(name = "status")
    val status: String,
    @Json(name = "synopsis")
    val synopsis: String?,
    @Json(name = "title")
    val title: String,
    @Json(name = "updated_at")
    val updatedAt: String
) {
    @Keep
    @JsonClass(generateAdapter = true)
    data class AlternativeTitles(
        @Json(name = "en")
        val en: String?,
        @Json(name = "ja")
        val ja: String?,
        @Json(name = "synonyms")
        val synonyms: List<String?>?
    )

    @Keep
    @JsonClass(generateAdapter = true)
    data class Author(
        @Json(name = "node")
        val node: Node,
        @Json(name = "role")
        val role: String
    ) {
        @Keep
        @JsonClass(generateAdapter = true)
        data class Node(
            @Json(name = "first_name")
            val firstName: String,
            @Json(name = "id")
            val id: Int,
            @Json(name = "last_name")
            val lastName: String
        )
    }

    @Keep
    @JsonClass(generateAdapter = true)
    data class Genre(
        @Json(name = "id")
        val id: Int,
        @Json(name = "name")
        val name: String
    )

    @Keep
    @JsonClass(generateAdapter = true)
    data class MainPicture(
        @Json(name = "large")
        val large: String?,
        @Json(name = "medium")
        val medium: String
    )

    @Keep
    @JsonClass(generateAdapter = true)
    data class MyListStatus(
        @Json(name = "is_rereading")
        val isRereading: Boolean,
        @Json(name = "num_chapters_read")
        val numChaptersRead: Int,
        @Json(name = "num_volumes_read")
        val numVolumesRead: Int,
        @Json(name = "score")
        val score: Int,
//        @Json(name = "priority")
//        val priority: Int,
//        @Json(name = "reread_value")
//        val reReadValue: Int,
//        @Json(name = "num_times_reread")
//        val numTimesReRead: Int,
        @Json(name = "status")
        val status: String?,
        @Json(name = "updated_at")
        val updatedAt: String,
//        @Json(name = "comments")
//        val comments: String,
//        @Json(name = "tags")
//        val tags: List<String>,
//        @Json(name = "start_date")
//        val startDate: String?,
//        @Json(name = "finish_date")
//        val finishDate: String?
    )

    @Keep
    @JsonClass(generateAdapter = true)
    data class Picture(
        @Json(name = "large")
        val large: String?,
        @Json(name = "medium")
        val medium: String
    )

    @Keep
    @JsonClass(generateAdapter = true)
    data class Recommendation(
        @Json(name = "node")
        val node: Node,
        @Json(name = "num_recommendations")
        val numRecommendations: Int
    ) {
        @Keep
        @JsonClass(generateAdapter = true)
        data class Node(
            @Json(name = "id")
            val id: Int,
            @Json(name = "main_picture")
            val mainPicture: MainPicture?,
            @Json(name = "title")
            val title: String
        ) {
            @Keep
            @JsonClass(generateAdapter = true)
            data class MainPicture(
                @Json(name = "large")
                val large: String?,
                @Json(name = "medium")
                val medium: String
            )
        }
    }

    @Keep
    @JsonClass(generateAdapter = true)
    data class RelatedManga(
        @Json(name = "node")
        val node: Node,
        @Json(name = "relation_type")
        val relationType: String,
        @Json(name = "relation_type_formatted")
        val relationTypeFormatted: String
    ) {
        @Keep
        @JsonClass(generateAdapter = true)
        data class Node(
            @Json(name = "id")
            val id: Int,
            @Json(name = "main_picture")
            val mainPicture: MainPicture?,
            @Json(name = "title")
            val title: String
        ) {
            @Keep
            @JsonClass(generateAdapter = true)
            data class MainPicture(
                @Json(name = "large")
                val large: String?,
                @Json(name = "medium")
                val medium: String
            )
        }
    }

    @Keep
    @JsonClass(generateAdapter = true)
    data class RelatedAnime(
        @Json(name = "node")
        val node: Node,
        @Json(name = "relation_type")
        val relationType: String,
        @Json(name = "relation_type_formatted")
        val relationTypeFormatted: String
    ) {
        @Keep
        @JsonClass(generateAdapter = true)
        data class Node(
            @Json(name = "id")
            val id: Int,
            @Json(name = "main_picture")
            val mainPicture: MainPicture?,
            @Json(name = "title")
            val title: String
        ) {
            @Keep
            @JsonClass(generateAdapter = true)
            data class MainPicture(
                @Json(name = "large")
                val large: String?,
                @Json(name = "medium")
                val medium: String
            )
        }
    }

    @Keep
    @JsonClass(generateAdapter = true)
    data class Serialization(
        @Json(name = "node")
        val node: Node
    ) {
        @Keep
        @JsonClass(generateAdapter = true)
        data class Node(
            @Json(name = "id")
            val id: Int,
            @Json(name = "name")
            val name: String
        )
    }
}