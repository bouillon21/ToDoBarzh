package com.example.todobarzh.ui.screens.editscreen.components

import android.content.res.Configuration
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.material3.Surface
import androidx.compose.material3.Switch
import androidx.compose.material3.SwitchDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.tooling.preview.PreviewParameterProvider
import androidx.compose.ui.unit.dp
import com.example.todobarzh.R
import com.example.todobarzh.ui.screens.common.toDateString
import com.example.todobarzh.ui.screens.editscreen.EditScreenEvent
import com.example.todobarzh.ui.theme.Blue
import com.example.todobarzh.ui.theme.ToDoBarzhTheme

@Composable
fun DeadlineTodoSwitcher(
    deadlineDate: Long?,
    onEvent: (EditScreenEvent) -> Unit,
    modifier: Modifier
) {
    var checked by remember { mutableStateOf(false) }
    var datePickerExpanded by remember { mutableStateOf(false) }
    val deadlineDateText = remember(deadlineDate) {
        deadlineDate?.toDateString() ?: ""
    }
    if (deadlineDate != null) checked = true

    val deadlineState = if (checked) stringResource(R.string.enable_deadline) else
        stringResource(R.string.disable_deadline)

    Row(modifier.semantics { contentDescription = deadlineState }) {
        Column(
            Modifier
                .align(Alignment.CenterVertically)
                .clickable { datePickerExpanded = checked }
        ) {
            Text(
                stringResource(R.string.deadline_title),
                style = ToDoBarzhTheme.typography.body,
                color = ToDoBarzhTheme.colorScheme.labelPrimary
            )
            if (checked) {
                Spacer(Modifier.height(4.dp))
                Text(
                    text = deadlineDateText,
                    style = ToDoBarzhTheme.typography.button,
                    color = Blue
                )
            }
        }
        Spacer(Modifier.weight(1f))
        Switch(
            checked = checked,
            onCheckedChange = {
                checked = it
                if (checked) {
                    if (deadlineDate == null)
                        onEvent.invoke(EditScreenEvent.UpdateDate(System.currentTimeMillis()))
                } else {
                    onEvent.invoke(EditScreenEvent.UpdateDate(null))
                }
            },
            colors = SwitchDefaults.colors(
                checkedTrackColor = Blue,
                uncheckedTrackColor = ToDoBarzhTheme.colorScheme.supportOverlay,
                uncheckedBorderColor = ToDoBarzhTheme.colorScheme.supportSeparator,
                uncheckedThumbColor = ToDoBarzhTheme.colorScheme.supportSeparator
            )
        )
    }
    if (datePickerExpanded) {
        TodoDatePicker(
            date = deadlineDate ?: System.currentTimeMillis(),
            onConfirm = {
                datePickerExpanded = false
                onEvent.invoke(EditScreenEvent.UpdateDate(it))
            },
            onDismiss = { datePickerExpanded = false }
        )
    }
}

@Preview(
    name = "light",
    group = "color",
    uiMode = Configuration.UI_MODE_NIGHT_NO,
)
@Composable
fun DeadlineTodoSwitcherPreview(@PreviewParameter(DeadlineTodoProviders::class) date: Long?) {
    ToDoBarzhTheme {
        Surface(Modifier.background(color = ToDoBarzhTheme.colorScheme.backPrimary)) {
            DeadlineTodoSwitcher(date, {}, Modifier)
        }
    }
}

private class DeadlineTodoProviders : PreviewParameterProvider<Long?> {
    override val values: Sequence<Long?>
        get() = sequenceOf(
            null,
            System.currentTimeMillis()
        )
}