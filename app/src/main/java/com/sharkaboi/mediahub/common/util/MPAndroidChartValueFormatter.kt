package com.sharkaboi.mediahub.common.util

import com.github.mikephil.charting.formatter.ValueFormatter
import java.text.DecimalFormat

class MPAndroidChartValueFormatter : ValueFormatter() {

    private var mSuffix = arrayOf(
        "", "k", "m", "b", "t"
    )
    private var mMaxLength = 5
    private var mFormat: DecimalFormat = DecimalFormat("###E00")

    override fun getFormattedValue(value: Float): String {
        return makePretty(value.toDouble())
    }

    private fun makePretty(number: Double): String {
        if (number == 0.0) {
            return ""
        }
        var r = mFormat.format(number)
        val numericValue1 = Character.getNumericValue(r[r.length - 1])
        val numericValue2 = Character.getNumericValue(r[r.length - 2])
        val combined = Integer.valueOf(numericValue2.toString() + "" + numericValue1)
        r = r.replace("E[0-9][0-9]".toRegex(), mSuffix[combined / 3])
        while (r.length > mMaxLength || r.matches(Regex("[0-9]+\\.[a-z]"))) {
            r = r.substring(0, r.length - 2) + r.substring(r.length - 1)
        }
        return r
    }
}
