package cat.copernic.abp_project_3.application.presentation.components.text_field

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.widthIn
import androidx.compose.material3.ListItem
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp

/**
 * Composable function representing a custom text field component with a title label.
 * This component renders a text field within a ListItem layout, allowing customization of the title and text input.
 *
 * @param modifier The modifier to be applied to the ListItem layout.
 * @param title The title or label displayed alongside the text field.
 * @param value The current value of the text field.
 * @param onValueChange The callback function invoked when the text field value changes.
 */
@Composable
fun CustomTextField(
    modifier: Modifier = Modifier,
    title: String,
    value: String,
    onValueChange: (String) -> Unit
) {
    ListItem(
        modifier = modifier
            .fillMaxWidth(),
        leadingContent = {
            Text(
                modifier = Modifier
                    .widthIn(max = 100.dp),
                style = MaterialTheme.typography.bodyMedium,
                text = title,
                color = MaterialTheme.colorScheme.secondary
            )
        },
        headlineContent = {
            TextField(
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(MaterialTheme.shapes.medium),
                value = value,
                onValueChange = onValueChange,
                singleLine = true,
                colors = TextFieldDefaults.colors(
                    disabledTextColor = Color.Transparent,
                    unfocusedContainerColor = Color.Transparent,
                    focusedIndicatorColor = Color.Transparent,
                    focusedContainerColor = MaterialTheme.colorScheme.secondaryContainer,
                    unfocusedIndicatorColor = Color.Transparent,
                    disabledIndicatorColor = Color.Transparent
                ),
                textStyle = MaterialTheme.typography.bodySmall.copy(
                    textAlign = TextAlign.End
                )
            )
        }
    )
}


