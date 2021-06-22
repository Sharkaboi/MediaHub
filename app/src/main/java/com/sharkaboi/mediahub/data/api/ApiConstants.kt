package com.sharkaboi.mediahub.data.api

object ApiConstants {
    const val BEARER_SEPARATOR = "Bearer "
    const val API_START_OFFSET = 0
    const val API_PAGE_LIMIT = 10
    const val NSFW_ALSO = 1
    const val SFW_ONLY = 0
    const val ME_IDENTIFIER = "@me"
    const val USER_ANIME_EXTRA_FIELDS = "list_status,num_episodes"
    const val USER_MANGA_EXTRA_FIELDS = "id,title,main_picture,num_volumes,num_chapters,list_status"
    const val ANIME_LESS_FIELDS = "id,title,main_picture,mean"
    const val ANIME_ALL_FIELDS =
        "id,title,main_picture,alternative_titles,start_date,end_date,synopsis,mean,rank,popularity,num_list_users,num_scoring_users,nsfw,created_at,updated_at,media_type,status,genres,my_list_status,num_episodes,start_season,broadcast,source,average_episode_duration,rating,studios,pictures,background,related_anime,related_manga,recommendations,statistics"
    const val MANGA_LESS_FIELDS = "id,title,main_picture,mean"
    const val MANGA_ALL_FIELDS =
        "id,title,main_picture,alternative_titles,start_date,end_date,synopsis,mean,rank,popularity,num_list_users,num_scoring_users,nsfw,created_at,updated_at,media_type,status,genres,my_list_status,num_volumes,num_chapters,authors{first_name,last_name},pictures,background,related_anime,related_manga,recommendations,serialization{name}"
    const val PROFILE_FIELDS = "anime_statistics"
}