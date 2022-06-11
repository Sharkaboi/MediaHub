package com.sharkaboi.mediahub.modules.anime_details.util

import androidx.lifecycle.MutableLiveData
import com.sharkaboi.mediahub.data.api.enums.AnimeStatus

data class AnimeDetailsUpdateClass(
    val animeStatus: AnimeStatus?,
    val score: Int?,
    val numWatchedEpisode: Int?,
    val totalEps: Int,
    val animeId: Int
)

fun MutableLiveData<AnimeDetailsUpdateClass>.setStatus(animeStatus: AnimeStatus?) {
    value = value?.copy(animeStatus = animeStatus)
}

// WARN : Doesnt check with total ep count
fun MutableLiveData<AnimeDetailsUpdateClass>.setWatchedEps(numWatchedEpisode: Int?) {
    value = value?.copy(numWatchedEpisode = numWatchedEpisode)
}

// WARN : Doesnt check if > 10
fun MutableLiveData<AnimeDetailsUpdateClass>.setScore(score: Int) {
    value = value?.copy(score = score)
}

fun MutableLiveData<AnimeDetailsUpdateClass>.setWatchedAsTotal() {
    // If total eps not available (??), keep watching count as same
    if (value?.totalEps == 0) {
        return
    }

    setWatchedEps(value?.totalEps)
}

fun MutableLiveData<AnimeDetailsUpdateClass>.isNotOfStatus(vararg animeStatuses: AnimeStatus): Boolean {
    return value?.animeStatus == null || value?.animeStatus !in animeStatuses
}

fun MutableLiveData<AnimeDetailsUpdateClass>.isMoreOrEqualToTotal(count: Int): Boolean {
    val isTotalAvailable = value?.totalEps != 0
    val totalEps = value?.totalEps ?: 0
    return isTotalAvailable && count >= totalEps
}

fun MutableLiveData<AnimeDetailsUpdateClass>.getAddedWatchedEps(count: Int): Int {
    // set watched eps as count when null as well
    return value?.numWatchedEpisode?.plus(count) ?: count
}