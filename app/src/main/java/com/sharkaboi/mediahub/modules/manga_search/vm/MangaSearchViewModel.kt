package com.sharkaboi.mediahub.modules.manga_search.vm

import androidx.lifecycle.*
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.sharkaboi.mediahub.data.api.models.manga.MangaSearchResponse
import com.sharkaboi.mediahub.modules.manga_search.repository.MangaSearchRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class MangaSearchViewModel
@Inject constructor(
    private val mangaSearchRepository: MangaSearchRepository,
    private val savedStateHandle: SavedStateHandle
) : ViewModel() {
    private val searchedQuery = savedStateHandle.get<String>(SEARCH_QUERY_KEY)

    private val _pagedSearchResult = MutableLiveData<PagingData<MangaSearchResponse.Data>>()
    val pagedSearchResult: LiveData<PagingData<MangaSearchResponse.Data>> = _pagedSearchResult

    init {
        Timber.d("Saved state for manga search $searchedQuery")
        if (searchedQuery != null) {
            getManga(searchedQuery)
        }
    }

    fun getManga(query: String) = viewModelScope.launch {
        savedStateHandle.set(SEARCH_QUERY_KEY, query)
        val newResult: Flow<PagingData<MangaSearchResponse.Data>> =
            mangaSearchRepository.getMangaByQuery(
                query = query
            ).cachedIn(viewModelScope)
        _pagedSearchResult.value = newResult.firstOrNull()
    }

    companion object {
        private const val SEARCH_QUERY_KEY = "searchQuery"
    }
}
