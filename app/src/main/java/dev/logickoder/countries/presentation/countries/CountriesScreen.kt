package dev.logickoder.countries.presentation.countries

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import com.google.accompanist.swiperefresh.SwipeRefresh
import com.google.accompanist.swiperefresh.rememberSwipeRefreshState
import dev.logickoder.countries.data.model.Country
import dev.logickoder.countries.domain.AppTheme
import dev.logickoder.countries.presentation.theme.padding
import dev.logickoder.countries.presentation.widgets.AppBar
import dev.logickoder.countries.presentation.widgets.CountryItem
import dev.logickoder.countries.presentation.widgets.SearchField
import kotlinx.collections.immutable.ImmutableList


@Composable
fun CountriesScreen(
    isRefreshing: Boolean,
    countries: ImmutableList<Country>,
    theme: AppTheme,
    modifier: Modifier = Modifier,
    onRefresh: () -> Unit,
    onTheme: (AppTheme) -> Unit,
    onDetails: (String) -> Unit,
) {
    Scaffold(
        modifier = modifier,
        topBar = {
            AppBar(
                modifier = Modifier.fillMaxWidth(),
                theme = theme,
                onTheme = onTheme,
            )
        },
        content = { scaffoldPadding ->
            val padding = padding()
            val largeShape = MaterialTheme.shapes.large
            val itemModifier = remember {
                Modifier
                    .fillMaxWidth()
                    .padding(top = padding)
                    .clip(largeShape)
            }
            val searchItemModifier = remember {
                Modifier
                    .fillMaxWidth()
                    .padding(bottom = padding)
            }
            val dropdownItemModifier = remember {
                Modifier.fillMaxWidth(0.6f)
            }
            SwipeRefresh(
                modifier = Modifier.padding(scaffoldPadding),
                state = rememberSwipeRefreshState(isRefreshing = isRefreshing),
                onRefresh = onRefresh,
                content = {
                    LazyColumn(
                        contentPadding = PaddingValues(padding),
                        content = {
                            item {
                                SearchField(searchItemModifier)
                            }
                            item {
                                ContinentsDropdownField(dropdownItemModifier)
                            }
                            items(
                                items = countries,
                                key = { it.code },
                                itemContent = { country ->
                                    val onClick = remember(country.code) {
                                        { onDetails(country.code) }
                                    }
                                    CountryItem(
                                        modifier = itemModifier.clickable(onClick = onClick),
                                        name = country.name,
                                        image = country.flag,
                                        population = country.population,
                                        region = country.region,
                                        capital = country.capital,
                                    )
                                }
                            )
                        }
                    )
                }
            )
        },
    )
}