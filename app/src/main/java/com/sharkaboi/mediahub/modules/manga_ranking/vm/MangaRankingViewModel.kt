package com.sharkaboi.mediahub.modules.manga_ranking.vm

import androidx.lifecycle.*
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.sharkaboi.mediahub.data.api.enums.MangaRankingType
import com.sharkaboi.mediahub.data.api.models.manga.MangaRankingResponse
import com.sharkaboi.mediahub.modules.manga_ranking.repository.MangaRankingRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class MangaRankingViewModel
@Inject constructor(
    private val mangaRankingRepository: MangaRankingRepository,
    private val savedStateHandle: SavedStateHandle
) : ViewModel() {
    private val selectedRankingType: String? =
        savedStateHandle.get(MANGA_RANKING_KEY)

    private var _rankingType: MangaRankingType =
        MangaRankingType.getMangaRankingFromString(selectedRankingType)
    val rankingType: MangaRankingType get() = _rankingType

    private val _result = MutableLiveData<PagingData<MangaRankingResponse.Data>>()
    val result: LiveData<PagingData<MangaRankingResponse.Data>> = _result

    init {
        Timber.d("Saved state for manga ranking type $selectedRankingType")
        getMangaRankingOfFilter()
    }

    private fun getMangaRankingOfFilter() = viewModelScope.launch {
        val newResult: Flow<PagingData<MangaRankingResponse.Data>> =
            mangaRankingRepository
                .getMangaRanking(_rankingType)
                .cachedIn(viewModelScope)
        _result.value = newResult.firstOrNull() ?: PagingData.empty()
    }

    fun setRankingType(mangaRankingType: MangaRankingType) {
        _rankingType = mangaRankingType
        savedStateHandle.set(MANGA_RANKING_KEY, mangaRankingType.name)
        getMangaRankingOfFilter()
    }

    companion object {
        private const val MANGA_RANKING_KEY = "mangaRankingType"
    }
}
