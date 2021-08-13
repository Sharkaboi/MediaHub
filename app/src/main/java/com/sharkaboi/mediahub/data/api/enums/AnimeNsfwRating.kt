package com.sharkaboi.mediahub.data.api.enums

import android.content.Context
import com.sharkaboi.mediahub.R

enum class AnimeNsfwRating {
    white,
    gray,
    black
}

internal fun Context.getAnimeNsfwRating(rating: String?): String {
    return when {
        rating == null -> {
            getString(R.string.n_a)
        }
        rating.trim() == AnimeNsfwRating.white.name -> {
            getString(R.string.anime_nsfw_rating_white)
        }
        rating.trim() == AnimeNsfwRating.gray.name -> {
            getString(R.string.anime_nsfw_rating_gray)
        }
        rating.trim() == AnimeNsfwRating.black.name -> {
            getString(R.string.anime_nsfw_rating_black)
        }
        else -> {
            getString(R.string.anime_nsfw_rating_unknown)
        }
    }
}
