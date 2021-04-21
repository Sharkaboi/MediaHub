package com.sharkaboi.mediahub.common.paging

import android.util.Log
import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.haroldadmin.cnradapter.NetworkResponse
import com.sharkaboi.mediahub.common.data.api.ApiConstants
import com.sharkaboi.mediahub.common.data.api.enums.AnimeStatus
import com.sharkaboi.mediahub.common.data.api.enums.UserAnimeSortType
import com.sharkaboi.mediahub.common.data.api.models.ApiError
import com.sharkaboi.mediahub.common.data.api.models.useranime.UserAnimeListResponse
import com.sharkaboi.mediahub.common.data.retrofit.UserAnimeService
import com.sharkaboi.mediahub.common.data.wrappers.NoTokenFoundError
import com.sharkaboi.mediahub.common.extensions.emptyString

class UserAnimeListDataSource(
    private val userAnimeService: UserAnimeService,
    private val accessToken: String?,
    private val animeStatus: AnimeStatus,
    private val animeSortType: UserAnimeSortType = UserAnimeSortType.list_updated_at
) : PagingSource<Int, UserAnimeListResponse.Data>() {

    override fun getRefreshKey(state: PagingState<Int, UserAnimeListResponse.Data>): Int? {
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

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, UserAnimeListResponse.Data> {
        Log.d(TAG, "params : ${params.key}")
        try {
            val offset = params.key ?: ApiConstants.API_START_OFFSET
            val limit = ApiConstants.API_PAGE_LIMIT
            if (accessToken == null) {
                return LoadResult.Error(
                    NoTokenFoundError.getThrowable()
                )
            } else {
                val response = userAnimeService.getAnimeListOfUserAsync(
                    authHeader = ApiConstants.BEARER_SEPARATOR + accessToken,
                    status = getStatus(),
                    offset = offset,
                    limit = limit,
                    sort = animeSortType.name
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

    private fun getStatus(): String? = if (animeStatus == AnimeStatus.all) {
        null
    } else {
        animeStatus.name
    }

    companion object {
        private const val TAG = "UserAnimeListDataSource"
    }
}