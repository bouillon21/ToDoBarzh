package com.example.todobarzh.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.todobarzh.domain.model.BaseThrowable
import com.example.todobarzh.domain.repository.TodoItemsRepository
import com.example.todobarzh.ui.ErrorPresenter
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
    private var mutableViewState = MutableStateFlow<TodoViewState>(TodoViewState.Loading)
    val viewState: StateFlow<TodoViewState> = mutableViewState.asStateFlow()

    private val isVisibleCompleteTodo = MutableStateFlow(true)

    init {
        getTodoList()
    }

    private fun getTodoList() {
        viewModelScope.launch {
            repository.getItems()
                .combine(
                    isVisibleCompleteTodo
                ) { todoItemList, doneTasksVisibility ->
                    todoItemList.filter { !it.isComplete or doneTasksVisibility }
                }.collect {
                    mutableViewState.emit(
                        TodoViewState.Loaded(
                            it,
                            repository.getCountCompleteTodo(),
                            isVisibleCompleteTodo.value,
                            er = repository.error.value?.let { error -> ErrorPresenter(error).description }
                        )
                    )
                }
        }
    }

    fun onTodoCheckChangePressed(todoId: String, checked: Boolean) {
        viewModelScope.launch {
            try {
                val item = repository.findTodoItemById(todoId).copy(isComplete = checked)
                repository.changeCheckTodo(item)
            } catch (throwable: BaseThrowable) {
                mutableViewState.emit(TodoViewState.LoadingError(throwable) {
                    viewModelScope.launch {
                        val item = repository.findTodoItemById(todoId).copy(isComplete = checked)
                        repository.changeCheckTodo(item)
                        getTodoList()
                    }
                })
            }
        }

    }

    fun onCompleteTodoVisibleChangePressed() {
        isVisibleCompleteTodo.update { !it }
    }
}