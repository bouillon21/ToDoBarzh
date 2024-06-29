package com.example.todobarzh.ui.theme

import android.content.res.Configuration
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.graphics.Shadow
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
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

@Composable
fun BoxColor(
    text: String,
    color: Color
) {
    Text(
        text = text,
        color = Color.Magenta,
        style = TextStyle(shadow = Shadow(Color.Black, Offset(2f, 3f), 2f)),
        modifier = Modifier
            .background(color)
            .border(1.dp, Color.Magenta, RectangleShape)
            .size(90.dp)

    )
}

@Preview(
    name = "light",
    group = "color",
    uiMode = Configuration.UI_MODE_NIGHT_NO,
    device = "spec:parent=pixel_5,orientation=landscape"
)
@Preview(
    name = "dark",
    group = "color",
    uiMode = Configuration.UI_MODE_NIGHT_YES,
    device = "spec:parent=pixel_5,orientation=landscape"
)
@Composable
fun ColorPalettePreview() {
    ToDoBarzhTheme {
        Column {
            Row {
                BoxColor("Support / Separator", ToDoBarzhTheme.colorScheme.supportSeparator)
                BoxColor("Support / Overlay", ToDoBarzhTheme.colorScheme.supportOverlay)
            }
            Row {
                BoxColor("Back / Primary", ToDoBarzhTheme.colorScheme.backPrimary)
                BoxColor("Back / Secondary", ToDoBarzhTheme.colorScheme.backSecondary)
                BoxColor("Back / Elevated", ToDoBarzhTheme.colorScheme.backElevated)
            }
            Row {
                BoxColor(text = "Label / Primary", color = ToDoBarzhTheme.colorScheme.labelPrimary)
                BoxColor(
                    text = "Label / Secondary",
                    color = ToDoBarzhTheme.colorScheme.labelSecondary
                )
                BoxColor(
                    text = "Label / Tertiary",
                    color = ToDoBarzhTheme.colorScheme.labelTertiary
                )
                BoxColor(text = "Label / Disable", color = ToDoBarzhTheme.colorScheme.labelDisable)
            }
            Row {
                BoxColor(text = "Color / Red", color = Red)
                BoxColor(text = "Color / Green", color = Green)
                BoxColor(text = "Color / Blue", color = Blue)
                BoxColor(text = "Color / Gray", color = Gray)
                BoxColor(text = "Color / GrayLight", color = GrayLight)
                BoxColor(text = "Color / White", color = White)
            }
        }

    }
}

@Preview(
    name = "light",
    group = "color",
    uiMode = Configuration.UI_MODE_NIGHT_NO
)
@Preview(
    name = "dark",
    group = "color",
    uiMode = Configuration.UI_MODE_NIGHT_YES
)
@Composable
fun TextStylePreview() {
    ToDoBarzhTheme {
        Column(
            horizontalAlignment = Alignment.Start,
            modifier = Modifier
                .background(ToDoBarzhTheme.colorScheme.backPrimary)
                .padding(8.dp)
        ) {
            val textModifier = Modifier.padding(12.dp)
            Text(
                text = "Large Title - 32/38",
                style = ToDoBarzhTheme.typography.largeTitle,
                color = ToDoBarzhTheme.colorScheme.labelPrimary,
                modifier = textModifier
            )
            Text(
                text = "Title — 20/32",
                style = ToDoBarzhTheme.typography.title,
                color = ToDoBarzhTheme.colorScheme.labelPrimary,
                modifier = textModifier
            )
            Text(
                text = "BUTTON — 14/24",
                style = ToDoBarzhTheme.typography.button,
                color = ToDoBarzhTheme.colorScheme.labelPrimary,
                modifier = textModifier
            )
            Text(
                text = "Body — 16/20",
                style = ToDoBarzhTheme.typography.body,
                color = ToDoBarzhTheme.colorScheme.labelPrimary,
                modifier = textModifier
            )
            Text(
                text = "Subhead — 14/20",
                style = ToDoBarzhTheme.typography.subhead,
                color = ToDoBarzhTheme.colorScheme.labelPrimary,
                modifier = textModifier
            )
        }

    }
}