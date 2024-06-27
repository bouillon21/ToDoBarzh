package com.example.todobarzh.data.model

import java.time.LocalDate
import java.util.UUID

data class TodoItem(
    val id: String,
    val text: String,
    val importance: TodoPriority,
    val deadline: LocalDate? = null,
    val isComplete: Boolean,
    val dateCreate: LocalDate,
    val dateEdit: LocalDate? = null
)

fun emptyTodoItem(): TodoItem {
    return TodoItem(
        id = UUID.randomUUID().toString(),
        text = "",
        importance = TodoPriority.USUAL,
        isComplete = false,
        dateCreate = LocalDate.now()
    )
}
