package com.sharkaboi.mediahub.data.api.enums

import android.content.Context
import com.sharkaboi.mediahub.R

object AnimeRating {
    private const val G = "g"
    private const val PG = "pg"
    private const val PG13 = "pg_13"
    private const val rRating = "r"
    private const val rPlusRating = "r+"
    private const val RX = "rx"

    fun Context.getAnimeRating(rating: String?): String {
        return when {
            rating == null -> getString(R.string.n_a)
            rating.trim() == G -> getString(R.string.anime_rating_g)
            rating.trim() == PG -> getString(R.string.anime_rating_pg)
            rating.trim() == PG13 -> getString(R.string.anime_rating_pg13)
            rating.trim() == rRating -> getString(R.string.anime_rating_r)
            rating.trim() == rPlusRating -> getString(R.string.anime_rating_r_plus)
            rating.trim() == RX -> getString(R.string.anime_rating_rx)
            else -> getString(R.string.n_a)
        }
    }
}
