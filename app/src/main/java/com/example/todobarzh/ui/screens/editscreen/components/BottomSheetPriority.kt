package com.example.todobarzh.ui.screens.editscreen.components

import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.example.todobarzh.R
import com.example.todobarzh.domain.model.TodoPriority
import com.example.todobarzh.ui.screens.editscreen.EditScreenEvent
import com.example.todobarzh.ui.theme.Red
import com.example.todobarzh.ui.theme.ToDoBarzhTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BottomSheet(
    important: TodoPriority,
    onEvent: (EditScreenEvent) -> Unit,
    modifier: Modifier
) {
    val showBottomSheet = remember { mutableStateOf(false) }

    val sheetState = rememberModalBottomSheetState()
    val isAnimatingImportanceIndication = remember { mutableStateOf(false) }

    val basicTextFieldBackgroundColor by animateColorAsState(
        targetValue =
        if (isAnimatingImportanceIndication.value)
            Red
        else
            ToDoBarzhTheme.colorScheme.backPrimary,
        animationSpec = tween(durationMillis = 500),
        finishedListener = {
            isAnimatingImportanceIndication.value = false
        },
        label = "basicTextFieldBackgroundColor"
    )

    Column(
        modifier
            .fillMaxWidth()
            .clickable { showBottomSheet.value = true }
            .border(
                BorderStroke(1.dp, basicTextFieldBackgroundColor),
                RoundedCornerShape(3.dp),
            )
    ) {
        Text(
            text = stringResource(R.string.label_spinner_edit_view),
            style = ToDoBarzhTheme.typography.body,
            color = ToDoBarzhTheme.colorScheme.labelPrimary,
        )
        Spacer(Modifier.height(4.dp))
        Text(
            text = important.value,
            style = ToDoBarzhTheme.typography.subhead,
            color = ToDoBarzhTheme.colorScheme.labelPrimary,
        )

        if (showBottomSheet.value) {
            ModalBottomSheet(
                onDismissRequest = {
                    showBottomSheet.value = false
                },
                sheetState = sheetState,
                containerColor = ToDoBarzhTheme.colorScheme.backPrimary
            ) {
                Column {
                    Text(
                        text = stringResource(R.string.label_spinner_edit_view),
                        style = ToDoBarzhTheme.typography.largeTitle,
                        color = ToDoBarzhTheme.colorScheme.labelPrimary,
                        textAlign = TextAlign.Center,
                        modifier = modifier.fillMaxWidth()
                    )
                    TextItemBottomSheet(
                        TodoPriority.LOW,
                        onEvent,
                        showBottomSheet,
                        Modifier
                    )
                    HorizontalDivider(
                        thickness = 1.dp,
                        color = ToDoBarzhTheme.colorScheme.supportSeparator
                    )
                    TextItemBottomSheet(
                        TodoPriority.BASIC,
                        onEvent,
                        showBottomSheet,
                        Modifier
                    )
                    HorizontalDivider(
                        thickness = 1.dp,
                        color = ToDoBarzhTheme.colorScheme.supportSeparator
                    )
                    TextItemBottomSheet(
                        TodoPriority.IMPORTANT,
                        onEvent,
                        showBottomSheet,
                        Modifier
                    ) { isAnimatingImportanceIndication.value = true }
                    Spacer(modifier = Modifier.height(20.dp))
                }
            }
        }
    }
}

@Composable
fun TextItemBottomSheet(
    important: TodoPriority,
    onEvent: (EditScreenEvent) -> Unit,
    showState: MutableState<Boolean>,
    modifier: Modifier,
    startAnimation: () -> Unit = {},
) {
    Text(
        text = important.value,
        style = ToDoBarzhTheme.typography.title,
        color = ToDoBarzhTheme.colorScheme.labelPrimary,
        textAlign = TextAlign.Center,
        modifier = modifier
            .fillMaxWidth()
            .padding(15.dp)
            .clickable {
                onEvent.invoke(EditScreenEvent.UpdateImportance(important))
                showState.value = false
                startAnimation.invoke()
            }
    )
}