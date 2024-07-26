package com.example.todobarzh.ui.screens.settingsscreen.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.clearAndSetSemantics
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.todobarzh.R
import com.example.todobarzh.ui.theme.ToDoBarzhTheme

@Composable
fun AboutUiItem(
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    val clickable = remember {
        mutableStateOf(true)
    }

    val description = stringResource(R.string.info_button_description)
    Box(
        modifier = modifier
            .clickable(enabled = clickable.value) {
                onClick()
                clickable.value = false
            }
            .padding(top = 16.dp, bottom = 16.dp)
            .semantics { contentDescription = description }
            .fillMaxWidth()

    ) {
        Text(
            text = "Об авторе",
            color = ToDoBarzhTheme.colorScheme.labelPrimary,
            style = ToDoBarzhTheme.typography.body,
            modifier = Modifier
                .align(Alignment.CenterStart)
                .clearAndSetSemantics { }
        )
        Icon(
            modifier = Modifier
                .align(Alignment.CenterEnd)
                .size(24.dp),
            painter = painterResource(R.drawable.ic_info),
            contentDescription = null,
            tint = ToDoBarzhTheme.colorScheme.labelTertiary
        )
    }
}

@Preview
@Composable
private fun AboutUiItemPreview() {
    ToDoBarzhTheme {
        AboutUiItem({})
    }
}