package dev.logickoder.countries.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import com.bumble.appyx.core.modality.BuildContext
import com.bumble.appyx.core.node.Node
import dev.logickoder.countries.CountriesViewModel
import dev.logickoder.countries.presentation.countries.CountriesScreen
import kotlinx.collections.immutable.toImmutableList

class CountriesRoute(
    buildContext: BuildContext,
) : Node(buildContext) {


    @Composable
    override fun View(modifier: Modifier) {
        val context = LocalContext.current
        val viewModel = remember {
            CountriesViewModel.getInstance(context)
        }
        LaunchedEffect(key1 = Unit, block = {
            viewModel.refreshCountries()
        })
        val countries by viewModel.countries.collectAsState(initial = emptyList())
        CountriesScreen(
            countries = countries.toImmutableList(),
            modifier = modifier,
        )
    }
}