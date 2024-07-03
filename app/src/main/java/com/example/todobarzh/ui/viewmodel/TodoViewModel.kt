package com.example.todobarzh.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.todobarzh.domain.repository.TodoItemsRepository
import com.example.todobarzh.ui.viewstate.TodoViewState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class TodoViewModel @Inject constructor(
    private val repository: TodoItemsRepository
) : ViewModel() {

    private val mutableViewState = MutableStateFlow(TodoViewState.Loaded(listOf(), 0, true))
    val viewState: StateFlow<TodoViewState> = mutableViewState.asStateFlow()

    private val isVisibleCompleteTodo = MutableStateFlow(true)

    private val countCompleted = MutableStateFlow(0)

    init {
        viewModelScope.launch {
            calculateCountComplete()
            repository.getTodoItems()
                .combine(isVisibleCompleteTodo) { todoItemList, doneTasksVisibility ->
                    todoItemList.filter { !it.isComplete or doneTasksVisibility }
                }.collect {
                    mutableViewState.emit(
                        TodoViewState.Loaded(
                            it,
                            countCompleted.value,
                            isVisibleCompleteTodo.value
                        )
                    )
                }
        }
    }

    private fun calculateCountComplete() {
        viewModelScope.launch {
            countCompleted.value = mutableViewState.value.todoItems.count { it.isComplete }
        }
    }

    fun onTodoCheckChangePressed(todoId: String, checked: Boolean) {
        viewModelScope.launch {
            repository.changeCheckTodo(todoId, checked)
            calculateCountComplete()
        }
    }

    fun onCompleteTodoVisibleChangePressed() {
        isVisibleCompleteTodo.update { !it }
    }
}