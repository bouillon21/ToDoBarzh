package com.example.todobarzh.ui.navigation

import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.todobarzh.ui.components.editscreen.EditScreen
import com.example.todobarzh.ui.components.editscreen.EditScreenArg
import com.example.todobarzh.ui.components.mainscreen.MainScreen

@Composable
fun Navigation() {
    val navController = rememberNavController()
    NavHost(
        navController = navController,
        startDestination = TodoNavRoute.Home.route
    ) {
        composable(TodoNavRoute.Home.route) {
            MainScreen(navController, hiltViewModel())
        }
        composable(
            TodoNavRoute.Edit.route + "/{${EditScreenArg.TODO_ID}}",
            arguments = listOf(navArgument(EditScreenArg.TODO_ID) {
                type = NavType.StringType
            })
        ) {
            EditScreen(navController, hiltViewModel())
        }
        composable(TodoNavRoute.New.route) {
            EditScreen(navController, hiltViewModel())
        }
    }
}