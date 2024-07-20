package com.example.todobarzh.ui.navigation

sealed class TodoNavRoute(val route: String) {
    data object Home : TodoNavRoute("home")
    data object Edit : TodoNavRoute("edit")
    data object New : TodoNavRoute("new")
    data object Settings : TodoNavRoute("settings")
    data object About : TodoNavRoute("about")

    fun withArgs(vararg args: String): String {
        return buildString {
            append(route)
            args.forEach { arg ->
                append("/${arg}")
            }
        }
    }
}
