package com.example.todobarzh.data.network.entities

data class TodoItemReceive(
    val status: String,
    val element: NetworkTodoItem,
    val revision: Int
)