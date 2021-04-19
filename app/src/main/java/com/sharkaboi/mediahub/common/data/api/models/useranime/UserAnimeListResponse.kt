package com.sharkaboi.mediahub.common.data.api.models.useranime


import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass
import androidx.annotation.Keep

@Keep
@JsonClass(generateAdapter = true)
data class UserAnimeListResponse(
    @Json(name = "data")
    val `data`: List<Data>,
    @Json(name = "paging")
    val paging: Paging
) {
    @Keep
    @JsonClass(generateAdapter = true)
    data class Data(
        @Json(name = "list_status")
        val listStatus: ListStatus,
        @Json(name = "node")
        val node: Node
    ) {
        @Keep
        @JsonClass(generateAdapter = true)
        data class ListStatus(
            @Json(name = "is_rewatching")
            val isRewatching: Boolean,
            @Json(name = "num_episodes_watched")
            val numEpisodesWatched: Int,
            @Json(name = "num_watched_episodes")
            val numWatchedEpisodes: Int,
            @Json(name = "score")
            val score: Int,
            @Json(name = "status")
            val status: String,
            @Json(name = "updated_at")
            val updatedAt: String
        )

        @Keep
        @JsonClass(generateAdapter = true)
        data class Node(
            @Json(name = "id")
            val id: Int,
            @Json(name = "main_picture")
            val mainPicture: MainPicture,
            @Json(name = "title")
            val title: String
        ) {
            @Keep
            @JsonClass(generateAdapter = true)
            data class MainPicture(
                @Json(name = "large")
                val large: String,
                @Json(name = "medium")
                val medium: String
            )
        }
    }

    @Keep
    @JsonClass(generateAdapter = true)
    data class Paging(
        @Json(name = "next")
        val next: String
    )
}