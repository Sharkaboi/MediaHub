package com.sharkaboi.mediahub.data.api.models.user

import androidx.annotation.Keep
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@Keep
@JsonClass(generateAdapter = true)
data class UserDetailsResponse(
    @Json(name = "anime_statistics")
    val animeStatistics: AnimeStatistics?,
    @Json(name = "id")
    val id: Int,
    @Json(name = "joined_at")
    val joinedAt: String,
    @Json(name = "location")
    val location: String?,
    @Json(name = "name")
    val name: String,
    @Json(name = "gender")
    val gender: String?,
    @Json(name = "birthday")
    val birthday: String?,
    @Json(name = "time_zone")
    val timeZone: String?,
    @Json(name = "is_supporter")
    val isSupporter: Boolean?,
    @Json(name = "picture")
    val profilePicUrl: String
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
        val numDaysDropped: Double,
        @Json(name = "num_days_on_hold")
        val numDaysOnHold: Double,
        @Json(name = "num_days_watched")
        val numDaysWatched: Double,
        @Json(name = "num_days_watching")
        val numDaysWatching: Double,
        @Json(name = "num_episodes")
        val numEpisodes: Double,
        @Json(name = "num_items")
        val numItems: Double,
        @Json(name = "num_items_completed")
        val numItemsCompleted: Double,
        @Json(name = "num_items_dropped")
        val numItemsDropped: Double,
        @Json(name = "num_items_on_hold")
        val numItemsOnHold: Double,
        @Json(name = "num_items_plan_to_watch")
        val numItemsPlanToWatch: Double,
        @Json(name = "num_items_watching")
        val numItemsWatching: Double,
        @Json(name = "num_times_rewatched")
        val numTimesReWatched: Double
    )
}
