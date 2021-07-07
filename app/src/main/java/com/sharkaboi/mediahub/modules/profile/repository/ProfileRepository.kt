package com.sharkaboi.mediahub.modules.profile.repository

import com.sharkaboi.mediahub.data.api.models.user.UserDetailsResponse
import com.sharkaboi.mediahub.data.wrappers.MHTaskState

interface ProfileRepository {

    suspend fun getUserDetails(): MHTaskState<UserDetailsResponse>
}
