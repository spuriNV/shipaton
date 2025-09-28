package com.performativemalecoach.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.runtime.Composable

private val LightColorScheme = lightColorScheme(
    primary = MatchaGreen,
    onPrimary = CardBackground,
    primaryContainer = LightMatcha,
    onPrimaryContainer = Charcoal,
    secondary = MutedBrown,
    onSecondary = CardBackground,
    secondaryContainer = SoftTaupe,
    onSecondaryContainer = Charcoal,
    tertiary = SageGreen,
    onTertiary = CardBackground,
    background = WarmBeige,
    onBackground = Charcoal,
    surface = CardBackground,
    onSurface = Charcoal,
    surfaceVariant = SoftTaupe,
    onSurfaceVariant = DeepEarth,
    outline = Divider
)

private val DarkColorScheme = darkColorScheme(
    primary = LightMatcha,
    onPrimary = Charcoal,
    primaryContainer = MatchaGreen,
    onPrimaryContainer = WarmBeige,
    secondary = SoftTaupe,
    onSecondary = Charcoal,
    secondaryContainer = MutedBrown,
    onSecondaryContainer = WarmBeige,
    tertiary = SageGreen,
    onTertiary = Charcoal,
    background = Charcoal,
    onBackground = WarmBeige,
    surface = DeepEarth,
    onSurface = WarmBeige,
    surfaceVariant = MutedBrown,
    onSurfaceVariant = SoftTaupe,
    outline = MutedBrown
)

@Composable
fun PerformativeMaleCoachTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit
) {
    val colorScheme = when {
        darkTheme -> DarkColorScheme
        else -> LightColorScheme
    }

    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography,
        content = content
    )
}