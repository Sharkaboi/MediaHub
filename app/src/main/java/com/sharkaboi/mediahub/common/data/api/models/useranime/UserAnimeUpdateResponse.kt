package com.sharkaboi.mediahub.common.data.api.models.useranime


import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass
import androidx.annotation.Keep

@Keep
@JsonClass(generateAdapter = true)
data class UserAnimeUpdateResponse(
    @Json(name = "comments")
    val comments: String,
    @Json(name = "is_rewatching")
    val isReWatching: Boolean,
    @Json(name = "num_times_rewatched")
    val numTimesReWatched: Int,
    @Json(name = "num_episodes_watched")
    val numWatchedEpisodes: Int,
    @Json(name = "priority")
    val priority: Int,
    @Json(name = "rewatch_value")
    val reWatchValue: Int,
    @Json(name = "score")
    val score: Int,
    @Json(name = "status")
    val status: String,
    @Json(name = "tags")
    val tags: List<String>,
    @Json(name = "updated_at")
    val updatedAt: String
)