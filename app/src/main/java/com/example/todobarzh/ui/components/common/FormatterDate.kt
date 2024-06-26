package com.example.todobarzh.ui.components.common

import android.annotation.SuppressLint
import java.text.SimpleDateFormat
import java.util.Locale

@SuppressLint("SimpleDateFormat")
val formatter = SimpleDateFormat("dd MMM yyyy", Locale("ru"))

fun Long.toDateText(): String {
    return formatter.format(this)
}