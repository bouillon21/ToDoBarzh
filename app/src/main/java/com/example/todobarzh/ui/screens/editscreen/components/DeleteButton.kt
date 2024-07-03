package com.example.todobarzh.ui.screens.editscreen.components

import android.content.res.Configuration
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Icon
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import com.example.todobarzh.R
import com.example.todobarzh.ui.screens.editscreen.EditScreenEvent
import com.example.todobarzh.ui.theme.Red
import com.example.todobarzh.ui.theme.ToDoBarzhTheme

@Composable
fun DeleteButton(onEvent: (EditScreenEvent) -> Unit, modifier: Modifier) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = modifier
            .fillMaxWidth()
            .clickable { onEvent.invoke(EditScreenEvent.Delete) }
    ) {
        Icon(
            painter = painterResource(id = R.drawable.ic_trash),
            contentDescription = "delete",
            tint = Red
        )
        Text(
            stringResource(R.string.delete_button),
            style = ToDoBarzhTheme.typography.body,
            color = Red
        )
    }
}

@Preview(
    name = "light",
    group = "color",
    uiMode = Configuration.UI_MODE_NIGHT_NO,
)
@Composable
fun DeleteButtonPreview() {
    ToDoBarzhTheme {
        Surface(Modifier.background(color = ToDoBarzhTheme.colorScheme.backPrimary)) {
            DeleteButton({}, Modifier)
        }
    }
}