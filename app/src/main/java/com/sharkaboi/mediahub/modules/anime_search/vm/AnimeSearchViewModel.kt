package com.sharkaboi.mediahub.modules.anime_search.vm

import androidx.lifecycle.*
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.sharkaboi.mediahub.data.api.models.anime.AnimeSearchResponse
import com.sharkaboi.mediahub.modules.anime_search.repository.AnimeSearchRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class AnimeSearchViewModel
@Inject constructor(
    private val animeSearchRepository: AnimeSearchRepository,
    private val savedStateHandle: SavedStateHandle
) : ViewModel() {
    private val searchedQuery = savedStateHandle.get<String>(SEARCH_QUERY_KEY)

    private val _pagedSearchResult = MutableLiveData<PagingData<AnimeSearchResponse.Data>>()
    val pagedSearchResult: LiveData<PagingData<AnimeSearchResponse.Data>> = _pagedSearchResult

    init {
        Timber.d("Saved state for anime search $searchedQuery")
        if (searchedQuery != null) {
            getAnime(searchedQuery)
        }
    }

    fun getAnime(query: String) = viewModelScope.launch {
        savedStateHandle.set(SEARCH_QUERY_KEY, query)
        val newResult: Flow<PagingData<AnimeSearchResponse.Data>> =
            animeSearchRepository.getAnimeByQuery(
                query = query
            ).cachedIn(viewModelScope)
        _pagedSearchResult.value = newResult.firstOrNull()
    }

    companion object {
        private const val SEARCH_QUERY_KEY = "searchQuery"
    }
}
