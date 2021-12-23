package com.sharkaboi.mediahub.modules.profile.util

import com.github.mikephil.charting.formatter.ValueFormatter
import com.sharkaboi.mediahub.common.extensions.emptyString
import java.text.DecimalFormat

class MPAndroidChartValueFormatter : ValueFormatter() {

    /**
     * Blank -> Less than 1000 (Exact)
     * k -> 1k, 5.5k (Round off)
     * m -> 1m, 1.8m (Round off)
     * b -> 1b, 1.8b (Round off)
     * t -> 1t, 1.8t (Round off)
     */
    private val suffixes = listOf("", "k", "m", "b", "t")

    override fun getFormattedValue(value: Float): String {
        return format(value.toDouble())
    }

    private fun format(number: Double): String {
        if (number == 0.0) {
            return String.emptyString
        }
        val exponentFormatter = DecimalFormat("###E00")
        val formattedValue = exponentFormatter.format(number)
        val base = formattedValue.dropLast(3).toLong()
        val exponent = formattedValue.takeLast(2).toInt()
        val currentSuffix = suffixes[exponent / 3]
        return "$base$currentSuffix"
    }
}
