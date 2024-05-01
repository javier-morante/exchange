package cat.copernic.abp_project_3.chat.presentation.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.ListItem
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import cat.copernic.abp_project_3.R
import cat.copernic.abp_project_3.application.presentation.components.divider.CustomDivider
import coil.compose.AsyncImage
import coil.request.ImageRequest

/**
 * Composable function that displays a single conversation item within a list.
 *
 * @param modifier The modifier for positioning and sizing the component.
 * @param title The title or name of the conversation.
 * @param imageUrl The URL string representing the image associated with the conversation.
 * @param onClick The lambda function to execute when the conversation item is clicked.
 */
@Composable
fun ConversationListComponent(
    modifier: Modifier = Modifier,
    title: String,
    imageUrl: String,
    onClick: () -> Unit
) {
    val context = LocalContext.current

    Column(
        modifier = modifier
            .fillMaxWidth()
            .clickable { onClick() }
    ) {
        ListItem(
            modifier = Modifier.fillMaxWidth(),
            leadingContent = {
                AsyncImage(
                    modifier = Modifier
                        .size(75.dp)
                        .clip(MaterialTheme.shapes.medium),
                    model = ImageRequest.Builder(context)
                        .data(imageUrl)
                        .crossfade(true)
                        .build(),
                    contentDescription = null,
                    placeholder = painterResource(R.drawable.ic_launcher_background),
                    contentScale = ContentScale.Crop
                )
            },
            headlineContent = {
                Text(
                    modifier = Modifier,
                    style = MaterialTheme.typography.titleLarge,
                    text = title
                )
            },
            trailingContent = {
                Icon(
                    painter = painterResource(R.drawable.navigate_next),
                    contentDescription = null
                )
            }
        )

        CustomDivider()
    }
}