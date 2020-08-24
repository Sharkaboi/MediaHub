package com.cybershark.mediahub.util

import android.content.Context
import android.net.Uri
import android.widget.Toast
import com.cybershark.mediahub.R
import com.cybershark.mediahub.data.api.APIConstants

internal fun getStagingAuthURI(context: Context) = Uri.parse(
    "${APIConstants.TRAKT_STAGING_API_URL}${APIConstants.AUTHORIZE_API_URL_PART}" +
            "?response_type=${APIConstants.RESPONSE_TYPE_AUTH}&" +
            "client_id=${context.getString(R.string.TRAKT_API_CLIENT_ID)}&" +
            "redirect_uri=${APIConstants.DEEP_LINK_CALLBACK_URI}"
)

internal fun Context.shortToast(message: String) = Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
internal fun Context.longToast(message: String) = Toast.makeText(this, message, Toast.LENGTH_LONG).show()