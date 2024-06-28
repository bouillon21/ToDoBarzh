package com.example.todobarzh.ui.viewmodel

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.todobarzh.domain.model.TodoPriority
import com.example.todobarzh.domain.model.emptyTodoItem
import com.example.todobarzh.domain.repository.TodoItemsRepository
import com.example.todobarzh.ui.components.editscreen.EditScreenArg
import com.example.todobarzh.ui.viewstate.EditTodoViewState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.time.LocalDate
import javax.inject.Inject

@HiltViewModel
class TodoEditViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    private val repository: TodoItemsRepository,
) : ViewModel() {

    private val id: String = savedStateHandle[EditScreenArg.TODO_ID] ?: EMPTY_ID

    private val mutableViewState =
        MutableStateFlow(EditTodoViewState.Loaded(emptyTodoItem()))
    val viewState: StateFlow<EditTodoViewState> = mutableViewState.asStateFlow()

    init {
        viewModelScope.launch {
            mutableViewState.update {
                EditTodoViewState.Loaded(repository.findTodoItemById(id))
            }
        }
    }

    fun saveTodo() {
        viewModelScope.launch {
            val item = viewState.value as EditTodoViewState.Loaded
            if (id == EMPTY_ID) {
                repository.addTodo(item.todoItem)
            } else {
                repository.updateTodo(item.todoItem)
            }
        }
    }

    fun updateEditText(text: String) {
        mutableViewState.update {
            EditTodoViewState.Loaded(
                mutableViewState.value.todoItem.copy(text = text)
            )
        }
    }

    fun updateImportance(important: TodoPriority) {
        mutableViewState.update {
            EditTodoViewState.Loaded(
                mutableViewState.value.todoItem.copy(importance = important)
            )
        }
    }

    fun updateDate(date: LocalDate) {
        mutableViewState.update {
            EditTodoViewState.Loaded(
                mutableViewState.value.todoItem.copy(deadline = date)
            )
        }
    }

    fun removeTodo() {
        viewModelScope.launch {
            val item = viewState.value as EditTodoViewState.Loaded
            repository.removeTodo(item.todoItem.id)
        }
    }

    companion object {
        const val EMPTY_ID = ""
    }

}