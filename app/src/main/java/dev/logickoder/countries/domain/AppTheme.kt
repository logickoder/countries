package dev.logickoder.countries.domain

import androidx.annotation.StringRes
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.DarkMode
import androidx.compose.material.icons.outlined.LightMode
import androidx.compose.ui.graphics.vector.ImageVector
import dev.logickoder.countries.R

enum class AppTheme(@StringRes val title: Int, val icon: ImageVector) {
    Light(R.string.theme_light, Icons.Outlined.LightMode),
    Dark(R.string.theme_dark, Icons.Outlined.DarkMode),
    System(R.string.theme_system, Light.icon),
}