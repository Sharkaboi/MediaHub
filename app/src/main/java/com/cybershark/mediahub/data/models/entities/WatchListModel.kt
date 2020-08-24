package com.cybershark.mediahub.data.models.entities

data class WatchListModel(
    val id:String,
    val name:String,
    val image_url:String,
    val next_episode_title:String?,
    val total_episodes:Long,
    val watched_count:Long
)