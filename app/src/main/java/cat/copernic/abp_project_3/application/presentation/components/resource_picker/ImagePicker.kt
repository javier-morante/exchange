package cat.copernic.abp_project_3.application.presentation.components.resource_picker

import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Icon
import androidx.compose.material3.ListItem
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import cat.copernic.abp_project_3.R
import coil.compose.AsyncImage
import coil.request.ImageRequest

/**
 * Composable function representing an image picker component.
 * This component allows users to select an image from the device gallery and display it in a ListItem layout.
 *
 * @param modifier The modifier to be applied to the image picker.
 * @param title The title or label displayed alongside the image picker.
 * @param profileImageUrl The URL of the current profile image to display as a placeholder.
 * @param handleImageChange The callback function invoked when a new image is selected.
 */
@Composable
fun ImagePicker(
    modifier: Modifier = Modifier,
    title: String,
    profileImageUrl: String,
    handleImageChange: (Uri?) -> Unit
) {
    val context = LocalContext.current

    var imageUri by rememberSaveable { mutableStateOf<Uri?>(null) }

    val launcher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent()
    ) { uri: Uri? ->
        imageUri = uri
        handleImageChange(uri)
    }

    ListItem(
        modifier = modifier
            .fillMaxWidth()
            .height(125.dp)
            .padding(
                vertical = 4.dp
            ),
        leadingContent = {
            Box(
                modifier = Modifier
                    .width(100.dp)
                    .fillMaxHeight()
            ) {
                Text(
                    modifier = Modifier
                        .fillMaxWidth()
                        .align(Alignment.TopStart),
                    style = MaterialTheme.typography.bodyMedium,
                    text = title,
                    color = MaterialTheme.colorScheme.secondary
                )
            }
        },
        headlineContent = {
            Box(
                contentAlignment = Alignment.TopEnd,
                modifier = Modifier
                    .fillMaxWidth()
            ) {
                Box(
                    contentAlignment = Alignment.Center,
                    modifier = modifier
                        .size(100.dp)
                        .clip(MaterialTheme.shapes.medium)
                        .border(
                            width = 2.dp,
                            color = MaterialTheme.colorScheme.onBackground,
                            shape = MaterialTheme.shapes.medium
                        )
                        .clickable {
                            launcher.launch("image/*")
                        }
                ) {

                    if(imageUri == null) {
                        AsyncImage(
                            model = ImageRequest.Builder(context)
                                .data(profileImageUrl)
                                .crossfade(true)
                                .build(),
                            contentDescription = stringResource(R.string.image_picker_helper),
                            placeholder = painterResource(R.drawable.ic_launcher_background),
                            contentScale = ContentScale.Crop
                        )
                    } else {
                        AsyncImage(
                            model = ImageRequest.Builder(context)
                                .data(imageUri)
                                .crossfade(true)
                                .build(),
                            contentDescription = stringResource(R.string.image_picker_helper),
                            placeholder = painterResource(R.drawable.ic_launcher_background),
                            contentScale = ContentScale.Crop
                        )
                    }

                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Icon(
                            tint = MaterialTheme.colorScheme.onBackground,
                            painter = painterResource(R.drawable.edit),
                            contentDescription = null
                        )
                    }
                }
            }
        }
    )
}