package com.example.todobarzh.ui.screens.common

import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.TopAppBarState
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.unit.dp

@OptIn(ExperimentalMaterial3Api::class)
fun getShadowTopAppBarModifier(topAppBarState: TopAppBarState): Modifier {
    return if (topAppBarState.collapsedFraction < 0.5f) {
        Modifier.shadow(0.dp)
    } else {
        Modifier.shadow(8.dp)
    }

}