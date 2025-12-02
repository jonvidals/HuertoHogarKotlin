package com.example.huertohogar.ui.theme

import android.app.Activity
import android.os.Build
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.dynamicDarkColorScheme
import androidx.compose.material3.dynamicLightColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext

// ⭐ COLORES PRINCIPALES - Tema Huerto
val DarkGreen = Color(0xFF1B5E20)
val FreshGreen = Color(0xFF4CAF50)
val LightGreen = Color(0xFF81C784)
val VeryLightGreen = Color(0xFFC8E6C9)


val Brown = Color(0xFF6D4C41)
val LightBrown = Color(0xFFBCAAA4)


val Grey80 = Color(0xFFCCCCCC)
val Grey40 = Color(0xFF999999)

val Accent = Color(0xFFFF6F00)

private val DarkColorScheme = darkColorScheme(
    primary = DarkGreen,
    onPrimary = Color.White,
    primaryContainer = DarkGreen,
    onPrimaryContainer = Color.White,

    secondary = Brown,
    onSecondary = Color.White,
    secondaryContainer = LightBrown,
    onSecondaryContainer = Color.White,

    tertiary = LightGreen,
    onTertiary = Color.White,

    background = Color(0xFF121212),
    onBackground = Color.White,

    surface = Color(0xFF1E1E1E),
    onSurface = Color.White,

    error = Color(0xFFCF6679),
    onError = Color.Black
)

private val LightColorScheme = lightColorScheme(
    primary = FreshGreen,
    onPrimary = Color.White,
    primaryContainer = LightGreen,
    onPrimaryContainer = DarkGreen,

    secondary = Brown,
    onSecondary = Color.White,
    secondaryContainer = LightBrown,
    onSecondaryContainer = Color.Black,

    tertiary = LightGreen,
    onTertiary = Color.White,

    background = Color(0xFFFFFBFE),
    onBackground = Color.Black,

    surface = Color.White,
    onSurface = Color.Black,

    error = Color(0xFFB3261E),
    onError = Color.White
)

@Composable
fun HuertohogarTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    dynamicColor: Boolean = false,
    content: @Composable () -> Unit
) {
    val colorScheme = when {
        dynamicColor && Build.VERSION.SDK_INT >= Build.VERSION_CODES.S -> {
            val context = LocalContext.current
            if (darkTheme) dynamicDarkColorScheme(context) else dynamicLightColorScheme(context)
        }
        darkTheme -> DarkColorScheme
        else -> LightColorScheme
    }

    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography,
        content = content
    )
}