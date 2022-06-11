package com.sharkaboi.mediahub.modules.manga_details.util

import androidx.lifecycle.MutableLiveData
import com.sharkaboi.mediahub.data.api.enums.MangaStatus

data class MangaDetailsUpdateClass(
    val mangaStatus: MangaStatus?,
    val score: Int?,
    val numReadVolumes: Int?,
    val numReadChapters: Int?,
    val totalVolumes: Int,
    val totalChapters: Int,
    val mangaId: Int
)

fun MutableLiveData<MangaDetailsUpdateClass>.setStatus(mangaStatus: MangaStatus?) {
    value = value?.copy(mangaStatus = mangaStatus)
}

// WARN : Doesnt check with total vol count
fun MutableLiveData<MangaDetailsUpdateClass>.setReadVolumes(numReadVolumes: Int?) {
    value = value?.copy(numReadVolumes = numReadVolumes)
}

// WARN : Doesnt check with total chap count
fun MutableLiveData<MangaDetailsUpdateClass>.setReadChapters(numReadChapters: Int?) {
    value = value?.copy(numReadChapters = numReadChapters)
}

// WARN : Doesnt check if > 10
fun MutableLiveData<MangaDetailsUpdateClass>.setScore(score: Int) {
    value = value?.copy(score = score)
}

fun MutableLiveData<MangaDetailsUpdateClass>.setReadAsTotalChaps() {
    // If total chaps not available (??), keep read count as same
    if (value?.totalChapters == 0) {
        return
    }

    setReadChapters(value?.totalChapters)
}

fun MutableLiveData<MangaDetailsUpdateClass>.setReadAsTotalVols() {
    // If total vols not available (??), keep read count as same
    if (value?.totalVolumes == 0) {
        return
    }

    setReadChapters(value?.totalVolumes)
}

fun MutableLiveData<MangaDetailsUpdateClass>.setReadAsTotal() {
    setReadAsTotalVols()
    setReadAsTotalChaps()
}

fun MutableLiveData<MangaDetailsUpdateClass>.isNotOfStatus(vararg mangaStatuses: MangaStatus): Boolean {
    return value?.mangaStatus == null || value?.mangaStatus !in mangaStatuses
}

fun MutableLiveData<MangaDetailsUpdateClass>.isMoreOrEqualToTotalVols(count: Int): Boolean {
    val isTotalVolsAvailable = value?.totalVolumes != 0
    val totalVols = value?.totalVolumes ?: 0
    return isTotalVolsAvailable && count >= totalVols
}

fun MutableLiveData<MangaDetailsUpdateClass>.isMoreOrEqualToTotalChaps(count: Int): Boolean {
    val isTotalChapsAvailable = value?.totalChapters != 0
    val totalChaps = value?.totalChapters ?: 0
    return isTotalChapsAvailable && count >= totalChaps
}