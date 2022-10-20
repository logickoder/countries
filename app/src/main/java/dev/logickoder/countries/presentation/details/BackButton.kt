package dev.logickoder.countries.presentation.details

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.Card
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import dev.logickoder.countries.R
import dev.logickoder.countries.presentation.theme.secondaryPadding

@Composable
fun BackButton(
    modifier: Modifier = Modifier,
    onBack: () -> Unit,
) {
    Card(
        modifier = modifier
            .clip(MaterialTheme.shapes.medium)
            .clickable(onClick = onBack),
        shape = MaterialTheme.shapes.medium,
        elevation = 2.dp,
        content = {
            Row(
                modifier = Modifier.padding(secondaryPadding()),
                content = {
                    Icon(
                        imageVector = Icons.Outlined.ArrowBack,
                        contentDescription = null,
                    )
                    Spacer(modifier = Modifier.width(secondaryPadding() / 2))
                    Text(stringResource(R.string.back))
                },
            )
        }
    )
}