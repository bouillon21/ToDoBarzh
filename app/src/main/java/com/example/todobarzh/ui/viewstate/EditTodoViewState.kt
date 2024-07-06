package com.example.todobarzh.ui.viewstate

import com.example.todobarzh.domain.model.BaseThrowable
import com.example.todobarzh.domain.model.TodoItem

sealed class EditTodoViewState {

    data object Loading : EditTodoViewState()

    data class Loaded(val todoItem: TodoItem) : EditTodoViewState()

    data class LoadingError(val throwable: BaseThrowable, val retry: () -> Unit) :
        EditTodoViewState()
}
