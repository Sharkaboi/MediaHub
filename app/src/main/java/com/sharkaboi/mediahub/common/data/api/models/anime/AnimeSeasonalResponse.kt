package com.sharkaboi.mediahub.common.data.api.models.anime


import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass
import androidx.annotation.Keep

@Keep
@JsonClass(generateAdapter = true)
data class AnimeSeasonalResponse(
    @Json(name = "data")
    val `data`: List<Data>,
    @Json(name = "paging")
    val paging: Paging,
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

    @Keep
    @JsonClass(generateAdapter = true)
    data class Season(
        @Json(name = "season")
        val season: String,
        @Json(name = "year")
        val year: Int
    )
}