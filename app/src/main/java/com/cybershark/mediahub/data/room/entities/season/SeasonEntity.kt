package com.cybershark.mediahub.data.room.entities.season

data class SeasonEntity(
    val number: Int,
    val trakt: Int,
    val tvdb: Int,
    val tmdb: Int
)

/*

JSON Structure is flattened to columns for room.

{
    "number": 0,
    "ids": {
        "trakt": 1,
        "tvdb": 439371,
        "tmdb": 3577
    }
}

 */