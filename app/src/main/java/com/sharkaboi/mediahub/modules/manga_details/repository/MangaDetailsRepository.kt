package com.sharkaboi.mediahub.modules.manga_details.repository

import com.sharkaboi.mediahub.data.api.models.manga.MangaByIDResponse
import com.sharkaboi.mediahub.data.wrappers.MHTaskState
import com.sharkaboi.mediahub.modules.manga_details.util.MangaDetailsUpdateClass

interface MangaDetailsRepository {

    suspend fun getMangaById(mangaId: Int): MHTaskState<MangaByIDResponse>

    suspend fun updateMangaStatus(
        mangaDetailsUpdateClass: MangaDetailsUpdateClass
    ): MHTaskState<Unit>

    suspend fun removeMangaFromList(mangaId: Int): MHTaskState<Unit>
}
