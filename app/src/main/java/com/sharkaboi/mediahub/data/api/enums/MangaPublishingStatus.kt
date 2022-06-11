package com.sharkaboi.mediahub.data.api.enums

import android.content.Context
import com.sharkaboi.mediahub.R

@Suppress("EnumEntryName")
enum class MangaPublishingStatus {
    finished,
    currently_publishing,
    not_yet_published
}

internal fun Context.getMangaPublishStatus(status: String): String {
    return when (status.trim()) {
        MangaPublishingStatus.finished.name -> getString(R.string.manga_publishing_finished)
        MangaPublishingStatus.currently_publishing.name -> getString(R.string.manga_publishing_ongoing)
        MangaPublishingStatus.not_yet_published.name -> getString(R.string.manga_publishing_not_yet_aired)
        else -> getString(R.string.manga_publishing_unknown)
    }
}
