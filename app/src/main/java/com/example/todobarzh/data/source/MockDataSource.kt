package com.example.todobarzh.data.source

import com.example.todobarzh.domain.model.TodoItem
import com.example.todobarzh.domain.model.TodoPriority
import java.time.LocalDate

class MockDataSource {
    private val mockTodoItems =
        listOf(
            TodoItem(
                "1",
                "But I must explain to you how all this mistaken idea of denouncing pleasure and praising pain was born and I will give you a complete account of the system",
                TodoPriority.USUAL,
                null,
                true,
                LocalDate.now(),
                null
            ),
            TodoItem(
                "2",
                "купить что то ",
                TodoPriority.USUAL,
                null,
                true,
                LocalDate.now(),
                null
            ),
            TodoItem(
                "3",
                "покушать",
                TodoPriority.LOW,
                null,
                false,
                LocalDate.now(),
                null
            ),
            TodoItem(
                "4",
                "успеть до дедлайна",
                TodoPriority.URGENT,
                LocalDate.of(2024, 7, 29),
                false,
                LocalDate.now(),
                null
            ),
            TodoItem(
                "5",
                "настроить DI",
                TodoPriority.USUAL,
                null,
                false,
                LocalDate.now(),
                null
            ),
            TodoItem(
                "6",
                "поспать",
                TodoPriority.LOW,
                null,
                false,
                LocalDate.now(),
                null
            ),
            TodoItem(
                "7",
                "покурить",
                TodoPriority.URGENT,
                null,
                false,
                LocalDate.now(),
                null
            ),
            TodoItem(
                "8",
                "да кто это такие корутины ваши",
                TodoPriority.USUAL,
                null,
                false,
                LocalDate.now(),
                null
            )
        )

    fun getTodoItems(): List<TodoItem> = mockTodoItems

    fun updateTodoItems(item: TodoItem) {
        mockTodoItems.map {
            if (it.id == item.id) {
                item
            } else {
                it
            }
        }
    }

    fun addTodo(item: TodoItem) {
        val updatedList = mockTodoItems.toMutableList()
        updatedList.add(0, item)
        updatedList.toList()
    }

    fun changeCheckTodo(todoId: String, checked: Boolean) {
        mockTodoItems.map {
            if (it.id == todoId) {
                it.copy(isComplete = checked)
            } else {
                it
            }
        }
    }

    fun removeTodo(todoId: String) {
        mockTodoItems.dropWhile { it.id == todoId }
    }

}