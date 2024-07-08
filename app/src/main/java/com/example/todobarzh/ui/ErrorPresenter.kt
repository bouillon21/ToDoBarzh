package com.example.todobarzh.ui

import com.example.todobarzh.domain.model.BaseThrowable

class ErrorPresenter(throwable: BaseThrowable) {
    val title: String
    val description: String?

    init {
        when (throwable) {
            BaseThrowable.AuthorizationError -> {
                title = "BaseThrowable"
                description = "Ошибка авторизации"
            }

            is BaseThrowable.GenericError -> {
                title = "BaseThrowable"
                description = "Не известная ошибка"
            }

            BaseThrowable.NoSuchDataError -> {
                title = "BaseThrowable"
                description = "Не найдена запись"
            }

            is BaseThrowable.ServerError -> {
                title = "BaseThrowable"
                description = null
            }
        }
    }
}

val BaseThrowable.getErrorTitle: String
    get() = ErrorPresenter(this).title

val BaseThrowable.getErrorDescription: String?
    get() = ErrorPresenter(this).description
