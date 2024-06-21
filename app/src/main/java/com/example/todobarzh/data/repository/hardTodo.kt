package com.example.todobarzh.data.repository

import com.example.todobarzh.data.model.TodoItem
import com.example.todobarzh.data.model.TodoPriorityEnum
import kotlinx.coroutines.flow.MutableStateFlow
import java.util.Date

object hardTodo {
    val todoItems = MutableStateFlow(
        listOf(
            TodoItem(
                "1",
                "text = 1text = 1text = 1text = 1text = 1text = 1text = 1text = 1text = 1text = 1text = 1text = 1text = 1text = 1text = 1text = 1text = 1text = 1text = 1text = 1text = 1text = 1text = 1text = 1text = 1text = 1text = 1text = 1text = 1text = 1text = 1text = 1text = 1text = 1text = 1text = 1text = 1text = 1",
                TodoPriorityEnum.USUAL,
                null,
                false,
                Date(),
                null
            ),
            TodoItem(
                "2",
                "text = 2",
                TodoPriorityEnum.LOW,
                null,
                true,
                Date(),
                null
            ),
            TodoItem(
                "3",
                "text = 3",
                TodoPriorityEnum.URGENT,
                null,
                false,
                Date(),
                null
            ),
            TodoItem(
                "4",
                "text = 4",
                TodoPriorityEnum.USUAL,
                null,
                false,
                Date(),
                null
            ),
            TodoItem(
                "5",
                "text = 1text = 1text = 1text = 1text = 1text = 1text = 1text = 1text = 1text = 1text = 1text = 1text = 1text = 1text = 1text = 1text = 1text = 1text = 1text = 1text = 1text = 1text = 1text = 1text = 1text = 1text = 1text = 1text = 1text = 1text = 1text = 1text = 1text = 1text = 1text = 1text = 1",
                TodoPriorityEnum.USUAL,
                null,
                false,
                Date(),
                null
            ),
            TodoItem(
                "6",
                "text = 2",
                TodoPriorityEnum.LOW,
                null,
                false,
                Date(),
                null
            ),
            TodoItem(
                "7",
                "text = 3",
                TodoPriorityEnum.URGENT,
                null,
                false,
                Date(),
                null
            ),
            TodoItem(
                "8",
                "text = 4",
                TodoPriorityEnum.USUAL,
                null,
                false,
                Date(),
                null
            ),
            TodoItem(
                "9",
                "text = 1",
                TodoPriorityEnum.USUAL,
                null,
                false,
                Date(),
                null
            ),
            TodoItem(
                "10",
                "text = 2",
                TodoPriorityEnum.LOW,
                null,
                false,
                Date(),
                null
            ),
            TodoItem(
                "11",
                "text = 3",
                TodoPriorityEnum.URGENT,
                null,
                false,
                Date(),
                null
            ),
            TodoItem(
                "12",
                "text = 4",
                TodoPriorityEnum.USUAL,
                null,
                false,
                Date(),
                null
            ),
            TodoItem(
                "13",
                "text = 3",
                TodoPriorityEnum.URGENT,
                null,
                false,
                Date(),
                null
            ),
            TodoItem(
                "14",
                "text = 4",
                TodoPriorityEnum.USUAL,
                null,
                false,
                Date(),
                null
            ),
            TodoItem(
                "15",
                "text = 3",
                TodoPriorityEnum.URGENT,
                null,
                false,
                Date(),
                null
            ),
            TodoItem(
                "16",
                "text = 4",
                TodoPriorityEnum.USUAL,
                null,
                false,
                Date(),
                null
            ),
            TodoItem(
                "17",
                "text = 3",
                TodoPriorityEnum.URGENT,
                null,
                false,
                Date(),
                null
            ),
            TodoItem(
                "18",
                "text = 4",
                TodoPriorityEnum.USUAL,
                null,
                false,
                Date(),
                null
            )

        )
    )
}