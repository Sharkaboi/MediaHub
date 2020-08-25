package com.cybershark.mediahub.data.room.entities

data class MangaModel(
    val id:String,
    val name:String,
    val image_url:String,
    val latest_chap_title:String,
    val total_chapters:Long?,
    val read_count:Long
)