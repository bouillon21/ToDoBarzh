package com.example.todobarzh.ui.theme

import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import com.example.todobarzh.ui.theme.local.AppTypography

val Typography = AppTypography(
    largeTitle = TextStyle(
        fontFamily = RobotoFonts,
        fontSize = 32.sp,
        lineHeight = 38.sp,
        fontWeight = FontWeight.Medium
    ),
    title = TextStyle(
        fontFamily = RobotoFonts,
        fontSize = 20.sp,
        lineHeight = 32.sp,
        letterSpacing = 0.5.sp,
        fontWeight = FontWeight.Medium
    ),
    button = TextStyle(
        fontFamily = RobotoFonts,
        fontSize = 14.sp,
        lineHeight = 24.sp,
        letterSpacing = 0.16.sp,
        fontWeight = FontWeight.Medium
    ),
    body = TextStyle(
        fontFamily = RobotoFonts,
        fontSize = 16.sp,
        lineHeight = 20.sp,
        fontWeight = FontWeight.Medium
    ),
    subhead = TextStyle(
        fontFamily = RobotoFonts,
        fontSize = 14.sp,
        lineHeight = 20.sp,
        fontWeight = FontWeight.Medium
    )
)