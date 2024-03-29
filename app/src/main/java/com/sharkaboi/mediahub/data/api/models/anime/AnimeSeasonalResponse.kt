package com.sharkaboi.mediahub.data.api.models.anime

import androidx.annotation.Keep
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@Keep
@JsonClass(generateAdapter = true)
data class AnimeSeasonalResponse(
    @Json(name = "data")
    val `data`: List<Data>,
    @Json(name = "season")
    val season: Season
) {
    @Keep
    @JsonClass(generateAdapter = true)
    data class Data(
        @Json(name = "node")
        val node: Node
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
    }

    @Keep
    @JsonClass(generateAdapter = true)
    data class Season(
        @Json(name = "season")
        val season: String,
        @Json(name = "year")
        val year: Int
    )
}
