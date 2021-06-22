package com.sharkaboi.mediahub.data.paging

import android.util.Log
import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.haroldadmin.cnradapter.NetworkResponse
import com.sharkaboi.mediahub.common.extensions.emptyString
import com.sharkaboi.mediahub.data.api.ApiConstants
import com.sharkaboi.mediahub.data.api.enums.AnimeSeason
import com.sharkaboi.mediahub.data.api.models.ApiError
import com.sharkaboi.mediahub.data.api.models.anime.AnimeSeasonalResponse
import com.sharkaboi.mediahub.data.api.retrofit.AnimeService
import com.sharkaboi.mediahub.data.wrappers.NoTokenFoundError

class AnimeSeasonalDataSource(
    private val animeService: AnimeService,
    private val accessToken: String?,
    private val animeSeason: AnimeSeason,
    private val year: Int,
    private val showNsfw: Boolean = false
) : PagingSource<Int, AnimeSeasonalResponse.Data>() {

    override fun getRefreshKey(state: PagingState<Int, AnimeSeasonalResponse.Data>): Int? {
        // Try to find the page key of the closest page to anchorPosition, from
        // either the prevKey or the nextKey, but you need to handle nullability
        // here:
        //  * prevKey == null -> anchorPage is the first page.
        //  * nextKey == null -> anchorPage is the last page.
        //  * both prevKey and nextKey null -> anchorPage is the initial page, so
        //    just return null.
        return state.anchorPosition?.let { anchorPosition ->
            val anchorPage = state.closestPageToPosition(anchorPosition)
            anchorPage?.prevKey?.plus(ApiConstants.API_PAGE_LIMIT) ?: anchorPage?.nextKey?.minus(
                ApiConstants.API_PAGE_LIMIT
            )
        }
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, AnimeSeasonalResponse.Data> {
        Log.d(TAG, "params : ${params.key}")
        try {
            val offset = params.key ?: ApiConstants.API_START_OFFSET
            val limit = ApiConstants.API_PAGE_LIMIT
            if (accessToken == null) {
                return LoadResult.Error(
                    NoTokenFoundError.getThrowable()
                )
            } else {
                val response = animeService.getAnimeBySeasonAsync(
                    authHeader = ApiConstants.BEARER_SEPARATOR + accessToken,
                    offset = offset,
                    limit = limit,
                    season = animeSeason.name,
                    year = year,
                    nsfw = if (showNsfw) ApiConstants.NSFW_ALSO else ApiConstants.SFW_ONLY
                ).await()
                when (response) {
                    is NetworkResponse.Success -> {
                        val nextOffset = if (response.body.data.isEmpty()) null else offset + limit
                        return LoadResult.Page(
                            data = response.body.data,
                            prevKey = if (offset == ApiConstants.API_START_OFFSET) null else offset - limit,
                            nextKey = nextOffset
                        )
                    }
                    is NetworkResponse.UnknownError -> {
                        return LoadResult.Error(
                            response.error
                        )
                    }
                    is NetworkResponse.ServerError -> {
                        return LoadResult.Error(
                            response.body?.getThrowable() ?: ApiError.DefaultError.getThrowable()
                        )
                    }
                    is NetworkResponse.NetworkError -> {
                        return LoadResult.Error(
                            response.error
                        )
                    }
                }
            }
        } catch (e: Exception) {
            e.printStackTrace()
            Log.d(TAG, e.message ?: String.emptyString)
            return LoadResult.Error(e)
        }
    }

    companion object {
        private const val TAG = "AnimeSeasonalDataSource"
    }
}