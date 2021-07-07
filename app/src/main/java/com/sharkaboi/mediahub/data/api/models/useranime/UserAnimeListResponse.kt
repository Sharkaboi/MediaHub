package com.sharkaboi.mediahub.data.api.models.useranime

import androidx.annotation.Keep
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@Keep
@JsonClass(generateAdapter = true)
data class UserAnimeListResponse(
    @Json(name = "data")
    val `data`: List<Data>
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
            val isReWatching: Boolean,
            @Json(name = "num_episodes_watched")
            val numWatchedEpisodes: Int,
            @Json(name = "score")
            val score: Int,
            @Json(name = "status")
            val status: String?,
            @Json(name = "updated_at")
            val updatedAt: String
        )

        @Keep
        @JsonClass(generateAdapter = true)
        data class Node(
            @Json(name = "id")
            val id: Int,
            @Json(name = "num_episodes")
            val numTotalEpisodes: Int,
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
}
