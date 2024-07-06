package com.example.todobarzh.data.repository

import com.example.todobarzh.data.source.DataSource
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
    dataSource: DataSource
) : TodoItemsRepository {
    private val mutableTodoItems = MutableStateFlow<List<TodoItem>>(emptyList())
    private val source = dataSource

    override suspend fun getItems(): StateFlow<List<TodoItem>> {
        val item = source.getItems()
        item.collect {
            mutableTodoItems.emit(it)
        }
        return mutableTodoItems.asStateFlow()
    }

    override suspend fun getCountCompleteTodo(): Int = withContext(Dispatchers.IO) {
        mutableTodoItems.value.count { it.isComplete }
    }

    override suspend fun updateTodo(item: TodoItem) = withContext(Dispatchers.IO) {
        mutableTodoItems.update { items ->
            items.map { todo ->
                if (todo.id == item.id) {
                    item
                } else {
                    todo
                }
            }
        }
        source.updateItem(item)
    }

    override suspend fun findTodoItemById(id: String): TodoItem = withContext(Dispatchers.IO) {
        mutableTodoItems.value.find { it.id == id } ?: emptyTodoItem()
    }

    override suspend fun addTodo(item: TodoItem) = withContext(Dispatchers.IO) {
        mutableTodoItems.update {
            val updatedList = mutableTodoItems.value.toMutableList()
            updatedList.add(0, item)
            updatedList.toList()
        }
        source.addItem(item)
    }

    override suspend fun changeCheckTodo(item: TodoItem) =
        withContext(Dispatchers.IO) {
            mutableTodoItems.update { items ->
                items.map {
                    if (it.id == item.id) {
                        it.copy(isComplete = item.isComplete)
                    } else {
                        it
                    }
                }
            }
            source.updateItem(item)
        }

    override suspend fun removeTodo(todoId: String) = withContext(Dispatchers.IO) {
        mutableTodoItems.update { items ->
            items.filter { it.id != todoId }
        }
        source.deleteItem(todoId)
    }
}