package com.example.todobarzh

import android.content.Context
import android.content.SharedPreferences
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class ThemeSetting(
    context: Context,
) {
    private val sharedPreferences: SharedPreferences =
        context.getSharedPreferences(APP_USER_PREFS, Context.MODE_PRIVATE)

    private val mutableAppThemeMode = MutableStateFlow(getTheme())
    val appThemeMode: StateFlow<AppThemeMode> get() = mutableAppThemeMode


    fun changeMode(mode: AppThemeMode) {
        setTheme(mode)
    }

    private fun getTheme(): AppThemeMode {
        val mode = sharedPreferences.getString(KEY_THEME, AppThemeMode.SYSTEM_THEME.toString())
        return AppThemeMode.valueOf(mode!!)
    }

    private fun setTheme(theme: AppThemeMode) {
        CoroutineScope(Dispatchers.IO).launch {
            mutableAppThemeMode.update { theme }
        }
        val editor = sharedPreferences.edit()
        editor.putString(KEY_THEME, theme.toString())
        editor.apply()
    }

    companion object {
        private const val APP_USER_PREFS = "AppUserPrefs"
        private const val KEY_THEME = "theme_color"
    }
}

@Composable
fun AppThemeMode.themeTitle(): String {
    return when (this) {
        AppThemeMode.DARK_THEME -> stringResource(R.string.dark_theme_title)
        AppThemeMode.LIGHT_THEME -> stringResource(R.string.light_theme_title)
        AppThemeMode.SYSTEM_THEME -> stringResource(R.string.system_theme_title)
    }
}

enum class AppThemeMode(val value: Boolean?) {
    DARK_THEME(true),
    LIGHT_THEME(false),
    SYSTEM_THEME(null)
}