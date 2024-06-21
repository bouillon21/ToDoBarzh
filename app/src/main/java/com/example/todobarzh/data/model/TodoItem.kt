package com.example.todobarzh.data.model

import java.util.Date

data class TodoItem(
    val id: String,
    var text: String,
    var important: TodoPriorityEnum,
    var deadline: Date?,
    var flagComplete: Boolean,
    val dateCreate: Date,
    var dateEdit: Date?
)
