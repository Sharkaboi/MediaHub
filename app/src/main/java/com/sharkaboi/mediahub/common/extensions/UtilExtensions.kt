package com.sharkaboi.mediahub.common.extensions

import android.content.Context
import android.graphics.Color
import android.text.Html
import android.text.Spanned
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.google.android.material.snackbar.Snackbar
import com.sharkaboi.mediahub.common.data.api.models.anime.AnimeByIDResponse
import java.text.DecimalFormat
import java.time.*
import java.time.format.DateTimeFormatter
import java.time.temporal.WeekFields
import java.util.*

internal fun AppCompatActivity.showToast(message: String?, length: Int = Toast.LENGTH_SHORT) =
    Toast.makeText(this, message ?: String.emptyString, length).show()

internal fun Fragment.showToast(message: String?, length: Int = Toast.LENGTH_SHORT) =
    Toast.makeText(context, message ?: String.emptyString, length).show()

internal fun Context.showToast(message: String?, length: Int = Toast.LENGTH_SHORT) =
    Toast.makeText(this, message ?: String.emptyString, length).show()
