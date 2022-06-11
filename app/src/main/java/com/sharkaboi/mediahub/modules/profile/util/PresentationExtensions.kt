package com.sharkaboi.mediahub.modules.profile.util

import android.content.Context
import com.sharkaboi.mediahub.R

internal fun Context.getEpisodesOfAnimeFullString(episodes: Double): String {
    return resources.getQuantityString(
        R.plurals.episode_count_full_template,
        episodes.toInt(),
        if (episodes == 0.0) getString(R.string.n_a) else episodes.toInt().toString()
    )
}

internal fun Context.getDaysCountString(days: Long): String {
    return resources.getQuantityString(
        R.plurals.days_count_template,
        days.toInt(),
        if (days == 0L) getString(R.string.n_a) else days.toString()
    )
}