package com.example.todobarzh.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.todobarzh.data.model.TodoItem
import com.example.todobarzh.data.model.TodoPriorityEnum
import com.example.todobarzh.data.repository.TodoItemsRepositoryImpl
import com.example.todobarzh.domain.repository.TodoItemsRepository
import com.example.todobarzh.ui.viewstate.TodoViewState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.map
import java.util.Date
import javax.inject.Inject

@HiltViewModel
class TodoViewModel @Inject constructor() : ViewModel() {

    private val repository: TodoItemsRepository = TodoItemsRepositoryImpl()

    private val mutableTodo = MutableStateFlow(TodoViewState(listOf()))
    val todo: SharedFlow<TodoViewState> = mutableTodo.asStateFlow()

    init {
        repository.getTodoItems().map {
            mutableTodo.emit(TodoViewState(it))
        }.launchIn(viewModelScope)
    }

    fun onAddTodoButtonPressed() {
        val moke = TodoItem(
            (1..12000).random().toString(),
            "new new new new new ",
            TodoPriorityEnum.URGENT,
            null,
            false,
            Date(),
            null
        )
        repository.addTodo(moke)
    }

    fun onTodoCheckChangePressed(todoId: String, checked: Boolean) {
        repository.changeCheckTodo(todoId, checked)
    }

    fun onTodoEditPressed(todoId: String) {

    }

}