package com.example.ui.theme

import android.os.Build
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.dynamicDarkColorScheme
import androidx.compose.material3.dynamicLightColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext

private val DarkColorScheme = darkColorScheme(
  primary = CoffeePrimaryDark,
  onPrimary = CoffeeOnPrimaryDark,
  primaryContainer = CoffeePrimaryContainerDark,
  onPrimaryContainer = CoffeeOnPrimaryContainerDark,
  secondary = CoffeeSecondaryDark,
  onSecondary = CoffeeOnSecondaryDark,
  background = CoffeeBackgroundDark,
  surface = CoffeeSurfaceDark,
  onBackground = CoffeeOnSurfaceDark,
  onSurface = CoffeeOnSurfaceDark,
  surfaceVariant = CoffeeSurfaceVariantDark,
  onSurfaceVariant = CoffeeOnSurfaceVariantDark
)

private val LightColorScheme = lightColorScheme(
  primary = CoffeePrimaryLight,
  onPrimary = CoffeeOnPrimaryLight,
  primaryContainer = CoffeePrimaryContainerLight,
  onPrimaryContainer = CoffeeOnPrimaryContainerLight,
  secondary = CoffeeSecondaryLight,
  onSecondary = CoffeeOnSecondaryLight,
  background = CoffeeBackgroundLight,
  surface = CoffeeSurfaceLight,
  onBackground = CoffeeOnSurfaceLight,
  onSurface = CoffeeOnSurfaceLight,
  surfaceVariant = CoffeeSurfaceVariantLight,
  onSurfaceVariant = CoffeeOnSurfaceVariantLight
)

@Composable
fun BaristaSimulatorTheme(
  darkTheme: Boolean = isSystemInDarkTheme(),
  dynamicColor: Boolean = false, // Keep warm coffee identity consistent
  content: @Composable () -> Unit,
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
