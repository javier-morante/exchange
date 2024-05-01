package cat.copernic.abp_project_3.application.presentation.components.label

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.width
import androidx.compose.material3.ListItem
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp

/**
 *  A list item that have a  a title and a text inside to show information
 *
 * @param modifier
 * @param title
 * @param value
 */
@Composable
fun CustomInfoLabel(
    modifier: Modifier = Modifier,
    title: String,
    value: String,
) {
    ListItem(
        modifier = modifier
            .fillMaxWidth(),
        leadingContent = {
            Text(
                modifier = Modifier
                    .width(100.dp),
                style = MaterialTheme.typography.bodyMedium,
                text = title,
                color = MaterialTheme.colorScheme.secondary
            )
        },
        headlineContent = {
            Text(
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(MaterialTheme.shapes.medium),
                text = value,
                textAlign = TextAlign.Start,
            )
        }
    )
}