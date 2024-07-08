package com.example.todobarzh.ui.screens.editscreen.components

import android.content.res.Configuration
import androidx.compose.material3.DatePicker
import androidx.compose.material3.DatePickerDefaults
import androidx.compose.material3.DatePickerDialog
import androidx.compose.material3.DisplayMode
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.SelectableDates
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import com.example.todobarzh.R
import com.example.todobarzh.ui.theme.Blue
import com.example.todobarzh.ui.theme.ToDoBarzhTheme
import com.example.todobarzh.ui.theme.White
import java.time.LocalDate

@OptIn(ExperimentalMaterial3Api::class)
object FutureOrPresentSelectableDates : SelectableDates {
    override fun isSelectableDate(utcTimeMillis: Long): Boolean {
        return utcTimeMillis >= System.currentTimeMillis()
    }

    override fun isSelectableYear(year: Int): Boolean {
        return year >= LocalDate.now().year
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TodoDatePicker(
    date: Long,
    onConfirm: (Long) -> Unit,
    onDismiss: () -> Unit
) {
    val datePickerState = rememberDatePickerState(
        initialDisplayMode = DisplayMode.Picker,
        initialSelectedDateMillis = date,
        selectableDates = FutureOrPresentSelectableDates
    )

    DatePickerDialog(
        confirmButton = {
            TextButton(
                onClick = {
                    onConfirm(
                        datePickerState.selectedDateMillis ?: datePickerState.displayedMonthMillis
                    )
                }
            ) {
                Text(
                    text = stringResource(R.string.confirm_button_date_picker_dialog),
                    color = Blue,
                    style = ToDoBarzhTheme.typography.button
                )
            }
        },
        dismissButton = {
            TextButton(
                onClick = { onDismiss() }
            ) {
                Text(
                    text = stringResource(R.string.dismiss_button_date_picker_dialog),
                    color = Blue,
                    style = ToDoBarzhTheme.typography.button
                )
            }
        },
        onDismissRequest = { onDismiss() },
        colors = DatePickerDefaults.colors(
            containerColor = ToDoBarzhTheme.colorScheme.backSecondary
        )
    ) {
        DatePicker(
            state = datePickerState,
            colors = DatePickerDefaults.colors(
                containerColor = ToDoBarzhTheme.colorScheme.backSecondary,
                todayDateBorderColor = Blue,
                todayContentColor = Blue,
                yearContentColor = ToDoBarzhTheme.colorScheme.labelPrimary,
                selectedDayContainerColor = Blue,
                selectedDayContentColor = White,
                selectedYearContainerColor = Blue,
                selectedYearContentColor = White,
                navigationContentColor = ToDoBarzhTheme.colorScheme.labelPrimary,
                headlineContentColor = ToDoBarzhTheme.colorScheme.labelPrimary,
                dividerColor = ToDoBarzhTheme.colorScheme.labelPrimary,
                disabledDayContentColor = ToDoBarzhTheme.colorScheme.labelTertiary,
                dayContentColor = ToDoBarzhTheme.colorScheme.labelPrimary,
                disabledYearContentColor = ToDoBarzhTheme.colorScheme.labelSecondary,
                disabledSelectedDayContainerColor = Blue.copy(0.5f),
                weekdayContentColor = ToDoBarzhTheme.colorScheme.labelPrimary
            )
        )
    }
}

@Preview(
    name = "light",
    group = "datePicker",
    uiMode = Configuration.UI_MODE_NIGHT_NO
)
@Preview(
    name = "dark",
    group = "datePicker",
    uiMode = Configuration.UI_MODE_NIGHT_YES
)
@Composable
fun TodoDatePickerPreview() {
    ToDoBarzhTheme {
        TodoDatePicker(System.currentTimeMillis(), { }, {})
    }
}