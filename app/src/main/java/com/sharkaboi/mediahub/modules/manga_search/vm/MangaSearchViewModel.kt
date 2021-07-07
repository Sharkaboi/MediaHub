package com.sharkaboi.mediahub.modules.manga_search.vm

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.sharkaboi.mediahub.data.api.models.manga.MangaSearchResponse
import com.sharkaboi.mediahub.modules.manga_search.repository.MangaSearchRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

@HiltViewModel
class MangaSearchViewModel
@Inject constructor(
    private val mangaSearchRepository: MangaSearchRepository
) : ViewModel() {

    private var _pagedSearchResult: Flow<PagingData<MangaSearchResponse.Data>>? = null

    suspend fun getManga(
        query: String
    ): Flow<PagingData<MangaSearchResponse.Data>> {
        val newResult: Flow<PagingData<MangaSearchResponse.Data>> =
            mangaSearchRepository.getMangaByQuery(
                query = query
            ).cachedIn(viewModelScope)
        _pagedSearchResult = newResult
        return newResult
    }
}
