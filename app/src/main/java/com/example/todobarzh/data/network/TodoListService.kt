package com.example.todobarzh.data.network

import com.example.todobarzh.data.network.entities.ListTodoItemReceive
import com.example.todobarzh.data.network.entities.ListTodoItemSend
import com.example.todobarzh.data.network.entities.TodoItemReceive
import com.example.todobarzh.data.network.entities.TodoItemSend
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.PATCH
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path

interface TodoListService {

    object RuntimeConstants {
        var lastKnownRevision: Int = 0
    }

    @GET("list")
    suspend fun getItems(): Response<ListTodoItemReceive>

    @PATCH("list")
    suspend fun updateItems(
        @Body body: ListTodoItemSend,
        @Header("X-Last-Known-Revision") revision: Int = RuntimeConstants.lastKnownRevision,
    ): Response<ListTodoItemReceive>

    @GET("list/{id}")
    suspend fun getItemById(
        @Path("id") path: String,
    ): Response<TodoItemReceive>

    @POST("list")
    suspend fun addItem(
        @Body body: TodoItemSend,
        @Header("X-Last-Known-Revision") revision: Int = RuntimeConstants.lastKnownRevision,
    ): Response<TodoItemReceive>

    @PUT("list/{id}")
    suspend fun updateItem(
        @Path("id") path: String,
        @Body body: TodoItemSend,
        @Header("X-Last-Known-Revision") revision: Int = RuntimeConstants.lastKnownRevision,
    ): Response<TodoItemReceive>

    @DELETE("list/{id}")
    suspend fun deleteItem(
        @Path("id") path: String,
        @Header("X-Last-Known-Revision") revision: Int = RuntimeConstants.lastKnownRevision,
    ): Response<TodoItemReceive>
}