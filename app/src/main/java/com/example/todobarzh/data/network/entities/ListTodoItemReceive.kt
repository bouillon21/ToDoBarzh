package com.example.todobarzh.data.network.entities

data class ListTodoItemReceive(
    val status: String,
    val list: List<NetworkTodoItem>,
    val revision: Int
)
