package com.sharkaboi.mediahub.data.cache.models.anime

import androidx.room.Entity

@Entity
data class RelatedManga(
    val animeId: Int,
    val relationType: String,
    val relationTypeFormatted: String,
    val relatedMangaId: Int,
    val title: String,
    val pictureL: String?,
    val pictureM: String?
)
