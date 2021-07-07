package com.sharkaboi.mediahub.common.extensions

import java.time.LocalDate
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

internal fun LocalDateTime.formatDateDMY(): String {
    val format = DateTimeFormatter.ofPattern("d MMM yyyy")
    return this.format(format)
}

internal fun LocalDate.formatDateDMY(): String {
    val format = DateTimeFormatter.ofPattern("d MMM yyyy")
    return this.format(format)
}
