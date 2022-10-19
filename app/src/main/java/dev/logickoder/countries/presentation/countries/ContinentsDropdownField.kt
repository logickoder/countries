package dev.logickoder.countries.presentation.countries

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import dev.logickoder.countries.CountriesViewModel
import dev.logickoder.countries.domain.Continent
import dev.logickoder.countries.presentation.widgets.DropdownField

@Composable
fun ContinentsDropdownField(
    modifier: Modifier = Modifier,
) {
    val context = LocalContext.current
    val viewModel = remember {
        CountriesViewModel.getInstance(context)
    }
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