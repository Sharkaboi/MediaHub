package com.sharkaboi.mediahub.data.api.enums

import android.content.Context
import com.sharkaboi.mediahub.R

@Suppress("EnumEntryName")
enum class UserAnimeSortType {
    list_score, // Descending
    list_updated_at, // Descending
    anime_title, // Ascending
    anime_start_date; // Descending

    companion object {
        fun getFormattedArray(context: Context) = arrayOf(
            context.getString(R.string.user_anime_sort_by_rating),
            context.getString(R.string.user_anime_sort_by_updated),
            context.getString(R.string.user_anime_sort_by_alphabetical),
            context.getString(R.string.user_anime_sort_by_newest)
        )

        fun parse(string: String?): UserAnimeSortType? {
            if (string == null) {
                return null
            }
            return runCatching {
                valueOf(string)
            }.getOrNull()
        }
    }
}
