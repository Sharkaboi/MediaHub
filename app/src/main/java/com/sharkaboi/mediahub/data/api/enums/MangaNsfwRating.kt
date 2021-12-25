package com.sharkaboi.mediahub.data.api.enums

import android.content.Context
import com.sharkaboi.mediahub.R

@Suppress("EnumEntryName")
enum class MangaNsfwRating {
    white,
    gray,
    black
}

internal fun Context.getMangaNsfwRating(rating: String?): String {
    return when (rating?.trim()) {
        MangaNsfwRating.white.name -> getString(R.string.manga_nsfw_rating_white)
        MangaNsfwRating.gray.name -> getString(R.string.manga_nsfw_rating_gray)
        MangaNsfwRating.black.name -> getString(R.string.manga_nsfw_rating_black)
        else -> getString(R.string.n_a)
    }
}
