package cat.copernic.abp_project_3.application.presentation.components.switch

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.width
import androidx.compose.material3.ListItem
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp

/**
 * Composable function representing a custom switch component with a title label.
 * This component renders a switch control within a ListItem layout, allowing customization of the title and switch state.
 *
 * @param modifier The modifier to be applied to the ListItem layout.
 * @param title The title or label displayed alongside the switch control.
 * @param value The current value of the switch control (true for checked, false for unchecked).
 * @param onValueChange The callback function invoked when the switch state changes.
 */
@Composable
fun CustomSwitch(
    modifier: Modifier = Modifier,
    title: String,
    value: Boolean,
    onValueChange: (Boolean) -> Unit
) {

    ListItem(
        modifier = modifier
            .fillMaxWidth()
            .clip(MaterialTheme.shapes.medium),
        leadingContent = {
            Text(
                modifier = Modifier
                    .width(75.dp),
                style = MaterialTheme.typography.bodyMedium,
                text = title,
                color = MaterialTheme.colorScheme.secondary
            )
        },
        headlineContent = {
            Box(
                contentAlignment = Alignment.CenterEnd,
                modifier = Modifier.fillMaxWidth()
            ) {
                Switch(
                    checked = value,
                    onCheckedChange = onValueChange
                )
            }
        }
    )
}