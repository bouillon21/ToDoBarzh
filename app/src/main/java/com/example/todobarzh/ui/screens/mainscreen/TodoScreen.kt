package com.example.todobarzh.ui.screens.mainscreen

import android.content.res.Configuration
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LargeTopAppBar
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberTopAppBarState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.tooling.preview.PreviewParameterProvider
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.todobarzh.R
import com.example.todobarzh.domain.model.TodoItem
import com.example.todobarzh.domain.model.TodoPriority
import com.example.todobarzh.ui.navigation.TodoNavRoute
import com.example.todobarzh.ui.screens.common.ErrorScreen
import com.example.todobarzh.ui.screens.common.LoadingScreen
import com.example.todobarzh.ui.screens.common.getShadowTopAppBarModifier
import com.example.todobarzh.ui.screens.mainscreen.components.Todo
import com.example.todobarzh.ui.theme.Blue
import com.example.todobarzh.ui.theme.ToDoBarzhTheme
import com.example.todobarzh.ui.theme.White
import com.example.todobarzh.ui.viewmodel.TodoViewModel
import com.example.todobarzh.ui.viewstate.TodoViewState

private fun onEvent(
    action: MainScreenEvent, viewModel: TodoViewModel, navController: NavController
) {
    when (action) {
        MainScreenEvent.NewItemPressed -> {
            navController.navigate(TodoNavRoute.New.route)
        }

        is MainScreenEvent.TodoCheckChangePressed -> {
            viewModel.onTodoCheckChangePressed(action.todoId, action.checked)
        }

        is MainScreenEvent.TodoEditPressed -> {
            navController.navigate(TodoNavRoute.Edit.withArgs(action.todoId))
        }

        MainScreenEvent.CompleteTodoVisibleChangePressed ->
            viewModel.onCompleteTodoVisibleChangePressed()
    }
}

sealed interface MainScreenEvent {

    data object NewItemPressed : MainScreenEvent

    data class TodoCheckChangePressed(val todoId: String, val checked: Boolean) : MainScreenEvent

    data class TodoEditPressed(val todoId: String) : MainScreenEvent

    data object CompleteTodoVisibleChangePressed : MainScreenEvent
}

