package com.example.todobarzh.ui.components.common

import android.content.res.Configuration
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Checkbox
import androidx.compose.material3.CheckboxDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.tooling.preview.PreviewParameterProvider
import androidx.compose.ui.unit.dp
import com.example.todobarzh.R
import com.example.todobarzh.data.model.TodoItem
import com.example.todobarzh.data.model.TodoPriorityEnum
import com.example.todobarzh.ui.theme.Green
import com.example.todobarzh.ui.theme.ToDoBarzhTheme
import java.util.Date

@Composable
fun Todo(
    todo: TodoItem,
    onClick: (todoId: String) -> Unit,
    onClickCheckbox: (todoId: String, checked: Boolean) -> Unit
) {
    var checked: Boolean by remember(todo.hashCode()) { mutableStateOf(todo.flagComplete) }

    val textStyle = if (checked) {
        ToDoBarzhTheme.typography.body.copy(textDecoration = TextDecoration.LineThrough)
    } else {
        ToDoBarzhTheme.typography.body
    }

    val textColor = if (checked) {
        ToDoBarzhTheme.colorScheme.labelTertiary
    } else {
        ToDoBarzhTheme.colorScheme.labelPrimary
    }

    Column(
        Modifier.clickable {
            onClick.invoke(todo.id)
        }
    ) {
        Spacer(Modifier.height(12.dp))
        Row(
            verticalAlignment = Alignment.Top,
        ) {
            Spacer(Modifier.width(16.dp))
            Checkbox(
                checked = checked,
                onCheckedChange = {
                    checked = it
                    onClickCheckbox.invoke(todo.id, it)
                },
                colors = CheckboxDefaults.colors(
                    checkedColor = Green,
                    uncheckedColor = ToDoBarzhTheme.colorScheme.labelTertiary
                ),
                modifier = Modifier.size(24.dp),
            )
            Text(
                text = todo.text,
                color = textColor,
                style = textStyle,
                maxLines = 3,
                overflow = TextOverflow.Ellipsis,
                modifier = Modifier
                    .padding(horizontal = 12.dp)
                    .weight(1f)
                    .align(Alignment.CenterVertically),
            )
            Icon(
                painter = painterResource(R.drawable.ic_info),
                tint = ToDoBarzhTheme.colorScheme.labelTertiary,
                contentDescription = null,
                modifier = Modifier.size(24.dp),
            )
            Spacer(Modifier.width(16.dp))
        }
        Spacer(Modifier.height(12.dp))
    }
}

@Preview(
    name = "light",
    group = "todo",
    uiMode = Configuration.UI_MODE_NIGHT_NO
)
@Preview(
    name = "dark",
    group = "todo",
    uiMode = Configuration.UI_MODE_NIGHT_YES or Configuration.UI_MODE_TYPE_NORMAL,
)
@Composable
private fun TodoPreview(@PreviewParameter(TodoProviders::class) todo: TodoItem) {
    ToDoBarzhTheme {
        Surface(color = ToDoBarzhTheme.colorScheme.backPrimary) {
            Todo(todo, { _ -> }, { _, _ -> })
        }
    }
}

private class TodoProviders : PreviewParameterProvider<TodoItem> {
    override val values: Sequence<TodoItem>
        get() = sequenceOf(
            TodoItem(
                "1",
                "text = 1",
                TodoPriorityEnum.USUAL,
                null,
                false,
                Date(),
                null
            ),
            TodoItem(
                "1",
                "But I must explain to you how all this mistaken idea of denouncing" +
                        " pleasure and praising pain was born and I will give you a" +
                        " complete account of the system",
                TodoPriorityEnum.USUAL,
                null,
                true,
                Date(),
                null
            ),
        )
}