package com.sharkaboi.mediahub.data.api.enums

import android.content.Context
import com.sharkaboi.mediahub.R

@Suppress("EnumEntryName")
enum class UserMangaSortType {
    list_score, // Descending
    list_updated_at, // Descending
    manga_title, // Ascending
    manga_start_date; // Descending

    companion object {
        fun getFormattedArray(context: Context) = arrayOf(
            context.getString(R.string.user_manga_sort_by_rating),
            context.getString(R.string.user_manga_sort_by_updated),
            context.getString(R.string.user_manga_sort_by_alphabetical),
            context.getString(R.string.user_manga_sort_by_newest)
        )

        fun parse(string: String?): UserMangaSortType? {
            if (string == null) {
                return null
            }
            return runCatching {
                valueOf(string)
            }.getOrNull()
        }
    }
}
