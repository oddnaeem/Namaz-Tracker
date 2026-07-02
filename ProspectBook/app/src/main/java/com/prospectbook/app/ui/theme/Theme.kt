package com.prospectbook.app.ui.theme

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

val Cream = Color(0xFFFAF8F4)
val Ink = Color(0xFF2B2621)
val Green = Color(0xFF3D6B57)
val GreenBg = Color(0xFFE4F0EA)
val MutedText = Color(0xFF8A8075)
val FaintText = Color(0xFFA79C8F)

private val LightColors = lightColorScheme(
    primary = Green,
    onPrimary = Color.White,
    background = Cream,
    surface = Color.White,
    onBackground = Ink,
    onSurface = Ink,
    secondaryContainer = GreenBg,
    onSecondaryContainer = Green,
)

@Composable
fun ProspectBookTheme(content: @Composable () -> Unit) {
    MaterialTheme(colorScheme = LightColors, content = content)
}
