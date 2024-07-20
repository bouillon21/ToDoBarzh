package com.example.todobarzh

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.runtime.collectAsState
import androidx.navigation.compose.rememberNavController
import com.example.todobarzh.divkit.ActionController
import com.example.todobarzh.divkit.DivScreenBuilder
import com.example.todobarzh.ui.navigation.Navigation
import com.example.todobarzh.ui.theme.ToDoBarzhTheme
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    @Inject
    lateinit var themeConfig: ThemeSetting

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            val navController = rememberNavController()
            val themeState = themeConfig.appThemeMode.collectAsState()
            ToDoBarzhTheme(
                darkTheme = themeState.value.value ?: isSystemInDarkTheme()
            ) {
                Navigation({ jsonName ->
                    DivScreenBuilder(
                        activity = this,
                        lifecycleOwner = this,
                        actionController = ActionController(navController)
                    ).provideScreenByJsonName(jsonName)
                }, navController)
            }
        }
    }
}
