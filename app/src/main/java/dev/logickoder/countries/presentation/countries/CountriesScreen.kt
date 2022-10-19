package dev.logickoder.countries.presentation.countries

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import dev.logickoder.countries.data.model.Country
import dev.logickoder.countries.presentation.theme.padding
import dev.logickoder.countries.presentation.widgets.AppBar
import dev.logickoder.countries.presentation.widgets.CountryItem
import dev.logickoder.countries.presentation.widgets.SearchField
import kotlinx.collections.immutable.ImmutableList


@Composable
fun CountriesScreen(
    countries: ImmutableList<Country>,
    modifier: Modifier = Modifier,
) {
    Scaffold(
        modifier = modifier,
        topBar = {
            AppBar(modifier = Modifier.fillMaxWidth())
        },
        content = { scaffoldPadding ->
            val padding = padding()
            val itemModifier = remember {
                Modifier
                    .fillMaxWidth()
                    .padding(vertical = padding)
            }
            val dropdownItemModifier = remember {
                Modifier
                    .fillMaxWidth(0.5f)
                    .padding(vertical = padding)
            }
            LazyColumn(
                modifier = Modifier.padding(scaffoldPadding),
                contentPadding = PaddingValues(padding),
                content = {
                    item {
                        SearchField(itemModifier)
                    }
                    item {
                        ContinentsDropdownField(dropdownItemModifier)
                    }
                    items(
                        items = countries,
                        key = { it.code },
                        itemContent = { country ->
                            CountryItem(
                                modifier = itemModifier,
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
        },
    )
}