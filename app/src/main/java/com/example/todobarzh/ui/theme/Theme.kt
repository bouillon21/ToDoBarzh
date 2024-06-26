package com.example.todobarzh.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import com.example.todobarzh.ui.theme.ToDoBarzhTheme.typography
import com.example.todobarzh.ui.theme.local.AppColorScheme
import com.example.todobarzh.ui.theme.local.AppTypography
import com.example.todobarzh.ui.theme.local.LocalAppColorScheme
import com.example.todobarzh.ui.theme.local.LocalAppTypography

private val lightColorScheme = AppColorScheme(
    supportSeparator = SupportSeparatorLight,
    supportOverlay = SupportOverlayLight,
    labelPrimary = LabelPrimaryLight,
    labelSecondary = LabelSecondaryLight,
    labelTertiary = LabelTertiaryLight,
    labelDisable = LabelDisableLight,
    backPrimary = BackPrimaryLight,
    backSecondary = BackSecondaryLight,
    backElevated = BackElevatedLight
)

private val darkColorScheme = AppColorScheme(
    supportSeparator = SupportSeparatorDark,
    supportOverlay = SupportOverlayDark,
    labelPrimary = LabelPrimaryDark,
    labelSecondary = LabelSecondaryDark,
    labelTertiary = LabelTertiaryDark,
    labelDisable = LabelDisableDark,
    backPrimary = BackPrimaryDark,
    backSecondary = BackSecondaryDark,
    backElevated = BackElevatedDark
)

@Composable
fun ToDoBarzhTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    dynamicColor: Boolean = true,
    content: @Composable () -> Unit
) {
    val colorScheme = if (darkTheme) darkColorScheme else lightColorScheme

    CompositionLocalProvider(
        LocalAppColorScheme provides colorScheme,
        LocalAppTypography provides Typography,
        content = content
    )
}

object ToDoBarzhTheme {

    val colorScheme: AppColorScheme
        @Composable get() = LocalAppColorScheme.current

    val typography: AppTypography
        @Composable get() = LocalAppTypography.current
}