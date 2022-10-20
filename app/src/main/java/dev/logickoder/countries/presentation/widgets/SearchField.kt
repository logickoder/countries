package dev.logickoder.countries.presentation.widgets

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.Card
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.material.TextFieldDefaults
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Search
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import dev.logickoder.countries.R
import dev.logickoder.countries.rememberCountriesViewModel

@Composable
private fun SearchField(
    search: String,
    onSearch: (String) -> Unit,
    modifier: Modifier = Modifier,
) {
    Card(
        modifier = modifier.clip(MaterialTheme.shapes.large),
        elevation = 2.dp,
        content = {
            OutlinedTextField(
                value = search,
                onValueChange = onSearch,
                modifier = Modifier.fillMaxWidth(),
                singleLine = true,
                colors = TextFieldDefaults.outlinedTextFieldColors(
                    focusedBorderColor = Color.Transparent,
                    unfocusedBorderColor = Color.Transparent,
                    backgroundColor = MaterialTheme.colors.surface,
                ),
                leadingIcon = {
                    Icon(
                        imageVector = Icons.Outlined.Search,
                        contentDescription = null,
                    )
                },
                placeholder = {
                    Text(stringResource(R.string.search))
                }
            )
        }
    )
}

@Composable
fun SearchField(
    modifier: Modifier = Modifier,
) {
    val viewModel = rememberCountriesViewModel()
    val search by viewModel.search.collectAsState()
    SearchField(
        modifier = modifier,
        search = search,
        onSearch = viewModel::search,
    )
}