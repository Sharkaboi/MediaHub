package com.sharkaboi.mediahub.common.data.api.models.anime

import androidx.annotation.Keep
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass


@Keep
@JsonClass(generateAdapter = true)
data class AnimeSearchResponse(
    @Json(name = "data")
    val `data`: List<Data>,
    @Json(name = "paging")
    val paging: Paging
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
}