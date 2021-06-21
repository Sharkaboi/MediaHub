package com.sharkaboi.mediahub.data.api.enums

@Suppress("EnumEntryName")
enum class UserMangaSortType {
    list_score, //Descending
    list_updated_at, //Descending
    manga_title, //Ascending
    manga_start_date; //Descending

    companion object {
        fun getFormattedArray() = arrayOf(
            "Highest rating",
            "Last updated",
            "Alphabetical order",
            "Newest addition"
        )
    }
}