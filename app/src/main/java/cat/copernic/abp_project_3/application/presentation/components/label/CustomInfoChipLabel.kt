package cat.copernic.abp_project_3.application.presentation.components.label

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FilterChip
import androidx.compose.material3.ListItem
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp

/**
 * Composable method that have a title and a value in a filter that is clickable
 *
 * @param modifier
 * @param title
 * @param value
 * @param onClick
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CustomInfoChipLabel(
    modifier: Modifier = Modifier,
    title: String,
    value: String,
    onClick: () -> Unit
) {
    ListItem(
        modifier = modifier,
        leadingContent = {
            Text(
                modifier = Modifier,
                style = MaterialTheme.typography.bodyMedium,
                text = title,
                color = MaterialTheme.colorScheme.secondary
            )
        },
        headlineContent = {
            FilterChip(
                modifier = Modifier
                    .padding(8.dp),
                selected = true,
                onClick = onClick,
                label = {
                    Text(
                        modifier = Modifier,
                        text = value,
                        textAlign = TextAlign.Start,
                    )
                }
            )
        }
    )
}