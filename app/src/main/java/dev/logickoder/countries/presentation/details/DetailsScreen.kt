package dev.logickoder.countries.presentation.details

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import com.google.accompanist.flowlayout.FlowRow
import dev.logickoder.countries.R
import dev.logickoder.countries.domain.AppTheme
import dev.logickoder.countries.domain.CountryDetail
import dev.logickoder.countries.presentation.theme.padding
import dev.logickoder.countries.presentation.theme.secondaryPadding
import dev.logickoder.countries.presentation.widgets.AppBar
import dev.logickoder.countries.presentation.widgets.CountryImage
import dev.logickoder.countries.presentation.widgets.CountryTitle
import dev.logickoder.countries.presentation.widgets.TextItem
import dev.logickoder.countries.utils.NumberFormatter

@Composable
fun DetailsScreen(
    detail: CountryDetail,
    theme: AppTheme,
    modifier: Modifier = Modifier,
    onBack: () -> Unit,
    onTheme: (AppTheme) -> Unit,
    onBorder: (String) -> Unit,
) = with(detail) {
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
            Column(
                modifier = Modifier
                    .padding(scaffoldPadding)
                    .verticalScroll(rememberScrollState())
                    .fillMaxSize()
                    .padding(padding),
                verticalArrangement = Arrangement.spacedBy(padding),
                content = {
                    BackButton(onBack = onBack)
                    BoxWithConstraints(
                        modifier = Modifier.fillMaxWidth(),
                        content = {
                            CountryImage(
                                modifier = Modifier.fillMaxWidth(),
                                image = country.flag,
                                width = maxWidth,
                                height = maxWidth
                            )
                        }
                    )
                    Column(
                        verticalArrangement = Arrangement.spacedBy(secondaryPadding()),
                        content = {
                            CountryTitle(country.name)
                            Spacer(modifier = Modifier.height(padding / 2))
                            TextItem(
                                title = stringResource(id = R.string.native_name),
                                content = country.nativeName,
                            )
                            TextItem(
                                title = stringResource(id = R.string.population),
                                content = NumberFormatter.format(country.population),
                            )
                            TextItem(
                                title = stringResource(R.string.region),
                                content = country.region,
                            )
                            TextItem(
                                title = stringResource(R.string.sub_region),
                                content = country.subregion,
                            )
                            TextItem(
                                title = stringResource(R.string.capital),
                                content = country.capital,
                            )
                        }
                    )
                    Column(
                        verticalArrangement = Arrangement.spacedBy(secondaryPadding()),
                        content = {
                            TextItem(
                                title = stringResource(id = R.string.tld),
                                content = country.domains.joinToString(),
                            )
                            TextItem(
                                title = stringResource(id = R.string.currencies),
                                content = country.currencies.joinToString(),
                            )
                            TextItem(
                                title = stringResource(id = R.string.languages),
                                content = country.languages.joinToString(),
                            )
                        }
                    )
                    Column(
                        verticalArrangement = Arrangement.spacedBy(secondaryPadding()),
                        content = {
                            TextItem(
                                title = stringResource(id = R.string.border),
                                content = "",
                                style = MaterialTheme.typography.h6,
                            )
                            FlowRow(
                                modifier = Modifier.fillMaxWidth(),
                                crossAxisSpacing = padding / 2,
                                mainAxisSpacing = padding / 2,
                                content = {
                                    borders.forEach {
                                        BorderItem(
                                            border = it,
                                            onClick = onBorder,
                                        )
                                    }
                                }
                            )
                        }
                    )
                }
            )
        },
    )
}