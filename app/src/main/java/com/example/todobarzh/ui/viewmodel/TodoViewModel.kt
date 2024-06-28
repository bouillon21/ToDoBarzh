package com.example.todobarzh.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.todobarzh.domain.repository.TodoItemsRepository
import com.example.todobarzh.ui.viewstate.TodoViewState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class TodoViewModel @Inject constructor(
    private val repository: TodoItemsRepository
) : ViewModel() {

    private val mutableViewState = MutableStateFlow(TodoViewState.Loaded(listOf(), true))
    val viewState: StateFlow<TodoViewState> = mutableViewState.asStateFlow()

    init {
        repository.getTodoItems().map {
            mutableViewState.emit(TodoViewState.Loaded(it, true))
        }.launchIn(viewModelScope)
    }

    fun onTodoCheckChangePressed(todoId: String, checked: Boolean) {
        viewModelScope.launch {
            repository.changeCheckTodo(todoId, checked)
        }
    }

    fun onCompleteTodoVisibleChangePressed() {
        mutableViewState.update {
            mutableViewState.value.copy(isVisibleCompleteTodo = !it.isVisibleCompleteTodo)
        }
    }
}