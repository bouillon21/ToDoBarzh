package com.example.todobarzh.data.repository

import com.example.todobarzh.domain.model.TodoItem
import com.example.todobarzh.domain.model.emptyTodoItem
import com.example.todobarzh.domain.repository.TodoItemsRepository
import com.example.todobarzh.data.source.MockDataSource
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.update
import javax.inject.Inject

class TodoItemsRepositoryImpl @Inject constructor(
    dataSource: MockDataSource
) : TodoItemsRepository {

    private val todoItems = MutableStateFlow<List<TodoItem>>(emptyList())

    init {
        todoItems.value = dataSource.getTodoItems()
    }

    override fun getTodoItems(): SharedFlow<List<TodoItem>> = todoItems.asSharedFlow()

    override fun getCountCompleteTodo(): Int = todoItems.value.count { it.isComplete }

    override fun updateTodo(item: TodoItem) {
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

    override fun findTodoItemById(id: String): TodoItem {
        return todoItems.value.find { it.id == id } ?: emptyTodoItem()
    }

    override fun addTodo(item: TodoItem) {
        todoItems.update {
            val updatedList = todoItems.value.toMutableList()
            updatedList.add(0, item)
            updatedList.toList()
        }
    }

    override fun changeCheckTodo(todoId: String, checked: Boolean) {
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

    override fun removeTodo(todoId: String) {
        todoItems.update { items ->
            items.dropWhile { it.id == todoId }
        }
    }

}