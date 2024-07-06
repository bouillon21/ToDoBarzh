package com.example.todobarzh.ui.viewmodel

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.todobarzh.domain.model.BaseThrowable
import com.example.todobarzh.domain.model.TodoPriority
import com.example.todobarzh.domain.model.emptyTodoItem
import com.example.todobarzh.domain.repository.TodoItemsRepository
import com.example.todobarzh.ui.screens.editscreen.EditScreenArg
import com.example.todobarzh.ui.viewstate.EditTodoViewState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class TodoEditViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    private val repository: TodoItemsRepository,
) : ViewModel() {

    private val id: String = savedStateHandle[EditScreenArg.TODO_ID] ?: EMPTY_ID

    private val mutableViewState =
        MutableStateFlow<EditTodoViewState>(EditTodoViewState.Loading)
    val viewState: StateFlow<EditTodoViewState> = mutableViewState.asStateFlow()

    private var item = emptyTodoItem()

    init {
        viewModelScope.launch {
            mutableViewState.update {
                item = repository.findTodoItemById(id)
                EditTodoViewState.Loaded(item)
            }
        }
    }

    fun saveTodo() {
        viewModelScope.launch {
            try {
                if (id == EMPTY_ID) {
                    repository.addTodo(item)
                } else {
                    repository.updateTodo(item)
                }
            } catch (throwable: BaseThrowable) {
                mutableViewState.emit(EditTodoViewState.LoadingError(throwable) {
                    viewModelScope.launch {
                        if (id == EMPTY_ID) {
                            repository.addTodo(item)
                        } else {
                            repository.updateTodo(item)
                        }
                        mutableViewState.emit(EditTodoViewState.Loaded(item))
                    }
                })
            }
        }
    }

    fun updateEditText(text: String) {
        mutableViewState.update {
            item = item.copy(text = text)
            EditTodoViewState.Loaded(item)
        }
    }

    fun updateImportance(important: TodoPriority) {
        mutableViewState.update {
            item = item.copy(importance = important)
            EditTodoViewState.Loaded(item)
        }
    }

    fun updateDate(date: Long?) {
        mutableViewState.update {
            item = item.copy(deadline = date)
            EditTodoViewState.Loaded(item)
        }
    }

    fun removeTodo() {
        viewModelScope.launch {
            try {
                repository.removeTodo(item.id)
            } catch (throwable: BaseThrowable) {
                mutableViewState.emit(EditTodoViewState.LoadingError(throwable) {
                    viewModelScope.launch {
                        repository.removeTodo(item.id)
                        mutableViewState.emit(EditTodoViewState.Loaded(item))
                    }
                })
            }
        }
    }

    companion object {
        const val EMPTY_ID = ""
    }

}