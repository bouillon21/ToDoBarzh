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
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.tooling.preview.PreviewParameterProvider
import androidx.compose.ui.unit.dp
import com.example.todobarzh.R
import com.example.todobarzh.ui.screens.common.toDate
import com.example.todobarzh.ui.screens.common.toFormatString
import com.example.todobarzh.ui.screens.common.toLong
import com.example.todobarzh.ui.screens.editscreen.EditScreenEvent
import com.example.todobarzh.ui.theme.Blue
import com.example.todobarzh.ui.theme.ToDoBarzhTheme
import java.time.LocalDate

@Composable
fun DeadlineTodoSwitcher(
    deadlineDate: LocalDate?,
    onEvent: (EditScreenEvent) -> Unit,
    modifier: Modifier
) {
    var checked: Boolean by remember { mutableStateOf(deadlineDate != null) }
    var datePickerExpanded by remember { mutableStateOf(false) }

    var deadlineDateText by remember {
        mutableStateOf(deadlineDate?.toFormatString())
    }

    Row(modifier) {
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
                    text = deadlineDateText ?: "",
                    style = ToDoBarzhTheme.typography.button,
                    color = Blue
                )
            }
        }
        Spacer(Modifier.weight(1f))
        Switch(
            checked = checked,
            onCheckedChange = { checked = it },
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
            date = deadlineDate?.toLong() ?: System.currentTimeMillis(),
            onConfirm = {
                datePickerExpanded = !datePickerExpanded
                onEvent.invoke(EditScreenEvent.UpdateDate(it.toDate()))
                deadlineDateText = deadlineDate?.toFormatString()
            },
            onDismiss = { datePickerExpanded = !datePickerExpanded }
        )
    }
}

@Preview(
    name = "light",
    group = "color",
    uiMode = Configuration.UI_MODE_NIGHT_NO,
)
@Composable
fun DeadlineTodoSwitcherPreview(@PreviewParameter(DeadlineTodoProviders::class) date: LocalDate?) {
    ToDoBarzhTheme {
        Surface(Modifier.background(color = ToDoBarzhTheme.colorScheme.backPrimary)) {
            DeadlineTodoSwitcher(date, {}, Modifier)
        }
    }
}

private class DeadlineTodoProviders : PreviewParameterProvider<LocalDate?> {
    override val values: Sequence<LocalDate?>
        get() = sequenceOf(
            null,
            LocalDate.of(2025, 7, 23)
        )
}