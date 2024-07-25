package com.example.todobarzh.ui.screens.settingsscreen

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberTopAppBarState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.tooling.preview.PreviewParameterProvider
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.todobarzh.AppThemeMode
import com.example.todobarzh.R
import com.example.todobarzh.ui.navigation.TodoNavRoute
import com.example.todobarzh.ui.screens.settingsscreen.components.AboutUiItem
import com.example.todobarzh.ui.screens.settingsscreen.components.ThemeSpinner
import com.example.todobarzh.ui.theme.ToDoBarzhTheme
import com.example.todobarzh.ui.viewmodel.SettingsViewModel

private fun onEvent(
    action: SettingsScreenEvent, viewModel: SettingsViewModel, navController: NavController
) {
    when (action) {
        SettingsScreenEvent.Exit -> {
            navController.popBackStack()
        }

        is SettingsScreenEvent.ChangeTheme -> {
            viewModel.changeThemeMode(action.themeMode)
        }

        SettingsScreenEvent.OpenAboutScreen -> navController.navigate(TodoNavRoute.About.route)
    }
}

sealed interface SettingsScreenEvent {

    data object Exit : SettingsScreenEvent

    data class ChangeTheme(val themeMode: AppThemeMode) : SettingsScreenEvent

    data object OpenAboutScreen : SettingsScreenEvent
}

@Composable
fun SettingsScreen(navController: NavController, viewModel: SettingsViewModel) {
    val state by viewModel.getThemeMode().collectAsState()
    val onEvent =
        remember { { action: SettingsScreenEvent -> onEvent(action, viewModel, navController) } }

    SettingsScreenContent(state, onEvent)
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SettingsScreenContent(state: AppThemeMode, onEvent: (SettingsScreenEvent) -> Unit) {
    val appBarState = rememberTopAppBarState()
    val scrollBehavior = TopAppBarDefaults.pinnedScrollBehavior(appBarState)

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = stringResource(R.string.setting_screen_title),
                        style = ToDoBarzhTheme.typography.largeTitle,
                        color = ToDoBarzhTheme.colorScheme.labelPrimary
                    )
                },
                navigationIcon = {
                    IconButton(onClick = { onEvent.invoke(SettingsScreenEvent.Exit) }) {
                        Icon(
                            painter = painterResource(id = R.drawable.ic_cross),
                            tint = ToDoBarzhTheme.colorScheme.labelPrimary,
                            contentDescription = "exit"
                        )
                    }
                },
                colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
                    containerColor = ToDoBarzhTheme.colorScheme.backPrimary,
                    scrolledContainerColor = ToDoBarzhTheme.colorScheme.backSecondary
                )
            )
        },
        containerColor = ToDoBarzhTheme.colorScheme.backPrimary
    ) { contentPadding ->
        Column(
            Modifier
                .nestedScroll(scrollBehavior.nestedScrollConnection)
                .padding(contentPadding)
                .padding(8.dp)
        ) {
            ThemeSpinner(
                state, onEvent, Modifier
            )
            Spacer(modifier = Modifier.height(16.dp))
            HorizontalDivider(color = ToDoBarzhTheme.colorScheme.supportSeparator)
            AboutUiItem(
                onClick = {
                    onEvent.invoke(SettingsScreenEvent.OpenAboutScreen)
                },
            )
        }
    }
}

@Preview
@Composable
fun SettingsScreenContentPreview(@PreviewParameter(SettingsProviders::class) themeMode: AppThemeMode) {
    ToDoBarzhTheme {
        SettingsScreenContent(themeMode, {})
    }
}

private class SettingsProviders : PreviewParameterProvider<AppThemeMode> {
    override val values: Sequence<AppThemeMode>
        get() = sequenceOf(
            AppThemeMode.SYSTEM_THEME,
            AppThemeMode.LIGHT_THEME,
            AppThemeMode.DARK_THEME
        )

}