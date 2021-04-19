package com.sharkaboi.mediahub.common.data.api.models


import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass
import androidx.annotation.Keep

@Keep
@JsonClass(generateAdapter = true)
data class UserDetailsResponse(
    @Json(name = "anime_statistics")
    val animeStatistics: AnimeStatistics,
    @Json(name = "id")
    val id: Int,
    @Json(name = "joined_at")
    val joinedAt: String,
    @Json(name = "location")
    val location: String,
    @Json(name = "name")
    val name: String
) {
    @Keep
    @JsonClass(generateAdapter = true)
    data class AnimeStatistics(
        @Json(name = "mean_score")
        val meanScore: Double,
        @Json(name = "num_days")
        val numDays: Double,
        @Json(name = "num_days_completed")
        val numDaysCompleted: Double,
        @Json(name = "num_days_dropped")
        val numDaysDropped: Int,
        @Json(name = "num_days_on_hold")
        val numDaysOnHold: Int,
        @Json(name = "num_days_watched")
        val numDaysWatched: Double,
        @Json(name = "num_days_watching")
        val numDaysWatching: Double,
        @Json(name = "num_episodes")
        val numEpisodes: Int,
        @Json(name = "num_items")
        val numItems: Int,
        @Json(name = "num_items_completed")
        val numItemsCompleted: Int,
        @Json(name = "num_items_dropped")
        val numItemsDropped: Int,
        @Json(name = "num_items_on_hold")
        val numItemsOnHold: Int,
        @Json(name = "num_items_plan_to_watch")
        val numItemsPlanToWatch: Int,
        @Json(name = "num_items_watching")
        val numItemsWatching: Int,
        @Json(name = "num_times_rewatched")
        val numTimesRewatched: Int
    )
}