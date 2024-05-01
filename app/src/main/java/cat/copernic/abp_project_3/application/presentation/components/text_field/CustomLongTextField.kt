package cat.copernic.abp_project_3.application.presentation.components.text_field

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
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
 * Composable function representing a custom long text field component with a title label.
 * This component renders a multi-line text field within a ListItem layout, allowing customization of the title and text input.
 *
 * @param modifier The modifier to be applied to the ListItem layout.
 * @param title The title or label displayed alongside the multi-line text field.
 * @param value The current value of the multi-line text field.
 * @param onValueChange The callback function invoked when the text field value changes.
 */
@Composable
fun CustomLongTextField(
    modifier: Modifier = Modifier,
    title: String,
    value: String,
    onValueChange: (String) -> Unit
) {
    ListItem(
        modifier = modifier
            .fillMaxWidth()
            .height(125.dp),
        leadingContent = {
            Box(
                modifier = Modifier
                    .widthIn(max = 100.dp)
                    .fillMaxHeight()
            ) {
                Text(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 20.dp),
                    style = MaterialTheme.typography.bodyMedium,
                    text = title,
                    color = MaterialTheme.colorScheme.secondary
                )
            }
        },
        headlineContent = {
            TextField(
                modifier = Modifier
                    .fillMaxSize()
                    .clip(MaterialTheme.shapes.medium),
                value = value,
                onValueChange = onValueChange,
                minLines = 1,
                maxLines = 5,
                colors = TextFieldDefaults.colors(
                    disabledTextColor = Color.Transparent,
                    unfocusedContainerColor = Color.Transparent,
                    focusedIndicatorColor = Color.Transparent,
                    focusedContainerColor = MaterialTheme.colorScheme.secondaryContainer,
                    unfocusedIndicatorColor = Color.Transparent,
                    disabledIndicatorColor = Color.Transparent
                ),
                textStyle = MaterialTheme.typography.bodySmall.copy(
                    textAlign = TextAlign.Start
                )
            )
        }
    )
}