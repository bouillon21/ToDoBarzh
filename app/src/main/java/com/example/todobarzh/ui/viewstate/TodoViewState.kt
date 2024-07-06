package com.example.todobarzh.ui.viewstate

import com.example.todobarzh.domain.model.TodoItem

sealed class TodoViewState {

    data object Loading : TodoViewState()

    data class Loaded(
        val todoItems: List<TodoItem> = listOf(),
        val countCompleted: Int = 0,
        val isVisibleCompleteTodo: Boolean = true
    ) : TodoViewState()

    data class LoadingError(val throwable: BaseThrowable, val retry: () -> Unit) :
        TodoViewState()
}