package cat.copernic.abp_project_3.application.presentation.components.profile_info

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
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
import cat.copernic.abp_project_3.application.data.model.Profile
import coil.compose.AsyncImage
import coil.request.ImageRequest

@Composable
fun UserProfileComponent(
    modifier: Modifier = Modifier,
    userProfile: Profile,
    userEmail: String,
    handleNavigation: () -> Unit
) {
    val context = LocalContext.current

    ListItem(
        modifier = modifier
            .fillMaxWidth()
            .height(100.dp)
            .clip(MaterialTheme.shapes.medium)
            .clickable { handleNavigation() },
        leadingContent = {
            AsyncImage(
                modifier = Modifier
                    .size(75.dp)
                    .clip(MaterialTheme.shapes.large),
                model = ImageRequest.Builder(context)
                    .data(userProfile.imageUrl)
                    .crossfade(true)
                    .build(),
                contentDescription = null,
                placeholder = painterResource(R.drawable.ic_launcher_background),
                contentScale = ContentScale.Crop
            )
        },
        headlineContent = {
            Column {
                Text(
                    style = MaterialTheme.typography.titleLarge,
                    text = "${userProfile.name} ${userProfile.surname}"
                )
                Text(
                    style = MaterialTheme.typography.titleSmall,
                    text = userEmail
                )
            }
        },
        trailingContent = {
            Icon(
                painter = painterResource(R.drawable.navigate_next),
                contentDescription = null
            )
        }
    )
}