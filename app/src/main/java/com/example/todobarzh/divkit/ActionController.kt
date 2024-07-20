package com.example.todobarzh.divkit

import androidx.navigation.NavController

class ActionController(
    private val navController: NavController,
) {

    fun action(action: DivKitActions) {
        when (action) {
            DivKitActions.NavigateUp -> navController.navigateUp()
        }
    }
}

enum class DivKitActions {
    NavigateUp
}