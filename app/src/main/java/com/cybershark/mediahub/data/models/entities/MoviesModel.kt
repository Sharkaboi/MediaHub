package com.cybershark.mediahub.data.models.entities

data class MoviesModel(
    val id:String,
    val name:String,
    val image_url:String,
    val length_in_min:Int,
    val release_year:String,
    val rating:Float?,
    val description:String?
)
