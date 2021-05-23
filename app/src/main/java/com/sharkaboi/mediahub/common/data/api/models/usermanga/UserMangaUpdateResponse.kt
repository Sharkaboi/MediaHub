package com.sharkaboi.mediahub.common.data.api.models.usermanga


import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass
import androidx.annotation.Keep

@Keep
@JsonClass(generateAdapter = true)
data class UserMangaUpdateResponse(
    @Json(name = "comments")
    val comments: String,
    @Json(name = "is_rereading")
    val isRereading: Boolean,
    @Json(name = "num_chapters_read")
    val numChaptersRead: Int,
    @Json(name = "num_times_reread")
    val numTimesReread: Int,
    @Json(name = "num_volumes_read")
    val numVolumesRead: Int,
    @Json(name = "priority")
    val priority: Int,
    @Json(name = "reread_value")
    val rereadValue: Int,
    @Json(name = "score")
    val score: Int,
    @Json(name = "status")
    val status: String?,
    @Json(name = "tags")
    val tags: List<Any>,
    @Json(name = "updated_at")
    val updatedAt: String
)