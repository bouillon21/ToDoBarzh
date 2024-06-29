package com.example.todobarzh.ui.screens.editscreen.components

import android.content.res.Configuration
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.todobarzh.R
import com.example.todobarzh.domain.model.TodoPriority
import com.example.todobarzh.ui.screens.editscreen.EditScreenEvent
import com.example.todobarzh.ui.theme.ToDoBarzhTheme

@Composable
fun PrioritySpinner(
    important: TodoPriority,
    onEvent: (EditScreenEvent) -> Unit,
    modifier: Modifier
) {
    var expanded by remember { mutableStateOf(false) }

    Column(
        modifier
            .fillMaxWidth()
            .clickable { expanded = true }) {
        Text(
            text = stringResource(R.string.label_spinner_edit_view),
            style = ToDoBarzhTheme.typography.body,
            color = ToDoBarzhTheme.colorScheme.labelPrimary,
        )
        Spacer(Modifier.height(4.dp))
        Text(
            text = important.value,
            style = ToDoBarzhTheme.typography.subhead,
            color = ToDoBarzhTheme.colorScheme.labelTertiary,
        )

        DropdownMenu(
            modifier = Modifier
                .background(ToDoBarzhTheme.colorScheme.backSecondary),
            expanded = expanded,
            onDismissRequest = { expanded = false },
        ) {
            TodoPriority.entries.forEach { entry ->
                DropdownMenuItem(
                    onClick = {
                        onEvent.invoke(EditScreenEvent.UpdateImportance(entry))
                        expanded = false
                    },
                    text = {
                        Text(
                            text = entry.value,
                            color = ToDoBarzhTheme.colorScheme.labelPrimary,
                            style = ToDoBarzhTheme.typography.body,
                            modifier = Modifier
                                .wrapContentWidth()
                                .align(Alignment.Start)
                        )
                    }
                )
            }
        }
    }
}

@Preview(
    name = "light",
    group = "color",
    uiMode = Configuration.UI_MODE_NIGHT_NO,
)

@Composable
fun PrioritySpinnerPreview() {
    ToDoBarzhTheme {
        Surface(Modifier.background(color = ToDoBarzhTheme.colorScheme.backPrimary)) {
            PrioritySpinner(TodoPriority.USUAL, {}, Modifier)
        }
    }
}