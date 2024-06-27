package com.example.todobarzh.ui.components.common

import java.time.Instant
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.ZoneId
import java.time.format.DateTimeFormatter
import java.util.Locale

fun Long.toDate(): LocalDate {
    return LocalDateTime.ofInstant(
        Instant.ofEpochMilli(this), ZoneId.systemDefault()
    ).toLocalDate()
}

fun LocalDate.toFormatString(): String {
    return this.format(
        DateTimeFormatter.ofPattern(
            "dd MMM yyyy",
            Locale("ru")
        )
    )
}

fun LocalDate.toLong(): Long {
    return this.atStartOfDay(ZoneId.systemDefault()).toInstant().toEpochMilli()
}
