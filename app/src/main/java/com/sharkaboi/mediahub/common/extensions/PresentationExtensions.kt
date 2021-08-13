package com.sharkaboi.mediahub.common.extensions

import GetNextAiringAnimeEpisodeQuery
import android.content.Context
import android.text.Spanned
import androidx.annotation.StringRes
import androidx.core.text.HtmlCompat
import androidx.core.text.toSpanned
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.sharkaboi.mediahub.R
import com.sharkaboi.mediahub.common.util.getLocalDateFromDayAndTime
import com.sharkaboi.mediahub.data.api.models.anime.AnimeByIDResponse
import com.sharkaboi.mediahub.data.api.models.manga.MangaByIDResponse
import java.time.format.DateTimeFormatter
import kotlin.time.Duration
import kotlin.time.ExperimentalTime

internal fun Context.getProgressStringWith(progress: Int?, total: Int?): String {
    val totalCountString = if (total == null || total == 0)
        getString(R.string.no_count_fount_marker)
    else
        total.toString()
    val progressCount = progress ?: 0
    return getString(
        R.string.media_progress_template,
        progressCount,
        totalCountString
    )
}

internal fun Context.getEpisodesOfAnimeString(episodes: Int): String {
    return resources.getQuantityString(
        R.plurals.episode_count_template,
        episodes,
        if (episodes == 0) getString(R.string.n_a) else episodes.toString()
    )
}

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

internal fun Context.getVolumesOfMangaString(volumes: Int): String {
    return resources.getQuantityString(
        R.plurals.volume_count_template,
        volumes,
        if (volumes == 0) getString(R.string.n_a) else volumes.toString()
    )
}

internal fun Context.getChaptersOfMangaString(chapters: Int): String {
    return resources.getQuantityString(
        R.plurals.chapter_count_template,
        chapters,
        if (chapters == 0) getString(R.string.n_a) else chapters.toString()
    )
}

internal fun Context.getAnimeSeasonString(season: String?, year: Int?): String {
    return if (season == null || year == null) {
        getString(R.string.anime_season_unknown_template, getString(R.string.n_a))
    } else {
        getString(R.string.anime_season_template, season.capitalizeFirst(), year)
    }
}

internal fun Context.getAnimeOriginalSourceString(source: String?): String {
    val sourceValue = source?.replaceUnderScoreWithWhiteSpace()
        ?.capitalizeFirst()
        ?: getString(R.string.n_a)
    return getString(R.string.anime_original_source_template, sourceValue)
}

internal fun Context.getMediaTypeStringWith(type: String): String {
    return getString(
        R.string.media_type_template,
        type.uppercase()
    )
}

internal fun Context.getRatingStringWithRating(rating: Number?): String {
    return resources.getQuantityString(
        R.plurals.rating_hint,
        rating?.toInt() ?: 0,
        rating?.toFloat() ?: 0F
    )
}

internal fun Context.getAnimeBroadcastTime(broadcast: AnimeByIDResponse.Broadcast?): String {
    runCatching {
        if (broadcast == null) {
            return getString(R.string.n_a)
        } else if (broadcast.startTime == null) {
            return getString(R.string.anime_broadcast_on_day, broadcast.dayOfTheWeek)
        }
        val localTime = getLocalDateFromDayAndTime(broadcast.dayOfTheWeek, broadcast.startTime)
        return localTime?.format(DateTimeFormatter.ofPattern("EEEE h:mm a zzzz"))
            ?: getString(R.string.n_a)
    }.getOrElse {
        it.printStackTrace()
        return getString(R.string.n_a)
    }
}

internal fun Context.getFormattedAnimeTitlesString(titles: AnimeByIDResponse.AlternativeTitles?): Spanned {
    if (titles == null) {
        return getString(R.string.n_a).toSpanned()
    }
    val synonyms = titles.synonyms?.joinToString().ifNullOrBlank { getString(R.string.n_a) }
    val englishTitle = titles.en.ifNullOrBlank { getString(R.string.n_a) }
    val japaneseTitle = titles.ja.ifNullOrBlank { getString(R.string.n_a) }
    val html = getString(R.string.alternate_titles_html, englishTitle, japaneseTitle, synonyms)
    return HtmlCompat.fromHtml(html, HtmlCompat.FROM_HTML_MODE_COMPACT)
}

