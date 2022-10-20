package dev.logickoder.countries.presentation.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material.MaterialTheme
import androidx.compose.material.darkColors
import androidx.compose.material.lightColors
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import dev.logickoder.countries.ui.theme.Typography

private val DarkColorPalette = darkColors(
    background = BackgroundColorDark,
    surface = SurfaceColorDark,
    onBackground = TextColorDark,
)

private val LightColorPalette = lightColors(
    background = BackgroundColorLight,
    surface = SurfaceColorLight,
    onBackground = TextColorLight,
)

@Composable
fun CountriesTheme(darkTheme: Boolean = isSystemInDarkTheme(), content: @Composable () -> Unit) {
    val colors = if (darkTheme) {
        DarkColorPalette
    } else LightColorPalette

    val systemUiController = rememberSystemUiController()
    LaunchedEffect(key1 = colors, block = {
        systemUiController.setSystemBarsColor(
            color = colors.background,
            darkIcons = !darkTheme,
        )
    })

    MaterialTheme(
        colors = colors,
        typography = Typography,
        shapes = Shapes,
        content = content
    )
}