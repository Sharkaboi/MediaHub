package com.sharkaboi.mediahub.common.util

import android.app.Activity
import android.content.ActivityNotFoundException
import android.content.Intent
import android.content.pm.PackageManager
import androidx.core.net.toUri
import androidx.fragment.app.Fragment
import com.sharkaboi.mediahub.R
import com.sharkaboi.mediahub.common.constants.AppConstants
import com.sharkaboi.mediahub.common.extensions.showToast
import timber.log.Timber

fun Activity.openUrl(url: String) {
    try {
        startActivity(Intent(Intent.ACTION_VIEW, url.toUri()))
        overridePendingTransition(R.anim.fade_in, R.anim.fade_out)
    } catch (e: ActivityNotFoundException) {
        showToast(getString(R.string.no_browser_found_hint))
    } catch (e: Exception) {
        showToast(e.message)
    }
}

fun Fragment.openUrl(url: String) {
    try {
        startActivity(Intent(Intent.ACTION_VIEW, url.toUri()))
        activity?.overridePendingTransition(R.anim.fade_in, R.anim.fade_out)
    } catch (e: ActivityNotFoundException) {
        showToast(getString(R.string.no_browser_found_hint))
    } catch (e: Exception) {
        showToast(e.message)
    }
}

fun Fragment.openShareChooser(content: String, shareHint: String = getString(R.string.share_hint)) {
    try {
        val sendIntent: Intent = Intent().apply {
            action = Intent.ACTION_SEND
            putExtra(Intent.EXTRA_TEXT, content)
            setTypeAndNormalize("text/plain")
        }
        val shareIntent = Intent.createChooser(sendIntent, shareHint)
        startActivity(shareIntent)
        activity?.overridePendingTransition(R.anim.fade_in, R.anim.fade_out)
    } catch (e: ActivityNotFoundException) {
        showToast(getString(R.string.no_browser_found_hint))
    } catch (e: Exception) {
        showToast(e.message)
    }
}

/**
 * Uses package manager to get all activities that can open a non-deeplinked https uri.
 * Then forces the url specified to be opened by the first activity among the possible activities.
 * Excludes Mediahub's package name and sorts by default to get most optimal browser.
 */
fun Activity.forceLaunchInBrowser(url: String) {
    try {
        val browserIntent = Intent(
            Intent.ACTION_VIEW,
            AppConstants.nonDeepLinkedUrl.toUri()
        )
        val browsersList = packageManager.queryIntentActivities(
            browserIntent, PackageManager.MATCH_DEFAULT_ONLY
        )
        Timber.d(browsersList.toString())
        val filteredList = browsersList.filter {
            it.activityInfo.packageName != this.packageName
        }
        val targetedBrowserIntent = Intent(Intent.ACTION_VIEW, url.toUri()).apply {
            setPackage(filteredList.first().activityInfo.packageName)
        }
        startActivity(targetedBrowserIntent)
        overridePendingTransition(R.anim.fade_in, R.anim.fade_out)
    } catch (e: NoSuchElementException) {
        showToast(getString(R.string.no_browser_found_hint))
    } catch (e: ActivityNotFoundException) {
        showToast(getString(R.string.no_browser_found_hint))
    } catch (e: Exception) {
        e.printStackTrace()
        showToast(e.message)
    }
}
