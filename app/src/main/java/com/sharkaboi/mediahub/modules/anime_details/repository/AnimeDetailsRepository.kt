package com.sharkaboi.mediahub.modules.anime_details.repository

import com.sharkaboi.GetNextAiringAnimeEpisodeQuery
import com.sharkaboi.mediahub.data.api.models.anime.AnimeByIDResponse
import com.sharkaboi.mediahub.data.wrappers.MHTaskState

interface AnimeDetailsRepository {

    suspend fun getAnimeById(animeId: Int): MHTaskState<AnimeByIDResponse>

    suspend fun getNextAiringEpisodeById(animeId: Int): MHTaskState<GetNextAiringAnimeEpisodeQuery.ReturnedMedia>

    suspend fun updateAnimeStatus(
        animeId: Int,
        animeStatus: String?,
        score: Int?,
        numWatchedEps: Int?
    ): MHTaskState<Unit>

    suspend fun removeAnimeFromList(animeId: Int): MHTaskState<Unit>
}
