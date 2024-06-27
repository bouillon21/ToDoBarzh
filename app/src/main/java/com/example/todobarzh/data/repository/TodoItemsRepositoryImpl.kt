package com.example.todobarzh.data.repository

import com.example.todobarzh.data.model.TodoItem
import com.example.todobarzh.data.model.emptyTodoItem
import com.example.todobarzh.domain.repository.TodoItemsRepository
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.update
import javax.inject.Inject

class TodoItemsRepositoryImpl @Inject constructor() : TodoItemsRepository {

    private val todoItems = hardTodo.todoItems

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