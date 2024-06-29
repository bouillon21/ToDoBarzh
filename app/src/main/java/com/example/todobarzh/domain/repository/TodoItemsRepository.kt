package com.example.todobarzh.domain.repository

import com.example.todobarzh.domain.model.TodoItem
import kotlinx.coroutines.flow.SharedFlow


interface TodoItemsRepository {

    fun getTodoItems(): SharedFlow<List<TodoItem>>

    suspend fun findTodoItemById(id: String): TodoItem

    suspend fun getCountCompleteTodo(): Int

    suspend fun updateTodo(item: TodoItem)

    suspend fun addTodo(item: TodoItem)

    suspend fun changeCheckTodo(todoId: String, checked: Boolean)

    suspend fun removeTodo(todoId: String)
}