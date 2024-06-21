package com.example.todobarzh.domain.repository

import com.example.todobarzh.data.model.TodoItem
import kotlinx.coroutines.flow.SharedFlow


interface TodoItemsRepository {

    fun getTodoItems(): SharedFlow<List<TodoItem>>

    fun getCountCompleteTodo(): Int

    fun addTodo(item: TodoItem)

    fun changeCheckTodo(todoId: String, checked: Boolean)

    fun removeTodo(item: TodoItem)
}