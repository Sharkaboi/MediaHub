package com.sharkaboi.mediahub.common.data.api.models.anime


import androidx.annotation.Keep
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@Keep
@JsonClass(generateAdapter = true)
data class AnimeRankingResponse(
    @Json(name = "data")
    val `data`: List<Data>
) {
    @Keep
    @JsonClass(generateAdapter = true)
    data class Data(
        @Json(name = "node")
        val node: Node,
        @Json(name = "ranking")
        val ranking: Ranking
    ) {
        @Keep
        @JsonClass(generateAdapter = true)
        data class Node(
            @Json(name = "id")
            val id: Int,
            @Json(name = "main_picture")
            val mainPicture: MainPicture?,
            @Json(name = "title")
            val title: String,
            @Json(name = "mean")
            val meanScore: Double?
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

        @Keep
        @JsonClass(generateAdapter = true)
        data class Ranking(
            @Json(name = "rank")
            val rank: Int
        )
    }
}