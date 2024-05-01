package cat.copernic.abp_project_3.application.presentation.components.button

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material3.Icon
import androidx.compose.material3.ListItem
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.unit.dp

/**
 * Composable method , shows a list item with an icon and a title
 *
 * @param modifier
 * @param title
 * @param leadingIcon
 * @param onClick
 */
@Composable
fun StylizedButtonComponent(
    modifier: Modifier = Modifier,
    title: String,
    leadingIcon: Painter,
    onClick: () -> Unit
) {
    Column {
        ListItem(
            modifier = modifier
                .fillMaxWidth()
                .clip(MaterialTheme.shapes.medium)
                .clickable { onClick() },
            leadingContent = {
                Icon(
                    painter = leadingIcon,
                    contentDescription = null
                )
            },
            headlineContent = {
                Text(text = title)
            }
        )

        Spacer(modifier = Modifier.height(8.dp))
    }
}