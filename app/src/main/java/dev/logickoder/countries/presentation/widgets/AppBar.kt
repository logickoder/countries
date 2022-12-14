package dev.logickoder.countries.presentation.widgets

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Card
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.Icon
import androidx.compose.material.ListItem
import androidx.compose.material.MaterialTheme
import androidx.compose.material.RadioButton
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import dev.logickoder.countries.R
import dev.logickoder.countries.domain.AppTheme
import dev.logickoder.countries.presentation.theme.CountriesTheme
import dev.logickoder.countries.presentation.theme.padding
import dev.logickoder.countries.presentation.theme.secondaryPadding

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun AppBar(
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
                Dialog(
                    onDismissRequest = { showDialog = false },
                    content = {
                        Column(
                            modifier = Modifier
                                .clip(MaterialTheme.shapes.large)
                                .background(MaterialTheme.colors.surface)
                                .padding(padding()),
                            verticalArrangement = Arrangement.spacedBy(secondaryPadding())
                        ) {
                            Text(
                                text = stringResource(R.string.choose_theme),
                                style = MaterialTheme.typography.h5
                            )
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
                            Card(
                                modifier = modifier
                                    .clip(MaterialTheme.shapes.medium)
                                    .clickable(onClick = { showDialog = false }),
                                shape = MaterialTheme.shapes.medium,
                                backgroundColor = MaterialTheme.colors.background,
                                elevation = 2.dp,
                                content = {
                                    Row(
                                        modifier = Modifier.padding(secondaryPadding()),
                                        content = {
                                            Text(stringResource(id = R.string.cancel))
                                        },
                                    )
                                }
                            )
                        }
                    },
                )
            }
        },
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