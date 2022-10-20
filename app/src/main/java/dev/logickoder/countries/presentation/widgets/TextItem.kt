package dev.logickoder.countries.presentation.widgets

import androidx.compose.foundation.layout.Row
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight

@Composable
fun TextItem(
    title: String,
    content: String,
    modifier: Modifier = Modifier,
    style: TextStyle = MaterialTheme.typography.body1
) {
    Row(
        modifier = modifier,
        content = {
            Text(
                "$title: ",
                style = style.copy(
                    fontWeight = FontWeight.Bold,
                )
            )
            Text(
                content,
                style = style,
            )
        },
    )
}