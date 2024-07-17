package com.example.todobarzh.ui.viewmodel

import androidx.lifecycle.ViewModel
import com.example.todobarzh.AppThemeMode
import com.example.todobarzh.ThemeSetting
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject

@HiltViewModel
class SettingsViewModel @Inject constructor(
    private val themeSetting: ThemeSetting
) : ViewModel() {

    fun getThemeMode(): StateFlow<AppThemeMode> {
        return themeSetting.appThemeMode
    }

    fun changeThemeMode(mode: AppThemeMode) {
        themeSetting.changeMode(mode)
    }

}