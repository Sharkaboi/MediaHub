package com.sharkaboi.mediahub.modules.manga_details.util

import android.content.Context
import android.text.Spanned
import androidx.core.text.HtmlCompat
import androidx.core.text.toSpanned
import com.sharkaboi.mediahub.R
import com.sharkaboi.mediahub.common.extensions.ifNullOrBlank
import com.sharkaboi.mediahub.data.api.models.manga.MangaByIDResponse

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