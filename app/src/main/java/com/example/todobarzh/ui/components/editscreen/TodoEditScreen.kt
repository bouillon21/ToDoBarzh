package com.example.todobarzh.ui.components.editscreen

import android.content.res.Configuration
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Switch
import androidx.compose.material3.SwitchDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberTopAppBarState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableLongStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.todobarzh.R
import com.example.todobarzh.data.model.TodoPriorityEnum
import com.example.todobarzh.ui.components.common.TodoDatePicker
import com.example.todobarzh.ui.components.common.toDateText
import com.example.todobarzh.ui.theme.Blue
import com.example.todobarzh.ui.theme.Red
import com.example.todobarzh.ui.theme.ToDoBarzhTheme
import com.example.todobarzh.ui.viewmodel.TodoEditViewModel
import java.util.Date

private fun onEvent(action: EditScreenEvent, viewModel: TodoEditViewModel) {
    when (action) {
        EditScreenEvent.Save -> TODO()
        EditScreenEvent.Delete -> TODO()
        EditScreenEvent.Exit -> TODO()
    }
}

sealed interface EditScreenEvent {

    data object Save : EditScreenEvent

    data object Exit : EditScreenEvent

    data object Delete : EditScreenEvent
}

@Composable
fun EditScreen(viewModel: TodoEditViewModel) {

    val onEvent = remember { { action: EditScreenEvent -> onEvent(action, viewModel) } }

    EditScreenContent(onEvent)
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EditScreenContent(onEvent: (EditScreenEvent) -> Unit) {
    val scrollBehavior = TopAppBarDefaults.pinnedScrollBehavior(rememberTopAppBarState())

    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = { },
                navigationIcon = {
                    IconButton(onClick = { onEvent.invoke(EditScreenEvent.Exit) }) {
                        Icon(
                            painter = painterResource(id = R.drawable.ic_cross),
                            tint = ToDoBarzhTheme.colorScheme.labelPrimary,
                            contentDescription = "exit"
                        )
                    }
                },
                actions = {
                    TextButton(onClick = { onEvent.invoke(EditScreenEvent.Save) }) {
                        Text(
                            text = stringResource(R.string.save_button_title),
                            style = ToDoBarzhTheme.typography.button,
                            color = Blue
                        )
                    }
                },
                colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
                    containerColor = ToDoBarzhTheme.colorScheme.backPrimary,
                    scrolledContainerColor = ToDoBarzhTheme.colorScheme.backSecondary
                ),
                scrollBehavior = scrollBehavior,
            )
        },
        containerColor = ToDoBarzhTheme.colorScheme.backPrimary,
        modifier = Modifier
            .nestedScroll(scrollBehavior.nestedScrollConnection)
            .fillMaxWidth()
    ) { contentPadding ->
        Column(
            Modifier
                .padding(contentPadding)
                .fillMaxWidth()
                .nestedScroll(scrollBehavior.nestedScrollConnection)
        ) {
            EditTextTodo(Modifier.padding(16.dp))
            PrioritySpinner(Modifier.padding(16.dp))
            HorizontalDivider(
                thickness = 1.dp,
                color = ToDoBarzhTheme.colorScheme.supportSeparator,
                modifier = Modifier.padding(horizontal = 16.dp)
            )
            DeadlineTodoSwitcher(Modifier.padding(16.dp))
            HorizontalDivider(
                thickness = 1.dp,
                color = ToDoBarzhTheme.colorScheme.supportSeparator
            )
            DeleteButton(Modifier.padding(16.dp), onEvent)
        }
    }
}

@Composable
fun EditTextTodo(modifier: Modifier) {
    var textInput by remember { mutableStateOf("") }
    TextField(
        value = textInput,
        onValueChange = { textInput = it },
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
        ),
        shape = RoundedCornerShape(8.dp),
        textStyle = ToDoBarzhTheme.typography.body,
        modifier = modifier
            .fillMaxWidth()
            .defaultMinSize(minHeight = 104.dp)
    )
}

@Composable
fun PrioritySpinner(modifier: Modifier) {

    var expanded by remember { mutableStateOf(false) }
    var important by remember { mutableStateOf(TodoPriorityEnum.LOW) }

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
            TodoPriorityEnum.entries.forEach { entry ->
                DropdownMenuItem(
                    onClick = {
                        important = entry
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


@Composable
fun DeadlineTodoSwitcher(modifier: Modifier) {
    var checked: Boolean by remember { mutableStateOf(false) }
    var datePickerExpanded by remember { mutableStateOf(false) }
    var deadlineDate by remember { mutableLongStateOf(Date().time) }
    var deadlineDateText by remember { mutableStateOf(deadlineDate.toDateText()) }

    Row(modifier) {
        Column(
            Modifier
                .align(Alignment.CenterVertically)
                .clickable { datePickerExpanded = checked }
        ) {
            Text(
                stringResource(R.string.deadline_title),
                style = ToDoBarzhTheme.typography.body,
                color = ToDoBarzhTheme.colorScheme.labelPrimary
            )
            if (checked) {
                Spacer(Modifier.height(4.dp))
                Text(
                    text = deadlineDateText,
                    style = ToDoBarzhTheme.typography.button,
                    color = Blue
                )
            }
        }
        Spacer(Modifier.weight(1f))
        Switch(
            checked = checked,
            onCheckedChange = { checked = it },
            colors = SwitchDefaults.colors(
                checkedTrackColor = Blue,
                uncheckedTrackColor = ToDoBarzhTheme.colorScheme.supportOverlay,
                uncheckedBorderColor = ToDoBarzhTheme.colorScheme.supportSeparator,
                uncheckedThumbColor = ToDoBarzhTheme.colorScheme.supportSeparator
            )
        )
    }
    if (datePickerExpanded) {
        TodoDatePicker(
            date = deadlineDate,
            onConfirm = {
                datePickerExpanded = !datePickerExpanded
                deadlineDate = it
                deadlineDateText = it.toDateText()
            },
            onDismiss = { datePickerExpanded = !datePickerExpanded }
        )
    }
}

@Composable
fun DeleteButton(modifier: Modifier, onEvent: (EditScreenEvent) -> Unit) {
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
    group = "todoEdit",
    uiMode = Configuration.UI_MODE_NIGHT_NO
)
@Preview(
    name = "dark",
    group = "todoEdit",
    uiMode = Configuration.UI_MODE_NIGHT_YES,
)
@Composable
private fun EditScreenContentPreview() {
    ToDoBarzhTheme {
        EditScreenContent({ _ -> })
    }
}