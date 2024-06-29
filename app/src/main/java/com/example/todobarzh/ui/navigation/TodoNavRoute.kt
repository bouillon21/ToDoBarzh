package com.example.todobarzh.ui.navigation

sealed class TodoNavRoute(val route: String) {
    data object Home : TodoNavRoute("home")
    data object Edit : TodoNavRoute("edit")
    data object New : TodoNavRoute("new")

    fun withArgs(vararg args: String): String {
        return buildString {
            append(route)
            args.forEach { arg ->
                append("/${arg}")
            }
        }
    }
}
