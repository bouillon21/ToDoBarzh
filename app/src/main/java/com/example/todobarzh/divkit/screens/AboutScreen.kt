package com.example.todobarzh.divkit.screens

import android.view.View
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.viewinterop.AndroidView
import com.example.todobarzh.ui.theme.BackPrimaryDark

@Composable
fun AboutScreen(
    aboutScreen: View,
) {
    Row(
        modifier = Modifier
            .fillMaxSize()
            .background(BackPrimaryDark)
    ) {
        AndroidView(factory = { aboutScreen })
    }
}