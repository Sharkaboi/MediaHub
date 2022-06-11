package com.sharkaboi.mediahub.modules.manga_list.data

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.haroldadmin.cnradapter.NetworkResponse
import com.sharkaboi.mediahub.common.extensions.getCatchingPaging
import com.sharkaboi.mediahub.data.api.constants.ApiConstants
import com.sharkaboi.mediahub.data.api.enums.MangaStatus
import com.sharkaboi.mediahub.data.api.enums.UserMangaSortType
import com.sharkaboi.mediahub.data.api.models.ApiError
import com.sharkaboi.mediahub.data.api.models.usermanga.UserMangaListResponse
import com.sharkaboi.mediahub.data.api.retrofit.UserMangaService
import com.sharkaboi.mediahub.data.wrappers.MHError
import timber.log.Timber

class UserMangaListDataSource(
    private val userMangaService: UserMangaService,
    private val accessToken: String?,
    private val mangaStatus: MangaStatus,
    private val mangaSortType: UserMangaSortType = UserMangaSortType.list_updated_at,
    private val showNsfw: Boolean = false
) : PagingSource<Int, UserMangaListResponse.Data>() {

    /**
     *   prevKey == null -> first page
     *   nextKey == null -> last page
     *   both prevKey and nextKey null -> only one page
     */
    override fun getRefreshKey(state: PagingState<Int, UserMangaListResponse.Data>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            val anchorPage = state.closestPageToPosition(anchorPosition)
            anchorPage?.prevKey?.plus(ApiConstants.API_PAGE_LIMIT)
                ?: anchorPage?.nextKey?.minus(ApiConstants.API_PAGE_LIMIT)
        }
    }

    override suspend fun load(
        params: LoadParams<Int>
    ): LoadResult<Int, UserMangaListResponse.Data> = getCatchingPaging {
        Timber.d("params : ${params.key}")
        val offset = params.key ?: ApiConstants.API_START_OFFSET
        val limit = ApiConstants.API_PAGE_LIMIT
        if (accessToken == null) {
            return@getCatchingPaging LoadResult.Error(
                MHError.LoginExpiredError.getThrowable()
            )
        }
        val response = userMangaService.getMangaListOfUserAsync(
            authHeader = ApiConstants.BEARER_SEPARATOR + accessToken,
            status = getStatus(),
            offset = offset,
            limit = limit,
            sort = mangaSortType.name,
            nsfw = if (showNsfw) ApiConstants.NSFW_ALSO else ApiConstants.SFW_ONLY
        ).await()

        return@getCatchingPaging when (response) {
            is NetworkResponse.Success -> {
                val nextOffset = if (response.body.data.isEmpty()) null else offset + limit
                LoadResult.Page(
                    data = response.body.data,
                    prevKey = if (offset == ApiConstants.API_START_OFFSET) null else offset - limit,
                    nextKey = nextOffset
                )
            }
            is NetworkResponse.UnknownError -> {
                LoadResult.Error(response.error)
            }
            is NetworkResponse.ServerError -> {
                LoadResult.Error(
                    response.body?.getThrowable() ?: ApiError.DefaultError.getThrowable()
                )
            }
            is NetworkResponse.NetworkError -> {
                LoadResult.Error(response.error)
            }
        }
    }

    private fun getStatus(): String? = if (mangaStatus == MangaStatus.all) {
        null
    } else {
        mangaStatus.name
    }
}
