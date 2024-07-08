package com.example.todobarzh.ui.screens.common

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.todobarzh.domain.model.BaseThrowable
import com.example.todobarzh.ui.getErrorDescription
import com.example.todobarzh.ui.theme.Blue
import com.example.todobarzh.ui.theme.ToDoBarzhTheme

@Composable
fun ErrorScreen(throwable: BaseThrowable, retry: ()->Unit) {
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
            Text(
                text = "Произошла ошибка:",
                style = ToDoBarzhTheme.typography.title,
                color = ToDoBarzhTheme.colorScheme.labelPrimary
            )
            Text(
                text = throwable.getErrorDescription ?: "",
                style = ToDoBarzhTheme.typography.body,
                color = ToDoBarzhTheme.colorScheme.labelSecondary
            )

            Button(
                onClick = { retry.invoke() },
                colors = ButtonDefaults.buttonColors().copy(containerColor = Blue),
                modifier = Modifier.padding(8.dp)
            ) {
                Text(
                    text = "Повторить попытку",
                    style = ToDoBarzhTheme.typography.button,
                )
            }
        }

    }
}