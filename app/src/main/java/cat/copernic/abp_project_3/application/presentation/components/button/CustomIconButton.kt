package cat.copernic.abp_project_3.application.presentation.components.button

import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.painter.Painter

/**
 * Composable method; display a floating button with an icon
 *
 * @param modifier
 * @param icon
 * @param onClick
 */
@Composable
fun CustomIconButton(
    modifier: Modifier = Modifier,
    icon: Painter,
    description : String,
    onClick: () -> Unit
) {

    FloatingActionButton(
        modifier = modifier,
        shape = MaterialTheme.shapes.medium,
        onClick = onClick
    ) {
        Icon(
            painter = icon,
            contentDescription = description
        )
    }

}