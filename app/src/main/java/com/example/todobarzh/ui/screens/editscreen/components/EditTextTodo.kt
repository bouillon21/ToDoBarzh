package com.example.todobarzh.ui.screens.editscreen.components

import android.content.res.Configuration
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.tooling.preview.PreviewParameterProvider
import androidx.compose.ui.unit.dp
import com.example.todobarzh.R
import com.example.todobarzh.ui.screens.editscreen.EditScreenEvent
import com.example.todobarzh.ui.theme.ToDoBarzhTheme

@Composable
fun EditTextTodo(text: String, onEvent: (EditScreenEvent) -> Unit, modifier: Modifier) {
    TextField(
        value = text,
        onValueChange = { onEvent.invoke(EditScreenEvent.UpdateEditText(it)) },
        placeholder = {
            Text(
                text = stringResource(R.string.placeholder_edit_view),
                color = ToDoBarzhTheme.colorScheme.labelSecondary
            )
        },
        colors = TextFieldDefaults.colors(
            unfocusedContainerColor = ToDoBarzhTheme.colorScheme.backSecondary,
            focusedContainerColor = ToDoBarzhTheme.colorScheme.backSecondary,
            unfocusedIndicatorColor = Color.Transparent,
            focusedIndicatorColor = Color.Transparent,
            cursorColor = ToDoBarzhTheme.colorScheme.labelTertiary,
            focusedTextColor = ToDoBarzhTheme.colorScheme.labelPrimary,
            unfocusedTextColor = ToDoBarzhTheme.colorScheme.labelPrimary
        ),
        shape = RoundedCornerShape(8.dp),
        textStyle = ToDoBarzhTheme.typography.body,
        modifier = modifier
            .fillMaxWidth()
            .defaultMinSize(minHeight = 104.dp)
    )
}

@Preview(
    name = "light",
    group = "color",
    uiMode = Configuration.UI_MODE_NIGHT_NO,
)
@Preview(
    name = "dark",
    group = "color",
    uiMode = Configuration.UI_MODE_NIGHT_YES,
)
@Composable
fun EditTextTodoPreview(@PreviewParameter(EditTextProviders::class) text: String) {
    ToDoBarzhTheme {
        EditTextTodo(text, {}, Modifier)
    }
}

private class EditTextProviders : PreviewParameterProvider<String> {
    override val values: Sequence<String>
        get() = sequenceOf(
            "",
            "Hello world!",
            "But I must explain to you how all this mistaken idea of denouncing" +
                    " pleasure and praising pain was born and I will give you a" +
                    " complete account of the system"
        )
}