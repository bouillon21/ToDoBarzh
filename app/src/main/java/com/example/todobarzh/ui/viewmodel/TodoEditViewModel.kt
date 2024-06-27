package com.example.todobarzh.ui.viewmodel

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import com.example.todobarzh.ui.components.editscreen.EditScreenArg
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class TodoEditViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle
) : ViewModel() {
    private val id: String? = savedStateHandle[EditScreenArg.ID]
}