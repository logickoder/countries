package dev.logickoder.countries.presentation.widgets

import androidx.compose.foundation.layout.Row
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight

@Composable
fun TextItem(
    title: String,
    content: String,
    modifier: Modifier = Modifier,
) {
    Row(
        modifier = modifier,
        content = {
            Text(
                "$title: ",
                style = MaterialTheme.typography.body1.copy(
                    fontWeight = FontWeight.Bold,
                )
            )
            Text(
                content,
                style = MaterialTheme.typography.body1,
            )
        },
    )
}