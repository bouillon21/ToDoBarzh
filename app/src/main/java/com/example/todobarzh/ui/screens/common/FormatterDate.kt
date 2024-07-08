package com.example.todobarzh.ui.screens.common

import java.text.DateFormat
import java.text.SimpleDateFormat
import java.util.Locale

fun Long.toDateString(): String {
    val formatter: DateFormat = SimpleDateFormat("dd MMMM yyyy", Locale.getDefault())
    return formatter.format(this)
}
