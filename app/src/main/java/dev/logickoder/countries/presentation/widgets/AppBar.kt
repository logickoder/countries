package dev.logickoder.countries.presentation.widgets

import androidx.compose.foundation.clickable
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.material.AlertDialog
import androidx.compose.material.Button
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.Icon
import androidx.compose.material.ListItem
import androidx.compose.material.MaterialTheme
import androidx.compose.material.RadioButton
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import dev.logickoder.countries.R
import dev.logickoder.countries.data.repository.SettingsRepository
import dev.logickoder.countries.domain.AppTheme
import dev.logickoder.countries.presentation.theme.CountriesTheme
import dev.logickoder.countries.presentation.theme.secondaryPadding
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterialApi::class)
@Composable
private fun AppBar(
    theme: AppTheme,
    modifier: Modifier = Modifier,
    onTheme: (AppTheme) -> Unit,
) {
    TopAppBar(
        modifier = modifier,
        backgroundColor = MaterialTheme.colors.surface,
        contentPadding = PaddingValues(secondaryPadding()),
        content = {
            var showDialog by remember { mutableStateOf(false) }
            val isDarkMode = isSystemInDarkTheme()
            val icon = remember(theme) {
                if (theme == AppTheme.System && isDarkMode) {
                    AppTheme.Dark.icon
                } else theme.icon
            }

            Text(
                stringResource(R.string.app_bar_title),
                style = MaterialTheme.typography.h6,
                color = MaterialTheme.colors.onSurface,
            )
            Spacer(modifier = Modifier.weight(1f))
            Row(
                modifier = Modifier
                    .clickable { showDialog = true }
                    .padding(secondaryPadding() / 2),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(secondaryPadding() / 2),
                content = {
                    Icon(
                        imageVector = icon,
                        contentDescription = null,
                    )
                    Text(
                        stringResource(id = theme.title),
                        style = MaterialTheme.typography.button,
                        color = MaterialTheme.colors.onSurface,
                    )
                }
            )

            if (showDialog) {
                AlertDialog(
                    onDismissRequest = { showDialog = false },
                    title = {
                        Text(
                            text = stringResource(R.string.choose_theme),
                            style = MaterialTheme.typography.h5
                        )
                    },
                    shape = MaterialTheme.shapes.large,
                    text = {
                        Column {
                            AppTheme.values().forEach {
                                ListItem(
                                    modifier = Modifier.clickable {
                                        onTheme(it)
                                        showDialog = false
                                    },
                                    icon = {
                                        RadioButton(
                                            selected = it == theme,
                                            onClick = null,
                                        )
                                    },
                                    text = {
                                        Text(it.name)
                                    },
                                )
                            }
                        }
                    },
                    confirmButton = {
                        Button(
                            onClick = { showDialog = false },
                            content = {
                                Text(stringResource(R.string.cancel))
                            }
                        )
                    }
                )
            }
        },
    )
}

@Composable
fun AppBar(
    modifier: Modifier = Modifier,
) {
    val context = LocalContext.current
    val scope = rememberCoroutineScope()
    val repository = remember {
        SettingsRepository.getInstance(context)
    }
    val theme by repository.theme.collectAsState(initial = AppTheme.System)
    val onTheme: (AppTheme) -> Unit = remember {
        { scope.launch { repository.setTheme(it) } }
    }
    AppBar(
        theme = theme,
        modifier = modifier,
        onTheme = onTheme,
    )
}

@Preview
@Composable
private fun AppBarDarkPreview() = CountriesTheme(darkTheme = true) {
    AppBar(theme = AppTheme.Dark, onTheme = {})
}

@Preview
@Composable
private fun AppBarLightPreview() = CountriesTheme(darkTheme = false) {
    AppBar(theme = AppTheme.Light, onTheme = {})
}

@Preview
@Composable
private fun AppBarPreview() = CountriesTheme {
    AppBar(theme = AppTheme.System, onTheme = {})
}