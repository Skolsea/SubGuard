package com.subguard.presentation.theme

import android.app.Activity
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalView
import androidx.core.view.WindowCompat

// 1. Definición de Colores "OLED Midnight Minimalist"
val PureBlack = Color(0xFF000000)       // Fondo Real OLED
val DarkGreySurface = Color(0xFF1C1C1E) // Tarjetas
val NeonTeal = Color(0xFF00E5FF)        // Botones y Acentos
val SoftRed = Color(0xFFFF5252)         // Alertas

private val DarkColorScheme = darkColorScheme(
    primary = NeonTeal,
    secondary = NeonTeal, // Usamos Teal también para secundario para consistencia
    tertiary = SoftRed,
    background = PureBlack,
    surface = DarkGreySurface,
    error = SoftRed,
    onPrimary = PureBlack, // Texto negro sobre botón Teal (mejor contraste)
    onSecondary = PureBlack,
    onTertiary = PureBlack,
    onBackground = Color.White,
    onSurface = Color.White,
    onError = PureBlack,
)

@Composable
fun SubGuardTheme(
    // Forzamos darkTheme = true por defecto para mantener la estética OLED siempre
    darkTheme: Boolean = true, 
    dynamicColor: Boolean = false,
    content: @Composable () -> Unit
) {
    val colorScheme = DarkColorScheme
    val view = LocalView.current
    
    if (!view.isInEditMode) {
        SideEffect {
            val window = (view.context as Activity).window
            
            // --- FIX OLED COMPLETO ---
            // 1. Pintar AMBAS barras de negro puro
            window.statusBarColor = PureBlack.toArgb()
            window.navigationBarColor = PureBlack.toArgb() 

            // 2. Forzar iconos BLANCOS (LightStatusBars = false significa "iconos claros")
            val insetsController = WindowCompat.getInsetsController(window, view)
            insetsController.isAppearanceLightStatusBars = false 
            insetsController.isAppearanceLightNavigationBars = false
        }
    }

    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography,
        shapes = Shapes,
        content = content
    )
}