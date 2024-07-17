package com.example.todobarzh.data.room

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Upsert
import com.example.todobarzh.data.network.entities.NetworkTodoItem
import kotlinx.coroutines.flow.Flow

@Dao
interface TodoDao {

    @Query("SELECT * FROM networktodoitem WHERE id = :id")
    suspend fun getTask(id: String): NetworkTodoItem

    @Query("SELECT * FROM networktodoitem WHERE isDeleted = 0")
    fun getTaskList(): Flow<List<NetworkTodoItem>>

    @Upsert
    suspend fun upsertTask(task: NetworkTodoItem)

    @Upsert
    suspend fun upsertTasks(tasks: List<NetworkTodoItem>)

    @Query("DELETE FROM networktodoitem WHERE id = :id")
    suspend fun deleteTaskById(id: String)

    @Query("SELECT * FROM networktodoitem WHERE isSynchronized = 0")
    suspend fun getNotSynchronizedItems(): List<NetworkTodoItem>
}
