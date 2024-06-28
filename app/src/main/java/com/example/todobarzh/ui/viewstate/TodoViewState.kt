package com.example.todobarzh.ui.viewstate

import com.example.todobarzh.domain.model.TodoItem

sealed class TodoViewState {

    data object Loading : TodoViewState()

    data class Loaded(
        val todoItems: List<TodoItem>,
        val isVisibleCompleteTodo: Boolean
    ) : TodoViewState()

    data object LoadingError : TodoViewState()
}