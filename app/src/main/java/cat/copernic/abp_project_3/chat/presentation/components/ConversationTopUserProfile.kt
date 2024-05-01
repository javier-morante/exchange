package cat.copernic.abp_project_3.chat.presentation.components

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
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
import cat.copernic.abp_project_3.application.data.model.Profile
import coil.compose.AsyncImage
import coil.request.ImageRequest

/**
 * Composable function that displays the top user profile in a conversation header.
 *
 * @param modifier The modifier for positioning and sizing the component.
 * @param profile The profile information of the user to be displayed.
 */
@Composable
fun ConversationTopUserProfile(
    modifier: Modifier = Modifier,
    profile: Profile
) {
    val context = LocalContext.current

    ListItem(
        modifier = modifier.fillMaxWidth(),
        leadingContent = {
            AsyncImage(
                modifier = Modifier
                    .size(40.dp)
                    .clip(MaterialTheme.shapes.medium),
                model = ImageRequest.Builder(context)
                    .data(profile.imageUrl)
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
                text = "${profile.name} ${profile.surname}"
            )
        }
    )
}