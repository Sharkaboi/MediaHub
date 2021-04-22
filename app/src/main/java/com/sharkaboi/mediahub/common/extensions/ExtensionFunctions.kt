package com.sharkaboi.mediahub.common.extensions

import android.content.Context
import android.graphics.Color
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.google.android.material.snackbar.BaseTransientBottomBar
import com.google.android.material.snackbar.Snackbar
import java.text.DecimalFormat
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

internal fun AppCompatActivity.showToast(message: String, length: Int = Toast.LENGTH_SHORT) =
    Toast.makeText(this, message, length).show()

internal fun Fragment.showToast(message: String, length: Int = Toast.LENGTH_SHORT) =
    Toast.makeText(context, message, length).show()

internal fun Context.showToast(message: String, length: Int = Toast.LENGTH_SHORT) =
    Toast.makeText(this, message, length).show()

internal fun View.shortSnackBar(
    message: String, action: (Snackbar.() -> Unit)? = null
) {
    val snackbar = Snackbar.make(this, message, Snackbar.LENGTH_SHORT)
    action?.let { snackbar.it() }
    snackbar.show()
}

internal fun View.longSnackBar(
    message: String, action: (Snackbar.() -> Unit)? = null
) {
    val snackbar = Snackbar.make(this, message, Snackbar.LENGTH_LONG)
    action?.let { snackbar.it() }
    snackbar.show()
}

internal fun View.indefiniteSnackBar(
    message: String,
    action: (Snackbar.() -> Unit)? = null
) {
    val snackbar = Snackbar.make(this, message, Snackbar.LENGTH_INDEFINITE)
    action?.let { snackbar.it() }
    snackbar.show()
}

internal fun Snackbar.action(message: String, action: (View) -> Unit) {
    this.setAction(message, action)
}

internal fun String.tryParseDateTime(): LocalDateTime? {
    return try {
        val format = DateTimeFormatter.ISO_DATE_TIME
        LocalDateTime.parse(this, format)
    } catch (e: Exception) {
        e.printStackTrace()
        null
    }
}

internal fun String.tryParseDate(): LocalDate? {
    return try {
        val format = DateTimeFormatter.ISO_DATE
        LocalDate.parse(this, format)
    } catch (e: Exception) {
        e.printStackTrace()
        null
    }
}

internal fun LocalDateTime.formatDate(): String {
    val format = DateTimeFormatter.ofPattern("d MMM yyyy")
    return this.format(format)
}

internal fun LocalDate.formatDate(): String {
    val format = DateTimeFormatter.ofPattern("d MMM yyyy")
    return this.format(format)
}

internal fun Double.roundOfString(): String {
    val format = DecimalFormat("#.##")
    return format.format(this)
}

fun String.parseRGB(): Int {
    val color = this.replace("#", "").toLong(16).toInt()
    val r = color shr 16 and 0xFF
    val g = color shr 8 and 0xFF
    val b = color shr 0 and 0xFF
    return Color.rgb(r, g, b)
}