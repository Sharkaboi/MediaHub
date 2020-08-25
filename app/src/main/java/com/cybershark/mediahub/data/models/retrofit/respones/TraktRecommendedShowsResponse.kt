package com.cybershark.mediahub.data.models.retrofit.respones

import com.cybershark.mediahub.data.models.retrofit.media.show.TraktShowModel

data class TraktRecommendedShowsResponse(
    val user_count: Int,
    val show: TraktShowModel
)