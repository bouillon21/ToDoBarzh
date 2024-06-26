package com.example.todobarzh.data.repository

import com.example.todobarzh.data.model.TodoItem
import com.example.todobarzh.domain.repository.TodoItemsRepository
import dagger.Provides
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.update
import javax.inject.Inject


class TodoItemsRepositoryImpl @Inject constructor() : TodoItemsRepository {

    private val todoItems = hardTodo.todoItems

    override fun getTodoItems(): SharedFlow<List<TodoItem>> = todoItems.asSharedFlow()

    override fun getCountCompleteTodo(): Int = todoItems.value.count { it.flagComplete }

    override fun addTodo(item: TodoItem) {
        todoItems.update {
            val updatedList = todoItems.value.toMutableList()
            updatedList.add(0, item)
            updatedList.toList()
        }
    }

    override fun changeCheckTodo(todoId: String, checked: Boolean) {
        todoItems.update {
            val tet = todoItems.value.toMutableList()
            tet.filter { it.id == todoId }.map { it.flagComplete = checked }
            tet.toList()
        }
    }

    override fun removeTodo(item: TodoItem) {

    }

}