package com.example.todobarzh.domain.repository

import com.example.todobarzh.domain.model.TodoItem
import kotlinx.coroutines.flow.StateFlow

interface TodoItemsRepository {

    suspend fun getItems(): StateFlow<List<TodoItem>>

    suspend fun findTodoItemById(id: String): TodoItem

    suspend fun getCountCompleteTodo(): Int

    suspend fun updateTodo(item: TodoItem)

    suspend fun addTodo(item: TodoItem)

    suspend fun changeCheckTodo(item: TodoItem)

    suspend fun removeTodo(todoId: String)
}