package com.cybershark.mediahub.data.models.retrofit.respones

import com.cybershark.mediahub.data.models.retrofit.media.show.TraktShowModel

data class TraktTrendingShowsResponse(
    val watchers: Int,
    val show: TraktShowModel
)