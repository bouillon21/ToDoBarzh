package com.example.todobarzh.ui.viewstate

import com.example.todobarzh.domain.model.TodoItem

data class TodoViewState(
    val todoItems: List<TodoItem>,

    val isVisibleCompleteTodo: Boolean
)