package com.example.todobarzh.domain.repository

import com.example.todobarzh.domain.model.TodoItem
import kotlinx.coroutines.flow.SharedFlow


interface TodoItemsRepository {

    fun getTodoItems(): SharedFlow<List<TodoItem>>

    fun findTodoItemById(id: String): TodoItem

    fun getCountCompleteTodo(): Int

    fun updateTodo(item: TodoItem)

    fun addTodo(item: TodoItem)

    fun changeCheckTodo(todoId: String, checked: Boolean)

    fun removeTodo(todoId: String)
}