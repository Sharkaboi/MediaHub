package com.sharkaboi.mediahub.common.extensions

import android.content.Context
import android.widget.Toast
import androidx.annotation.StringRes
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment

internal fun AppCompatActivity.showToast(message: String?, length: Int = Toast.LENGTH_SHORT) =
    Toast.makeText(this, message ?: String.emptyString, length).show()

internal fun Fragment.showToast(message: String?, length: Int = Toast.LENGTH_SHORT) =
    Toast.makeText(context, message ?: String.emptyString, length).show()

internal fun Fragment.showToast(@StringRes id: Int, length: Int = Toast.LENGTH_SHORT) =
    Toast.makeText(context, id, length).show()

internal fun Context.showToast(message: String?, length: Int = Toast.LENGTH_SHORT) =
    Toast.makeText(this, message ?: String.emptyString, length).show()