internal fun Context.getFormattedMangaTitlesString(titles: MangaByIDResponse.AlternativeTitles?): Spanned {
    if (titles == null) {
        return getString(R.string.n_a).toSpanned()
    }
    val synonyms = titles.synonyms?.joinToString().ifNullOrBlank { getString(R.string.n_a) }
    val englishTitle = titles.en.ifNullOrBlank { getString(R.string.n_a) }
    val japaneseTitle = titles.ja.ifNullOrBlank { getString(R.string.n_a) }
    val html = getString(R.string.alternate_titles_html, englishTitle, japaneseTitle, synonyms)
    return HtmlCompat.fromHtml(html, HtmlCompat.FROM_HTML_MODE_COMPACT)
}

internal fun Context.getAnimeStats(stats: AnimeByIDResponse.Statistics?): Spanned {
    if (stats == null) {
        return getString(R.string.n_a).toSpanned()
    }
    val html = getString(
        R.string.anime_stats_html,
        stats.numListUsers.toLong(),
        stats.status.watching.toLong(),
        stats.status.planToWatch.toLong(),
        stats.status.completed.toLong(),
        stats.status.dropped.toLong(),
        stats.status.onHold.toLong()
    )
    return HtmlCompat.fromHtml(html, HtmlCompat.FROM_HTML_MODE_COMPACT)
}

internal fun Context.getMangaStats(numListUsers: Int?, numScoredUsers: Int?): Spanned {
    val listUsers = numListUsers?.toString() ?: getString(R.string.n_a)
    val scoredUsers = numScoredUsers?.toString() ?: getString(R.string.n_a)
    val html = getString(
        R.string.manga_stats_html,
        listUsers,
        scoredUsers
    )
    return HtmlCompat.fromHtml(html, HtmlCompat.FROM_HTML_MODE_COMPACT)
}

@ExperimentalTime
internal fun Context.getEpisodeLengthFromSeconds(seconds: Int?): String {
    runCatching {
        if (seconds == null || seconds <= 0) {
            return getString(R.string.anime_episode_length_n_a)
        }
        val duration = Duration.seconds(seconds.toLong())
        val hours = duration.inWholeHours.toInt()
        val minutes = duration.minus(Duration.hours(hours)).inWholeMinutes
        return if (hours <= 0) getString(
            R.string.anime_episode_length_mins,
            minutes
        ) else getString(
            R.string.anime_episode_length_hours,
            hours,
            minutes
        )
    }.getOrElse {
        it.printStackTrace()
        return getString(R.string.anime_episode_length_n_a)
    }
}

@ExperimentalTime
internal fun Context.getAiringTimeFormatted(nextEp: GetNextAiringAnimeEpisodeQuery.NextAiringEpisode): String {
    val timeFromNow = runCatching {
        if (nextEp.timeUntilAiring <= 0) {
            return@runCatching getString(R.string.anime_next_episode_airing_n_a)
        }
        var currentDuration = Duration.seconds(nextEp.timeUntilAiring.toLong())
        val days = currentDuration.inWholeDays.toInt()
        currentDuration = currentDuration.minus(Duration.days(days))
        val hours = currentDuration.inWholeHours.toInt()
        currentDuration = currentDuration.minus(Duration.hours(hours))
        val minutes = currentDuration.inWholeMinutes.toInt()
        if (days <= 0 && hours <= 0) {
            getString(R.string.anime_next_episode_airing_minutes, minutes)
        } else if (days <= 0) {
            getString(R.string.anime_next_episode_airing_hours, hours, minutes)
        } else {
            getString(R.string.anime_next_episode_airing_days, days, hours, minutes)
        }
    }.getOrElse {
        it.printStackTrace()
        getString(R.string.anime_next_episode_airing_n_a)
    }
    return buildString {
        append(getString(R.string.anime_next_episode_prefix))
        append(nextEp.episode)
        append(getString(R.string.anime_next_episode_suffix))
        append(timeFromNow)
    }
}

internal fun Context.showNoActionOkDialog(@StringRes title: Int, content: CharSequence?) {
    MaterialAlertDialogBuilder(this)
        .setTitle(title)
        .setMessage(
            content.ifNullOrBlank { getString(R.string.n_a) }
        ).setPositiveButton(R.string.ok) { dialog, _ ->
            dialog.dismiss()
        }.show()
}
