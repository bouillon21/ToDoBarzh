package com.example.todobarzh.data.room

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.todobarzh.data.network.entities.NetworkTodoItem

@Database(
    entities = [
        NetworkTodoItem::class
    ],
    version = 1
)

abstract class TodoDatabase : RoomDatabase() {
    abstract val dao: TodoDao
}


