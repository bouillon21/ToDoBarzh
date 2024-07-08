package com.example.todobarzh.data.repository

import com.example.todobarzh.domain.model.BaseThrowable
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import retrofit2.Response

open class BaseRepository {
    suspend fun <T> safeAPICall(
        dispatcher: CoroutineDispatcher,
        apiCall: suspend () -> Response<T>
    ): T {
        return withContext(dispatcher) {
            val response = apiCall.invoke()
            when (val code = response.code()) {
                in 200..299 -> response.body()!!
                401 -> throw BaseThrowable.AuthorizationError
                404 -> throw BaseThrowable.NoSuchDataError
                else -> throw BaseThrowable.ServerError(code)
            }
        }
    }

}