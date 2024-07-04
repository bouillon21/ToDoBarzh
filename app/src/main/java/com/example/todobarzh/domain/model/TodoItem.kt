package com.example.todobarzh.domain.model

import java.util.UUID

data class TodoItem(
    val id: String,
    val text: String,
    val importance: TodoPriority,
    val deadline: Long? = null,
    val isComplete: Boolean,
    val dateCreate: Long,
    val dateEdit: Long? = null
)

fun emptyTodoItem(): TodoItem {
    return TodoItem(
        id = UUID.randomUUID().toString(),
        text = "",
        importance = TodoPriority.BASIC,
        isComplete = false,
        dateCreate = System.currentTimeMillis()
    )
}
