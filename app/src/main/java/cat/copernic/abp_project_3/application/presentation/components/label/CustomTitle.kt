package cat.copernic.abp_project_3.application.presentation.components.label

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

/**
 *  Composable method that shows a title
 *
 * @param modifier Style modifier by default 8 dp of padding
 * @param title The text title of component
 * @param color The color of text
 */
@Composable
fun CustomTitle(
    modifier: Modifier = Modifier,
    title: String,
    color: Color = Color.Unspecified
) {
    Text(
        modifier = modifier.padding(8.dp),
        style = MaterialTheme.typography.titleLarge,
        text = title,
        color = color
    )
}