package dev.logickoder.countries.presentation.details

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Card
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import dev.logickoder.countries.domain.CountryDetail
import dev.logickoder.countries.presentation.theme.secondaryPadding

@Composable
fun BorderItem(
    border: CountryDetail.Border,
    modifier: Modifier = Modifier,
    onClick: (String) -> Unit,
) {
    val click = remember {
        {
            onClick(border.code)
        }
    }
    Card(
        modifier = modifier
            .clip(MaterialTheme.shapes.medium)
            .clickable(onClick = click),
        shape = MaterialTheme.shapes.medium,
        elevation = 2.dp,
        content = {
            Row(
                modifier = Modifier.padding(secondaryPadding()),
                content = {
                    Text(border.name)
                },
            )
        }
    )
}