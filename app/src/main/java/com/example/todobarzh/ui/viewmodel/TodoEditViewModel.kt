package com.example.todobarzh.ui.viewmodel

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import com.example.todobarzh.data.model.TodoItem
import com.example.todobarzh.domain.repository.TodoItemsRepository
import com.example.todobarzh.ui.components.editscreen.EditScreenArg
import com.example.todobarzh.ui.viewstate.EditTodoViewState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject

@HiltViewModel
class TodoEditViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    private val repository: TodoItemsRepository,
) : ViewModel() {

    private val id: String = savedStateHandle[EditScreenArg.ID] ?: EMPTY_ID

    private val mutableTodo = MutableStateFlow(EditTodoViewState(repository.findTodoItemById(id)))
    val todo: StateFlow<EditTodoViewState> = mutableTodo.asStateFlow()

    fun saveTodo(item: TodoItem) {
        if (id == EMPTY_ID) {
            repository.addTodo(item)
        } else {
            repository.updateTodo(item)
        }
    }

    fun removeTodo(itemId: String) {
        repository.removeTodo(itemId)
    }

    companion object {
        const val EMPTY_ID = ""
    }

}