@Composable
fun MainScreen(navController: NavController, viewModel: TodoViewModel) {
    val state by viewModel.viewState.collectAsState()
    val onEvent =
        remember { { action: MainScreenEvent -> onEvent(action, viewModel, navController) } }

    MainScreenContent(state, onEvent)
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainScreenContent(viewState: TodoViewState, onEvent: (MainScreenEvent) -> Unit) {
    val appBarState = rememberTopAppBarState()
    val scrollBehavior = TopAppBarDefaults.exitUntilCollapsedScrollBehavior(appBarState)

    when (viewState) {
        is TodoViewState.Loaded -> {
            val stateCounterComplete = stringResource(
                R.string.completeTodoTemplate,
                viewState.countCompleted
            )
            val stateStyleTitle =
                if (appBarState.collapsedFraction < 0.5f) {
                    ToDoBarzhTheme.typography.largeTitle
                } else {
                    ToDoBarzhTheme.typography.title
                }
            Scaffold(
                topBar = {
                    LargeTopAppBar(
                        title = {
                            Column {
                                Text(
                                    stringResource(R.string.main_screen_title),
                                    style = stateStyleTitle,
                                    color = ToDoBarzhTheme.colorScheme.labelPrimary
                                )
                                if (appBarState.collapsedFraction < 0.5f) {
                                    Row(
                                        verticalAlignment = Alignment.CenterVertically,
                                        horizontalArrangement = Arrangement.SpaceBetween,
                                        modifier = Modifier.fillMaxWidth()
                                    ) {
                                        Text(
                                            text = stateCounterComplete,
                                            style = ToDoBarzhTheme.typography.body,
                                            color = ToDoBarzhTheme.colorScheme.labelTertiary,
                                        )
                                        EyeButton(
                                            viewState.isVisibleCompleteTodo,
                                            onEvent,
                                            Modifier.padding(end = 4.dp)
                                        )
                                    }
                                }
                            }
                        },
                        actions = {
                            AnimatedVisibility(appBarState.collapsedFraction > 0.5f) {
                                EyeButton(viewState.isVisibleCompleteTodo, onEvent, Modifier)
                            }
                        },
                        scrollBehavior = scrollBehavior,
                        colors = TopAppBarDefaults.topAppBarColors(
                            containerColor = ToDoBarzhTheme.colorScheme.backPrimary,
                            scrolledContainerColor = ToDoBarzhTheme.colorScheme.backSecondary,
                        ), modifier = getShadowTopAppBarModifier(appBarState)
                    )
                },
                floatingActionButton = {
                    FloatingActionButton(
                        onClick = {
                            onEvent(MainScreenEvent.NewItemPressed)
                        }, containerColor = Blue, contentColor = White
                    ) {
                        Icon(Icons.Default.Add, contentDescription = "Add")
                    }
                },
                containerColor = ToDoBarzhTheme.colorScheme.backPrimary,
                modifier = Modifier
                    .fillMaxSize()
                    .nestedScroll(scrollBehavior.nestedScrollConnection)
            ) { contentPadding ->
                Column(
                    Modifier
                        .padding(contentPadding)
                        .padding(horizontal = 8.dp)
                ) {
                    TodoList(viewState.todoItems, onEvent)
                }
            }
        }

        TodoViewState.Loading -> LoadingScreen()

        is TodoViewState.LoadingError -> ErrorScreen(viewState.throwable, viewState.retry)
    }
}

@Composable
fun EyeButton(
    isVisibleCompleteTodo: Boolean,
    onEvent: (MainScreenEvent) -> Unit,
    modifier: Modifier
) {
    val painterEye =
        if (isVisibleCompleteTodo) {
            painterResource(id = R.drawable.ic_eye)
        } else {
            painterResource(id = R.drawable.ic_eye_off)
        }

    IconButton(
        onClick = { onEvent(MainScreenEvent.CompleteTodoVisibleChangePressed) },
        modifier = modifier
    ) {
        Icon(
            painter = painterEye,
            tint = Blue,
            contentDescription = null,
        )
    }
}


@Composable
fun TodoList(todoItems: List<TodoItem>, onEvent: (MainScreenEvent) -> Unit) {
    Column(
        modifier = Modifier
            .background(
                ToDoBarzhTheme.colorScheme.backSecondary, shape = RoundedCornerShape(8.dp)
            )
            .padding(vertical = 8.dp)
    ) {
        LazyColumn {
            items(todoItems) { todo ->
                Todo(
                    todo = todo,
                    onClick = { todoId -> onEvent(MainScreenEvent.TodoEditPressed(todoId)) },
                    onClickCheckbox = { todoId, checked ->
                        onEvent(
                            MainScreenEvent.TodoCheckChangePressed(todoId, checked)
                        )
                    },
                )
            }
            items(1) {
                NewTodo(onEvent)
            }
        }
    }
}

@Composable
fun NewTodo(onEvent: (MainScreenEvent) -> Unit) {
    Column(Modifier.clickable {
        onEvent(MainScreenEvent.NewItemPressed)
    }) {
        Spacer(Modifier.height(12.dp))
        Row(
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Spacer(Modifier.width(40.dp))
            Text(
                color = ToDoBarzhTheme.colorScheme.labelPrimary,
                text = stringResource(R.string.add_new_todo_button),
                modifier = Modifier
                    .padding(horizontal = 12.dp)
                    .weight(1f),
                maxLines = 3,
                overflow = TextOverflow.Ellipsis,
            )
        }
        Spacer(Modifier.height(12.dp))
    }
}

@Preview(
    name = "light",
    group = "mainScreen",
    uiMode = Configuration.UI_MODE_NIGHT_NO
)
@Preview(
    name = "dark",
    group = "mainScreen",
    uiMode = Configuration.UI_MODE_NIGHT_YES or Configuration.UI_MODE_TYPE_NORMAL,
)
@Composable
fun MainScreenContentPreview(@PreviewParameter(TodoListProviders::class) todoItems: List<TodoItem>) {
    ToDoBarzhTheme {
        MainScreenContent(TodoViewState.Loaded(todoItems, 0, true), { _ -> })
    }
}


@Preview(
    name = "light",
    group = "todoList",
    uiMode = Configuration.UI_MODE_NIGHT_NO
)
@Preview(
    name = "dark",
    group = "todoList",
    uiMode = Configuration.UI_MODE_NIGHT_YES or Configuration.UI_MODE_TYPE_NORMAL,
)
@Composable
private fun TodoListPreview(@PreviewParameter(TodoListProviders::class) todoItems: List<TodoItem>) {
    ToDoBarzhTheme {
        TodoList(todoItems, { _ -> })
    }
}

private class TodoListProviders : PreviewParameterProvider<List<TodoItem>> {
    override val values: Sequence<List<TodoItem>>
        get() = sequenceOf(
            listOf(
                TodoItem(
                    "1",
                    "text = 1",
                    TodoPriority.BASIC,
                    null,
                    false,
                    System.currentTimeMillis(),
                    null
                ),
                TodoItem(
                    "2",
                    "But I must explain to you how all this mistaken idea of denouncing" +
                            " pleasure and praising pain was born and I will give you a" +
                            " complete account of the system",
                    TodoPriority.BASIC,
                    null,
                    false,
                    System.currentTimeMillis(),
                    null
                ),
                TodoItem(
                    "3",
                    "text = 3",
                    TodoPriority.BASIC,
                    null,
                    true,
                    System.currentTimeMillis(),
                    null
                )
            )
        )
}