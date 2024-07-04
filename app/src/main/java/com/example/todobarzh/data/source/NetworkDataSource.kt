package com.example.todobarzh.data.source

import com.example.todobarzh.data.network.RetrofitBuilder
import com.example.todobarzh.data.network.TodoListService
import com.example.todobarzh.data.network.entities.NetworkTodoItem.Companion.toNetwork
import com.example.todobarzh.data.network.entities.TodoItemSend
import com.example.todobarzh.data.network.entities.ListTodoItemSend
import com.example.todobarzh.data.network.entities.TodoItemReceive
import com.example.todobarzh.data.network.entities.ListTodoItemReceive
import com.example.todobarzh.domain.model.TodoItem
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class NetworkDataSource(private val service: TodoListService) : DataSource {
    companion object {
        fun create(): NetworkDataSource {
            return NetworkDataSource(RetrofitBuilder.networkService)
        }
    }

    override suspend fun getItems(): Flow<List<TodoItem>> {
        val items = service.getItems()
        TodoListService.RuntimeConstants.lastKnownRevision = items.revision

        return flow {
            emit(items.list.map { it.toItem() })
        }
    }

    override suspend fun updateItems(items: List<TodoItem>): Flow<List<TodoItem>> {
        val body = ListTodoItemSend(items.map { it.toNetwork() })

        val itemsWithRevision: ListTodoItemReceive = service.updateItems(body)
        TodoListService.RuntimeConstants.lastKnownRevision = itemsWithRevision.revision

        return flow {
            emit(itemsWithRevision.list.map { it.toItem() })
        }
    }

    override suspend fun addItem(item: TodoItem) {
        val body = TodoItemSend(element = item.toNetwork())
        val todoItemReceive: TodoItemReceive = service.addItem(body)
        TodoListService.RuntimeConstants.lastKnownRevision = todoItemReceive.revision
    }

    override suspend fun getItemById(id: String): TodoItem {
        return service.getItemById(id).element.toItem()
    }

    override suspend fun updateItem(item: TodoItem) {
        val body = TodoItemSend(item.toNetwork())
        val itemWithRevision = service.updateItem(body.element.id, body)
        TodoListService.RuntimeConstants.lastKnownRevision = itemWithRevision.revision
    }

    override suspend fun deleteItem(id: String) {
        val itemWithRevision = service.deleteItem(id)
        TodoListService.RuntimeConstants.lastKnownRevision = itemWithRevision.revision
    }
}