package com.example.todobarzh.data.source

import com.example.todobarzh.domain.model.TodoItem
import kotlinx.coroutines.flow.Flow

interface DataSource {
    suspend fun getItems(): Flow<List<TodoItem>>

    suspend fun updateItems(items: List<TodoItem>): Flow<List<TodoItem>>

    suspend fun getItemById(id: String): TodoItem

    suspend fun addItem(item: TodoItem)

    suspend fun updateItem(item: TodoItem)

    suspend fun deleteItem(id: String)
}