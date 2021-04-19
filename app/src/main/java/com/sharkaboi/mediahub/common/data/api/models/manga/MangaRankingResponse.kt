package com.sharkaboi.mediahub.common.data.api.models.manga


import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass
import androidx.annotation.Keep

@Keep
@JsonClass(generateAdapter = true)
data class MangaRankingResponse(
    @Json(name = "data")
    val `data`: List<Data>,
    @Json(name = "paging")
    val paging: Paging
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

        @Keep
        @JsonClass(generateAdapter = true)
        data class Ranking(
            @Json(name = "rank")
            val rank: Int
        )
    }

    @Keep
    @JsonClass(generateAdapter = true)
    data class Paging(
        @Json(name = "next")
        val next: String
    )
}