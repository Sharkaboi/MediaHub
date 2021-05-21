package com.sharkaboi.mediahub.modules.anime_search.vm

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.sharkaboi.mediahub.common.data.api.models.anime.AnimeSearchResponse
import com.sharkaboi.mediahub.modules.anime_search.repository.AnimeSearchRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

@HiltViewModel
class AnimeSearchViewModel
@Inject constructor(
    private val animeSearchRepository: AnimeSearchRepository
) : ViewModel() {

    private var _pagedSearchResult: Flow<PagingData<AnimeSearchResponse.Data>>? = null

    suspend fun getAnime(
        query: String
    ): Flow<PagingData<AnimeSearchResponse.Data>> {
        val newResult: Flow<PagingData<AnimeSearchResponse.Data>> =
            animeSearchRepository.getAnimeByQuery(
                query = query
            ).cachedIn(viewModelScope)
        _pagedSearchResult = newResult
        return newResult
    }

    companion object {
        private const val TAG = "AnimeSearchViewModel"
    }
}