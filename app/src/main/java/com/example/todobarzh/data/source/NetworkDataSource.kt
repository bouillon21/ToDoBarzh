package com.example.todobarzh.data.source

import com.example.todobarzh.data.network.RetrofitBuilder
import com.example.todobarzh.data.network.TodoListService
import com.example.todobarzh.data.network.entities.ListTodoItemSend
import com.example.todobarzh.data.network.entities.NetworkTodoItem.Companion.toNetwork
import com.example.todobarzh.data.network.entities.TodoItemSend
import com.example.todobarzh.data.repository.BaseRepository
import com.example.todobarzh.domain.model.TodoItem
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class NetworkDataSource(private val service: TodoListService) : BaseRepository(), DataSource {
    companion object {
        fun create(): NetworkDataSource {
            return NetworkDataSource(RetrofitBuilder.networkService)
        }
    }

    override suspend fun getItems(): Flow<List<TodoItem>> {
        val result = safeAPICall(Dispatchers.IO) { service.getItems() }
        TodoListService.RuntimeConstants.lastKnownRevision = result.revision
        return flow {
            emit(result.list.map { it.toItem() })
        }
    }

    override suspend fun updateItems(items: List<TodoItem>): Flow<List<TodoItem>> {
        val body = ListTodoItemSend(items.map { it.toNetwork() })
        val result = safeAPICall(Dispatchers.IO) { service.updateItems(body) }

        TodoListService.RuntimeConstants.lastKnownRevision = result.revision
        return flow { emit(result.list.map { it.toItem() }) }
    }

    override suspend fun addItem(item: TodoItem) {
        val body = TodoItemSend(element = item.toNetwork())
        val result = safeAPICall(Dispatchers.IO) { service.addItem(body) }

        TodoListService.RuntimeConstants.lastKnownRevision = result.revision
    }


    override suspend fun getItemById(id: String): TodoItem {
        val result = safeAPICall(Dispatchers.IO) { service.getItemById(id) }
        return result.element.toItem()
    }

    override suspend fun updateItem(item: TodoItem) {
        val body = TodoItemSend(item.toNetwork())
        val result = safeAPICall(Dispatchers.IO) { service.updateItem(body.element.id, body) }

        TodoListService.RuntimeConstants.lastKnownRevision = result.revision
    }

    override suspend fun deleteItem(id: String) {
        val result = safeAPICall(Dispatchers.IO) { service.deleteItem(id) }

        TodoListService.RuntimeConstants.lastKnownRevision = result.revision
    }
}