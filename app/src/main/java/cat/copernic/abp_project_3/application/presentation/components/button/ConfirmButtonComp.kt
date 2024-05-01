package cat.copernic.abp_project_3.application.presentation.components.button

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.unit.dp

/**
 * Composable method, display a button with a text
 *
 * @param modifier
 * @param title
 * @param onClick
 */
@Composable
fun ConfirmButtonComp(
    modifier: Modifier,
    title: String,
    onClick: () -> Unit
) {
    Button(
        shape = MaterialTheme.shapes.small,
        onClick = onClick,
        modifier = modifier
    ) {
        Text(
            style = MaterialTheme.typography.titleMedium,
            text = title
        )
    }
}

/**
 * Composable method, display a button with an icon
 *
 * @param modifier
 * @param icon
 * @param onClick
 */
@Composable
fun ConfirmButtonComp(
    modifier: Modifier,
    icon: Painter,
    iconDescription: String,
    onClick: () -> Unit
) {
    Button(
        shape = MaterialTheme.shapes.small,
        onClick = onClick,
        modifier = modifier
    ) {
        Icon(
            painter = icon,
            contentDescription = iconDescription
        )
    }
}

/**
 * Composable method, display a button with title and icon
 *
 * @param modifier
 * @param title
 * @param icon
 * @param iconDescription
 * @param onClick
 */
@Composable
fun ConfirmButtonComp(
    modifier: Modifier,
    title: String,
    icon: Painter,
    iconDescription: String,
    onClick: () -> Unit
) {
    Button(
        shape = MaterialTheme.shapes.small,
        onClick = onClick,
        modifier = modifier,
    ) {
        Row(
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Icon(
                painter = icon,
                contentDescription = iconDescription
            )
            Text(
                style = MaterialTheme.typography.titleMedium,
                text = title
            )
        }
    }
}