package dev.logickoder.countries.presentation.widgets

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.Card
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import coil.compose.AsyncImagePainter
import coil.request.CachePolicy
import coil.request.ImageRequest
import com.google.accompanist.placeholder.PlaceholderHighlight
import com.google.accompanist.placeholder.material.shimmer
import com.google.accompanist.placeholder.placeholder
import dev.logickoder.countries.R
import dev.logickoder.countries.presentation.theme.CountriesTheme
import dev.logickoder.countries.presentation.theme.padding
import dev.logickoder.countries.presentation.theme.secondaryPadding
import dev.logickoder.countries.utils.NumberFormatter

@Composable
fun CountryItem(
    name: String,
    image: String,
    population: Int,
    region: String,
    capital: String,
    modifier: Modifier = Modifier,
) {
    BoxWithConstraints(
        modifier = modifier,
        content = {
            val (width, height) = maxWidth to maxWidth / 2
            Card(
                shape = MaterialTheme.shapes.large,
                elevation = 2.dp,
                content = {
                    Column(
                        content = {
                            CountryImage(
                                modifier = Modifier.size(width, height),
                                image = image,
                                width = width,
                                height = height,
                            )
                            Column(
                                modifier = Modifier.padding(padding()),
                                verticalArrangement = Arrangement.spacedBy(secondaryPadding() / 2),
                                content = {
                                    CountryTitle(name)
                                    Spacer(modifier = Modifier.height(padding()))
                                    TextItem(
                                        title = stringResource(id = R.string.population),
                                        content = NumberFormatter.format(population),
                                    )
                                    TextItem(
                                        title = stringResource(R.string.region),
                                        content = region,
                                    )
                                    TextItem(
                                        title = stringResource(R.string.capital),
                                        content = capital,
                                    )
                                }
                            )
                        }
                    )
                }
            )
        }
    )
}

@Composable
fun CountryImage(
    image: String,
    width: Dp,
    height: Dp,
    modifier: Modifier = Modifier,
    contentScale: ContentScale = ContentScale.Crop,
) {
    val context = LocalContext.current
    var isLoading by remember {
        mutableStateOf(true)
    }
    val model = remember {
        ImageRequest.Builder(context)
            .data(image)
            .size(width.value.toInt(), height.value.toInt())
            .memoryCacheKey(image)
            .diskCacheKey(image)
            .diskCachePolicy(CachePolicy.ENABLED)
            .memoryCachePolicy(CachePolicy.ENABLED)
            .build()
    }
    AsyncImage(
        model = model,
        onState = { state ->
            isLoading = state is AsyncImagePainter.State.Loading
        },
        contentDescription = null,
        contentScale = contentScale,
        modifier = modifier
            .placeholder(
                visible = isLoading,
                highlight = PlaceholderHighlight.shimmer(),
                color = MaterialTheme.colors.surface,
            )
    )
}

@Preview(showBackground = true)
@Composable
private fun CountryItemPreview() = CountriesTheme {
    CountryItem(
        name = "Iceland",
        image = "https://flagcdn.com/w320/ar.png",
        population = 334000,
        region = "Europe",
        capital = "Reykjavik",
    )
}