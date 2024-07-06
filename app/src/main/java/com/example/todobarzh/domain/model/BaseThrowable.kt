package com.example.todobarzh.domain.model

sealed class BaseThrowable : Throwable() {

    data object AuthorizationError : BaseThrowable()

    data object NoSuchDataError : BaseThrowable()

    data class ServerError(val value: Int) : BaseThrowable()

    data class GenericError(override val message: String?) : BaseThrowable()

}