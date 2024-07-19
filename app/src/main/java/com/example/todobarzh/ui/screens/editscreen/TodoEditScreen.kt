package com.example.todobarzh.ui.screens.editscreen

import android.content.res.Configuration
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberTopAppBarState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.todobarzh.R
import com.example.todobarzh.domain.model.TodoPriority
import com.example.todobarzh.domain.model.emptyTodoItem
import com.example.todobarzh.ui.screens.common.ErrorScreen
import com.example.todobarzh.ui.screens.common.LoadingScreen
import com.example.todobarzh.ui.screens.common.getShadowTopAppBarModifier
import com.example.todobarzh.ui.screens.editscreen.components.BottomSheet
import com.example.todobarzh.ui.screens.editscreen.components.DeadlineTodoSwitcher
import com.example.todobarzh.ui.screens.editscreen.components.DeleteButton
import com.example.todobarzh.ui.screens.editscreen.components.EditTextTodo
import com.example.todobarzh.ui.theme.Blue
import com.example.todobarzh.ui.theme.ToDoBarzhTheme
import com.example.todobarzh.ui.viewmodel.TodoEditViewModel
import com.example.todobarzh.ui.viewstate.EditTodoViewState

private fun onEvent(
    action: EditScreenEvent,
    viewModel: TodoEditViewModel,
    navController: NavController
) {
    when (action) {
        is EditScreenEvent.Save -> {
            viewModel.saveTodo()
            navController.popBackStack()
        }

        is EditScreenEvent.Delete -> {
            viewModel.removeTodo()
            navController.popBackStack()
        }

        is EditScreenEvent.UpdateEditText -> viewModel.updateEditText(action.text)

        is EditScreenEvent.UpdateImportance -> viewModel.updateImportance(action.important)

        is EditScreenEvent.UpdateDate -> viewModel.updateDate(action.date)

        is EditScreenEvent.Exit -> navController.popBackStack()
    }
}

sealed interface EditScreenEvent {

    data class Save(val todo: EditTodoViewState) : EditScreenEvent

    data object Exit : EditScreenEvent

    data object Delete : EditScreenEvent

    data class UpdateEditText(val text: String) : EditScreenEvent

    data class UpdateImportance(val important: TodoPriority) : EditScreenEvent

    data class UpdateDate(val date: Long?) : EditScreenEvent
}

object EditScreenArg {
    const val TODO_ID = "todoId"
}

@Composable
fun EditScreen(navController: NavController, viewModel: TodoEditViewModel) {
    val state by viewModel.viewState.collectAsState()
    val onEvent =
        remember { { action: EditScreenEvent -> onEvent(action, viewModel, navController) } }

    EditScreenContent(state, onEvent)
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EditScreenContent(viewState: EditTodoViewState, onEvent: (EditScreenEvent) -> Unit) {
    val appBarState = rememberTopAppBarState()
    val scrollBehavior = TopAppBarDefaults.pinnedScrollBehavior(appBarState)

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
                    TextButton(onClick = { onEvent.invoke(EditScreenEvent.Save(viewState)) }) {
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
                modifier = getShadowTopAppBarModifier(appBarState)
            )
        },
        containerColor = ToDoBarzhTheme.colorScheme.backPrimary,
        modifier = Modifier
            .nestedScroll(scrollBehavior.nestedScrollConnection)
            .fillMaxWidth()
    ) { contentPadding ->

        when (viewState) {
            is EditTodoViewState.Loaded -> {
                Column(
                    Modifier
                        .padding(contentPadding)
                        .fillMaxWidth()
                        .nestedScroll(scrollBehavior.nestedScrollConnection)
                ) {
                    EditTextTodo(viewState.todoItem.text, onEvent, Modifier.padding(16.dp))
                    BottomSheet(viewState.todoItem.importance, onEvent, Modifier.padding(16.dp))
                    HorizontalDivider(
                        thickness = 1.dp,
                        color = ToDoBarzhTheme.colorScheme.supportSeparator,
                        modifier = Modifier.padding(horizontal = 16.dp)
                    )
                    DeadlineTodoSwitcher(
                        viewState.todoItem.deadline,
                        onEvent,
                        Modifier.padding(16.dp)
                    )
                    HorizontalDivider(
                        thickness = 1.dp,
                        color = ToDoBarzhTheme.colorScheme.supportSeparator
                    )
                    DeleteButton(onEvent, Modifier.padding(16.dp))
                }
            }

            EditTodoViewState.Loading -> LoadingScreen()

            is EditTodoViewState.LoadingError -> ErrorScreen(
                throwable = viewState.throwable,
                viewState.retry
            )
        }
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
        EditScreenContent(EditTodoViewState.Loaded(emptyTodoItem()), { _ -> })
    }
}