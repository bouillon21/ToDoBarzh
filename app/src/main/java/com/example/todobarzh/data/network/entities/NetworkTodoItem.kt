package com.example.todobarzh.data.network.entities

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.todobarzh.domain.model.TodoItem
import com.example.todobarzh.domain.model.TodoPriority

@Entity
data class NetworkTodoItem(
    @PrimaryKey
    val id: String,
    val text: String,
    val importance: String,
    val deadline: Long?,
    val done: Boolean,
    val color: String?,
    val created_at: Long,
    val changed_at: Long,
    val last_updated_by: String,
    val isSynchronized: Boolean,
    val isDeleted: Boolean
) {
    companion object {
        fun TodoItem.toNetwork(): NetworkTodoItem {
            return NetworkTodoItem(
                id = id,
                text = text,
                importance = importance.name.lowercase(),
                deadline = deadline,
                done = isComplete,
                color = "#FFFFFF",
                created_at = dateCreate,
                changed_at = dateEdit ?: (dateCreate + 10),
                last_updated_by = "12",
                isSynchronized = false,
                isDeleted = false
            )
        }
    }

    fun toItem(): TodoItem {
        return TodoItem(
            id = id,
            text = text,
            importance = TodoPriority.valueOf(importance.uppercase()),
            dateCreate = created_at,
            deadline = deadline,
            isComplete = done,
            dateEdit = changed_at,
            isSynchronized = isSynchronized
        )
    }
}