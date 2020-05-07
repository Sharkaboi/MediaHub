package com.cybershark.mediahub.data.models

data class WatchListModel(
    val name:String,
    val image_url:String,
    val total_episodes:Long,
    val watched_count:Long
)