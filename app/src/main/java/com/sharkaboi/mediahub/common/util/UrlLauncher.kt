package com.sharkaboi.mediahub.common.util

import android.app.Activity
import android.content.ActivityNotFoundException
import android.content.Intent
import android.net.Uri
import androidx.fragment.app.Fragment
import com.sharkaboi.mediahub.R
import com.sharkaboi.mediahub.common.extensions.showToast

fun Activity.openUrl(url: String) {
    try {
        startActivity(Intent(Intent.ACTION_VIEW, Uri.parse(url)))
        overridePendingTransition(R.anim.fade_in, R.anim.fade_out)
    } catch (e: ActivityNotFoundException) {
        showToast("No browser found in the phone")
    } catch (e: Exception) {
        showToast(e.message)
    }
}

fun Fragment.openUrl(url: String) {
    try {
        startActivity(Intent(Intent.ACTION_VIEW, Uri.parse(url)))
        activity?.overridePendingTransition(R.anim.fade_in, R.anim.fade_out)
    } catch (e: ActivityNotFoundException) {
        showToast("No browser found in the phone")
    } catch (e: Exception) {
        showToast(e.message)
    }
}

fun Activity.openShareChooser(content: String, shareHint: String = "Share here") {
    try {
        val sendIntent: Intent = Intent().apply {
            action = Intent.ACTION_SEND
            putExtra(Intent.EXTRA_TEXT, content)
            type = "text/plain"
        }
        val shareIntent = Intent.createChooser(sendIntent, shareHint)
        startActivity(shareIntent)
        overridePendingTransition(R.anim.fade_in, R.anim.fade_out)
    } catch (e: ActivityNotFoundException) {
        showToast("No browser found in the phone")
    } catch (e: Exception) {
        showToast(e.message)
    }
}

fun Fragment.openShareChooser(content: String, shareHint: String = "Share here") {
    try {
        val sendIntent: Intent = Intent().apply {
            action = Intent.ACTION_SEND
            putExtra(Intent.EXTRA_TEXT, content)
            type = "text/plain"
        }
        val shareIntent = Intent.createChooser(sendIntent, shareHint)
        startActivity(shareIntent)
        activity?.overridePendingTransition(R.anim.fade_in, R.anim.fade_out)
    } catch (e: ActivityNotFoundException) {
        showToast("No browser found in the phone")
    } catch (e: Exception) {
        showToast(e.message)
    }
}