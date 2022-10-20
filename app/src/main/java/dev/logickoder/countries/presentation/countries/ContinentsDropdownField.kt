package dev.logickoder.countries.presentation.countries

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import dev.logickoder.countries.domain.Continent
import dev.logickoder.countries.presentation.widgets.DropdownField
import dev.logickoder.countries.rememberCountriesViewModel

@Composable
fun ContinentsDropdownField(
    modifier: Modifier = Modifier,
) {
    val viewModel = rememberCountriesViewModel()
    val region by viewModel.region.collectAsState()
    val continents = remember {
        Continent.values().sortedBy { it.readableName }
    }
    DropdownField(
        suggested = region,
        suggestions = continents,
        modifier = modifier,
        onSuggestionSelected = viewModel::updateRegion,
    )
}