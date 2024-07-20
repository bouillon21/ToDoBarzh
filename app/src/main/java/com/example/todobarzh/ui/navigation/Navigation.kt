package com.example.todobarzh.ui.navigation

import android.view.View
import androidx.compose.animation.AnimatedContentTransitionScope
import androidx.compose.animation.core.tween
import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.example.todobarzh.divkit.screens.AboutScreen
import com.example.todobarzh.ui.screens.editscreen.EditScreen
import com.example.todobarzh.ui.screens.editscreen.EditScreenArg
import com.example.todobarzh.ui.screens.mainscreen.MainScreen
import com.example.todobarzh.ui.screens.settingsscreen.SettingsScreen

@Composable
fun Navigation(
    divScreenProvider: (jsonName: String) -> View,
    navController: NavHostController
) {
    NavHost(
        navController = navController,
        startDestination = TodoNavRoute.Home.route,
        enterTransition = {
            slideIntoContainer(
                AnimatedContentTransitionScope.SlideDirection.Left,
                animationSpec = tween(400)
            )
        },
        exitTransition = {
            slideOutOfContainer(
                AnimatedContentTransitionScope.SlideDirection.Left,
                animationSpec = tween(400)
            )
        },
        popEnterTransition = {
            slideIntoContainer(
                AnimatedContentTransitionScope.SlideDirection.Right,
                animationSpec = tween(400)
            )
        },
        popExitTransition = {
            slideOutOfContainer(
                AnimatedContentTransitionScope.SlideDirection.Right,
                animationSpec = tween(400)
            )
        }
    ) {
        composable(route = TodoNavRoute.Home.route) {
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
        composable(TodoNavRoute.Settings.route) {
            SettingsScreen(navController, hiltViewModel())
        }

        composable(TodoNavRoute.About.route) {
            AboutScreen(
                aboutScreen = divScreenProvider.invoke("about_screen.json")
            )
        }
    }
}