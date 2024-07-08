package com.example.todobarzh.data.network.entities

import com.example.todobarzh.domain.model.TodoItem
import com.example.todobarzh.domain.model.TodoPriority

data class NetworkTodoItem(
    val id: String,
    val text: String,
    val importance: String,
    val deadline: Long?,
    val done: Boolean,
    val color: String?,
    val created_at: Long,
    val changed_at: Long,
    val last_updated_by: String
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
                last_updated_by = "12"
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
            dateEdit = changed_at
        )
    }
}