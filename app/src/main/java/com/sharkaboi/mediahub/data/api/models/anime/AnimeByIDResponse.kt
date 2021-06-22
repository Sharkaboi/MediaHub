package com.sharkaboi.mediahub.data.api.models.anime

import androidx.annotation.Keep
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@Keep
@JsonClass(generateAdapter = true)
data class AnimeByIDResponse(
    @Json(name = "alternative_titles")
    val alternativeTitles: AlternativeTitles?,
    @Json(name = "average_episode_duration")
    val averageEpisodeDuration: Int?,
    @Json(name = "background")
    val background: String?,
    @Json(name = "broadcast")
    val broadcast: Broadcast?,
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
    @Json(name = "num_episodes")
    val numEpisodes: Int,
    @Json(name = "num_list_users")
    val numListUsers: Int,
    @Json(name = "num_scoring_users")
    val numScoringUsers: Int,
    @Json(name = "pictures")
    val pictures: List<Picture>,
    @Json(name = "popularity")
    val popularity: Int,
    @Json(name = "rank")
    val rank: Int?,
    @Json(name = "rating")
    val rating: String?,
    @Json(name = "recommendations")
    val recommendations: List<Recommendation>,
    @Json(name = "related_anime")
    val relatedAnime: List<RelatedAnime>,
    @Json(name = "related_manga")
    val relatedManga: List<RelatedManga>,
    @Json(name = "source")
    val source: String?,
    @Json(name = "start_date")
    val startDate: String?,
    @Json(name = "start_season")
    val startSeason: StartSeason?,
    @Json(name = "statistics")
    val statistics: Statistics?,
    @Json(name = "status")
    val status: String,
    @Json(name = "studios")
    val studios: List<Studio>,
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
    data class Broadcast(
        @Json(name = "day_of_the_week")
        val dayOfTheWeek: String,
        @Json(name = "start_time")
        val startTime: String?
    )

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
        @Json(name = "is_rewatching")
        val isReWatching: Boolean,
        @Json(name = "num_episodes_watched")
        val numEpisodesWatched: Int,
        @Json(name = "score")
        val score: Int,
//        @Json(name = "priority")
//        val priority: Int,
//        @Json(name = "rewatch_value")
//        val reWatchValue: Int,
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
    data class StartSeason(
        @Json(name = "season")
        val season: String,
        @Json(name = "year")
        val year: Int
    ) {
        fun toNavString(): String {
            return "${season}_$year"
        }
    }

    @Keep
    @JsonClass(generateAdapter = true)
    data class Statistics(
        @Json(name = "num_list_users")
        val numListUsers: Int,
        @Json(name = "status")
        val status: Status
    ) {
        @Keep
        @JsonClass(generateAdapter = true)
        data class Status(
            @Json(name = "completed")
            val completed: String,
            @Json(name = "dropped")
            val dropped: String,
            @Json(name = "on_hold")
            val onHold: String,
            @Json(name = "plan_to_watch")
            val planToWatch: String,
            @Json(name = "watching")
            val watching: String
        )
    }

    @Keep
    @JsonClass(generateAdapter = true)
    data class Studio(
        @Json(name = "id")
        val id: Int,
        @Json(name = "name")
        val name: String
    )
}