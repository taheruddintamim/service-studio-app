package com.example.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.ColorScheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.graphics.Color

val LocalAppThemeIsDark = staticCompositionLocalOf { true }

private val StudioDarkColorScheme = darkColorScheme(
    primary = StudioBrandBlue,
    onPrimary = Color.White,
    primaryContainer = StudioBrandBlueDark,
    onPrimaryContainer = Color.White,
    secondary = DarkTypographySecondary,
    onSecondary = DarkTypographyPrimary,
    secondaryContainer = DarkSurfaceHigh,
    onSecondaryContainer = DarkTypographyPrimary,
    tertiary = StudioWarmAmber,
    onTertiary = Color.Black,
    background = DarkCanvas,
    onBackground = DarkTypographyPrimary,
    surface = DarkSurface,
    onSurface = DarkTypographyPrimary,
    surfaceVariant = DarkSurfaceHigh,
    onSurfaceVariant = DarkTypographySecondary,
    surfaceContainerLowest = DarkCanvas,
    surfaceContainerLow = DarkSurfaceContainerLow,
    surfaceContainer = DarkSurface,
    surfaceContainerHigh = DarkSurfaceHigh,
    surfaceContainerHighest = DarkSurfaceHighest,
    outline = DarkBorderHighlight,
    outlineVariant = DarkBorder
)

private val StudioLightColorScheme = lightColorScheme(
    primary = LightPrimary,
    onPrimary = Color.White,
    primaryContainer = LightPrimaryContainer,
    onPrimaryContainer = Color.White,
    secondary = LightTypographySecondary,
    onSecondary = LightTypographyPrimary,
    secondaryContainer = LightSurfaceHigh,
    onSecondaryContainer = LightTypographyPrimary,
    tertiary = StudioBrandBlueDark,
    onTertiary = Color.White,
    background = LightCanvas,
    onBackground = LightTypographyPrimary,
    surface = LightSurface,
    onSurface = LightTypographyPrimary,
    surfaceVariant = LightSurfaceHigh,
    onSurfaceVariant = LightTypographySecondary,
    surfaceContainerLowest = LightCanvas,
    surfaceContainerLow = LightSurfaceContainerLow,
    surfaceContainer = LightSurface,
    surfaceContainerHigh = LightSurfaceHigh,
    surfaceContainerHighest = LightSurfaceHighest,
    outline = LightBorderHighlight,
    outlineVariant = LightBorder
)

@Composable
fun MyApplicationTheme(
    darkTheme: Boolean = true,
    content: @Composable () -> Unit
) {
    val colorScheme = if (darkTheme) StudioDarkColorScheme else StudioLightColorScheme

    CompositionLocalProvider(LocalAppThemeIsDark provides darkTheme) {
        MaterialTheme(
            colorScheme = colorScheme,
            typography = Typography,
            content = content
        )
    }
}
