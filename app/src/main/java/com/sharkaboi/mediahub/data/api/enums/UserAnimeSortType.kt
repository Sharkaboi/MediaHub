package com.sharkaboi.mediahub.data.api.enums

@Suppress("EnumEntryName")
enum class UserAnimeSortType {
    list_score, // Descending
    list_updated_at, // Descending
    anime_title, // Ascending
    anime_start_date; // Descending

    companion object {
        fun getFormattedArray() = arrayOf(
            "Highest rating",
            "Last updated",
            "Alphabetical order",
            "Newest addition"
        )
    }
}
