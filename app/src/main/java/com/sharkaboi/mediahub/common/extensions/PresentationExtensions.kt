package com.sharkaboi.mediahub.common.extensions

import android.content.Context
import androidx.annotation.StringRes
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.sharkaboi.mediahub.R

internal fun Context.getProgressStringWith(progress: Int?, total: Int?): String {
    val totalCountString = if (total == null || total == 0)
        getString(R.string.no_count_fount_marker)
    else
        total.toString()
    val progressCount = progress ?: 0
    return getString(
        R.string.media_progress_template,
        progressCount,
        totalCountString
    )
}

internal fun Context.getMediaTypeStringWith(type: String): String {
    return getString(
        R.string.media_type_template,
        type.uppercase()
    )
}

internal fun Context.getRatingStringWithRating(rating: Number?): String {
    return resources.getQuantityString(
        R.plurals.rating_hint,
        rating?.toInt() ?: 0,
        rating?.toFloat() ?: 0F
    )
}

internal fun Context.showNoActionOkDialog(@StringRes title: Int, content: CharSequence?) {
    MaterialAlertDialogBuilder(this)
        .setTitle(title)
        .setMessage(
            content.ifNullOrBlank { getString(R.string.n_a) }
        ).setPositiveButton(R.string.ok) { dialog, _ ->
            dialog.dismiss()
        }.show()
}
