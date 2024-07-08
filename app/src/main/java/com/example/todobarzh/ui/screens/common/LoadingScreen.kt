package com.example.todobarzh.ui.screens.common

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.todobarzh.ui.theme.Blue
import com.example.todobarzh.ui.theme.ToDoBarzhTheme

@Composable
fun LoadingScreen() {
    Scaffold(
        containerColor = ToDoBarzhTheme.colorScheme.backPrimary,
    ) { contentPadding ->
        Column(
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .padding(contentPadding)
                .fillMaxSize(),
        ) {
            CircularProgressIndicator(
                modifier = Modifier.width(64.dp),
                color = Blue.copy(0.7f),
                trackColor = Blue,
            )
        }
    }
}