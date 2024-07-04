package com.example.todobarzh.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.todobarzh.domain.repository.TodoItemsRepository
import com.example.todobarzh.ui.viewstate.TodoViewState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class TodoViewModel @Inject constructor(
    private val repository: TodoItemsRepository
) : ViewModel() {
    private val mutableViewState = MutableStateFlow(TodoViewState.Loaded())
    private val isVisibleCompleteTodo = MutableStateFlow(true)

    val viewState = combine(
        mutableViewState,
        repository.todoItems,
        isVisibleCompleteTodo
    ) { state, tasks, isVisible ->
        state.copy(
            todoItems = tasks.filter { !it.isComplete or isVisible },
            countCompleted = repository.getCountCompleteTodo(),
            isVisibleCompleteTodo = isVisible,
        )
    }.stateIn(
        viewModelScope,
        SharingStarted.WhileSubscribed(5000),
        TodoViewState.Loaded()
    )

    fun onTodoCheckChangePressed(todoId: String, checked: Boolean) {
        viewModelScope.launch {
            val item = repository.findTodoItemById(todoId).copy(isComplete = checked)
            repository.changeCheckTodo(item)
        }
    }

    fun onCompleteTodoVisibleChangePressed() {
        isVisibleCompleteTodo.update { !it }
    }
}