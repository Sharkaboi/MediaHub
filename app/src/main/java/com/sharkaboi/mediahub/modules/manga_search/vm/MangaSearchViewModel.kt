package com.sharkaboi.mediahub.modules.manga_search.vm

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.sharkaboi.mediahub.data.api.models.manga.MangaSearchResponse
import com.sharkaboi.mediahub.modules.manga_search.repository.MangaSearchRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.firstOrNull
import javax.inject.Inject

@HiltViewModel
class MangaSearchViewModel
@Inject constructor(
    private val mangaSearchRepository: MangaSearchRepository
) : ViewModel() {

    private val _pagedSearchResult = MutableLiveData<PagingData<MangaSearchResponse.Data>>()
    val pagedSearchResult: LiveData<PagingData<MangaSearchResponse.Data>> = _pagedSearchResult

    suspend fun getManga(query: String) {
        val newResult: Flow<PagingData<MangaSearchResponse.Data>> =
            mangaSearchRepository.getMangaByQuery(
                query = query
            ).cachedIn(viewModelScope)
        _pagedSearchResult.value = newResult.firstOrNull()
    }
}
