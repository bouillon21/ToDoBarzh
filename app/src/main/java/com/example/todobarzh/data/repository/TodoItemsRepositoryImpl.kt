package com.example.todobarzh.data.repository

import com.example.todobarzh.data.network.entities.NetworkTodoItem
import com.example.todobarzh.data.network.entities.NetworkTodoItem.Companion.toNetwork
import com.example.todobarzh.data.room.TodoDao
import com.example.todobarzh.data.source.DataSource
import com.example.todobarzh.domain.model.BaseThrowable
import com.example.todobarzh.domain.model.TodoItem
import com.example.todobarzh.domain.model.emptyTodoItem
import com.example.todobarzh.domain.repository.TodoItemsRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

class TodoItemsRepositoryImpl @Inject constructor(
    networkDataSource: DataSource,
    localDataSource: TodoDao,
) : TodoItemsRepository {
    private val mutableTodoItems = MutableStateFlow<List<TodoItem>>(emptyList())
    private val networkSource = networkDataSource
    private val localSource = localDataSource

    private var mutableError = MutableStateFlow<BaseThrowable?>(null)
    override val error = mutableError.asStateFlow()

    private suspend fun handleError(call: suspend () -> Unit) {
        try {
            call.invoke()
        } catch (throwable: BaseThrowable) {
            mutableError.update { throwable }
        }
    }

    override suspend fun getItems(): StateFlow<List<TodoItem>> {
        CoroutineScope(Dispatchers.IO).launch {
            localSource.getTaskList().collect { items ->
                mutableTodoItems.update { items.map { it.toItem() } }

                val networkTodoItems = mutableListOf<TodoItem>()
                handleError {
                    networkSource.getItems().collect {
                        networkTodoItems.addAll(it)
                    }
                }
                mergeData(localSource.getNotSynchronizedItems(), networkTodoItems)
            }
        }
        return mutableTodoItems.asStateFlow()
    }

    private suspend fun mergeData(
        notSynchronizedItems: List<NetworkTodoItem>,
        networkItems: List<TodoItem>
    ) {
        val networkItemsMap = hashMapOf<String, TodoItem>()
        networkItems.forEach { item -> networkItemsMap[item.id] = item }

        localSource.upsertTasks(networkItems.map { it.toNetwork().copy(isSynchronized = true) })

        for (item in notSynchronizedItems) {
            if (item.id in networkItemsMap.keys) {
                if (item.isDeleted) {
                    handleError {
                        networkSource.deleteItem(item.id)
                        localSource.deleteTaskById(item.id)
                    }
                    continue
                }
                handleError {
                    networkSource.updateItem(item.toItem())
                    localSource.upsertTask(item.copy(isSynchronized = true))
                }
            } else {
                handleError {
                    networkSource.addItem(item.toItem())
                    localSource.upsertTask(item.copy(isSynchronized = true))
                }
            }
        }
    }

    override suspend fun getCountCompleteTodo(): Int = withContext(Dispatchers.IO) {
        mutableTodoItems.value.count { it.isComplete }
    }

    override suspend fun updateTodo(item: TodoItem) = withContext(Dispatchers.IO) {
        localSource.upsertTask(item.toNetwork())
    }

    override suspend fun findTodoItemById(id: String): TodoItem = withContext(Dispatchers.IO) {
        mutableTodoItems.value.find { it.id == id } ?: emptyTodoItem()
    }

    override suspend fun addTodo(item: TodoItem) = withContext(Dispatchers.IO) {
        localSource.upsertTask(item.toNetwork())
    }

    override suspend fun changeCheckTodo(item: TodoItem) =
        withContext(Dispatchers.IO) {
            localSource.upsertTask(
                item.toNetwork().copy(done = item.isComplete)
            )
        }

    override suspend fun removeTodo(todoId: String) = withContext(Dispatchers.IO) {
        val item = localSource.getTask(todoId)
        localSource.upsertTask(item.copy(isSynchronized = false, isDeleted = true))
    }
}