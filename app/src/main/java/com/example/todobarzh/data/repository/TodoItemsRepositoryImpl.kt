package com.example.todobarzh.data.repository

import com.example.todobarzh.data.source.MockDataSource
import com.example.todobarzh.domain.model.TodoItem
import com.example.todobarzh.domain.model.emptyTodoItem
import com.example.todobarzh.domain.repository.TodoItemsRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.withContext
import javax.inject.Inject

class TodoItemsRepositoryImpl @Inject constructor(
    dataSource: MockDataSource
) : TodoItemsRepository {

    private val todoItems = MutableStateFlow<List<TodoItem>>(emptyList())

    init {
        todoItems.value = dataSource.getTodoItems()
    }

    override fun getTodoItems(): StateFlow<List<TodoItem>> = todoItems.asStateFlow()

    override suspend fun getCountCompleteTodo(): Int = withContext(Dispatchers.IO) {
        todoItems.value.count { it.isComplete }
    }

    override suspend fun updateTodo(item: TodoItem) = withContext(Dispatchers.IO) {
        todoItems.update { items ->
            items.map { todo ->
                if (todo.id == item.id) {
                    item
                } else {
                    todo
                }
            }
        }
    }

    override suspend fun findTodoItemById(id: String): TodoItem = withContext(Dispatchers.IO) {
        todoItems.value.find { it.id == id } ?: emptyTodoItem()
    }

    override suspend fun addTodo(item: TodoItem) = withContext(Dispatchers.IO) {
        todoItems.update {
            val updatedList = todoItems.value.toMutableList()
            updatedList.add(0, item)
            updatedList.toList()
        }
    }

    override suspend fun changeCheckTodo(todoId: String, checked: Boolean) =
        withContext(Dispatchers.IO) {
            todoItems.update { items ->
                items.map {
                    if (it.id == todoId) {
                        it.copy(isComplete = checked)
                    } else {
                        it
                    }
                }
            }
        }

    override suspend fun removeTodo(todoId: String) = withContext(Dispatchers.IO) {
        todoItems.update { items ->
            items.filter { it.id != todoId }
        }
    }
}