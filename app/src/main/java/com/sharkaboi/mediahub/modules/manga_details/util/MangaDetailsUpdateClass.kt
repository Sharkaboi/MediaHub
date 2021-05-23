package com.sharkaboi.mediahub.modules.manga_details.util

import com.sharkaboi.mediahub.common.data.api.enums.MangaStatus

data class MangaDetailsUpdateClass(
    val mangaStatus: MangaStatus?,
    val score: Int?,
    val numReadVolumes: Int?,
    val numReadChapters: Int?,
    val totalVolumes: Int,
    val totalChapters: Int,
    val mangaId: Int
)
