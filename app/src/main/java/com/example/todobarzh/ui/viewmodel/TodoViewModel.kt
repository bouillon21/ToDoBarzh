package com.example.todobarzh.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.todobarzh.domain.repository.TodoItemsRepository
import com.example.todobarzh.ui.viewstate.TodoViewState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.map
import javax.inject.Inject

@HiltViewModel
class TodoViewModel @Inject constructor(private val repository: TodoItemsRepository) : ViewModel() {

    private val mutableTodo = MutableStateFlow(TodoViewState(listOf(),true))
    val todo: SharedFlow<TodoViewState> = mutableTodo.asStateFlow()

    init {
        repository.getTodoItems().map {
            mutableTodo.emit(TodoViewState(it,true))
        }.launchIn(viewModelScope)
    }

    fun onTodoCheckChangePressed(todoId: String, checked: Boolean) {
        repository.changeCheckTodo(todoId, checked)
    }

    fun onCompleteTodoVisibleChangePressed() {
        mutableTodo.value =
            mutableTodo.value.copy(isVisibleCompleteTodo = !mutableTodo.value.isVisibleCompleteTodo)
    }

}