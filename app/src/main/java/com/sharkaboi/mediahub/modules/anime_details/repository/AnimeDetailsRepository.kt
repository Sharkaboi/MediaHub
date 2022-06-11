package com.sharkaboi.mediahub.modules.anime_details.repository

import com.sharkaboi.mediahub.GetNextAiringAnimeEpisodeQuery
import com.sharkaboi.mediahub.data.api.models.anime.AnimeByIDResponse
import com.sharkaboi.mediahub.data.wrappers.MHTaskState
import com.sharkaboi.mediahub.modules.anime_details.util.AnimeDetailsUpdateClass

interface AnimeDetailsRepository {

    suspend fun getAnimeById(animeId: Int): MHTaskState<AnimeByIDResponse>

    suspend fun getNextAiringEpisodeById(animeId: Int): MHTaskState<GetNextAiringAnimeEpisodeQuery.ReturnedMedia>

    suspend fun updateAnimeStatus(
        animeDetailsUpdateClass: AnimeDetailsUpdateClass
    ): MHTaskState<Unit>

    suspend fun removeAnimeFromList(animeId: Int): MHTaskState<Unit>
}
