package com.example.todobarzh.data.repository

import com.example.todobarzh.data.model.TodoItem
import com.example.todobarzh.data.model.TodoPriority
import kotlinx.coroutines.flow.MutableStateFlow
import java.time.LocalDate

object hardTodo {
    val todoItems = MutableStateFlow(
        listOf(
            TodoItem(
                "1",
                "text = 1text = 1text = 1text = 1text = 1text = 1text = 1text = 1text = 1text = 1text = 1text = 1text = 1text = 1text = 1text = 1text = 1text = 1text = 1text = 1text = 1text = 1text = 1text = 1text = 1text = 1text = 1text = 1text = 1text = 1text = 1text = 1text = 1text = 1text = 1text = 1text = 1text = 1",
                TodoPriority.USUAL,
                null,
                false,
                LocalDate.now(),
                null
            ),
            TodoItem(
                "2",
                "text = 2",
                TodoPriority.LOW,
                null,
                true,
                LocalDate.now(),
                null
            ),
            TodoItem(
                "3",
                "text = 3",
                TodoPriority.URGENT,
                null,
                false,
                LocalDate.now(),
                null
            ),
            TodoItem(
                "4",
                "text = 4",
                TodoPriority.USUAL,
                null,
                false,
                LocalDate.now(),
                null
            ),
            TodoItem(
                "5",
                "text = 1text = 1text = 1text = 1text = 1text = 1text = 1text = 1text = 1text = 1text = 1text = 1text = 1text = 1text = 1text = 1text = 1text = 1text = 1text = 1text = 1text = 1text = 1text = 1text = 1text = 1text = 1text = 1text = 1text = 1text = 1text = 1text = 1text = 1text = 1text = 1text = 1",
                TodoPriority.USUAL,
                null,
                false,
                LocalDate.now(),
                null
            ),
            TodoItem(
                "6",
                "text = 2",
                TodoPriority.LOW,
                null,
                false,
                LocalDate.now(),
                null
            ),
            TodoItem(
                "7",
                "text = 3",
                TodoPriority.URGENT,
                null,
                false,
                LocalDate.now(),
                null
            ),
            TodoItem(
                "8",
                "text = 4",
                TodoPriority.USUAL,
                null,
                false,
                LocalDate.now(),
                null
            ),
            TodoItem(
                "9",
                "text = 1",
                TodoPriority.USUAL,
                null,
                false,
                LocalDate.now(),
                null
            ),
            TodoItem(
                "10",
                "text = 2",
                TodoPriority.LOW,
                null,
                false,
                LocalDate.now(),
                null
            ),
            TodoItem(
                "11",
                "text = 3",
                TodoPriority.URGENT,
                null,
                false,
                LocalDate.now(),
                null
            ),
            TodoItem(
                "12",
                "text = 4",
                TodoPriority.USUAL,
                null,
                false,
                LocalDate.now(),
                null
            ),
            TodoItem(
                "13",
                "text = 3",
                TodoPriority.URGENT,
                null,
                false,
                LocalDate.now(),
                null
            ),
            TodoItem(
                "14",
                "text = 4",
                TodoPriority.USUAL,
                null,
                false,
                LocalDate.now(),
                null
            ),
            TodoItem(
                "15",
                "text = 3",
                TodoPriority.URGENT,
                null,
                false,
                LocalDate.now(),
                null
            ),
            TodoItem(
                "16",
                "text = 4",
                TodoPriority.USUAL,
                null,
                false,
                LocalDate.now(),
                null
            ),
            TodoItem(
                "17",
                "text = 3",
                TodoPriority.URGENT,
                null,
                false,
                LocalDate.now(),
                null
            ),
            TodoItem(
                "18",
                "text = 4",
                TodoPriority.USUAL,
                null,
                false,
                LocalDate.now(),
                null
            )

        )
    )
}