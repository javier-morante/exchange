package cat.copernic.abp_project_3.offers_catalogue.presentation.offer_details.offer_details

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import cat.copernic.abp_project_3.R
import cat.copernic.abp_project_3.application.presentation.components.button.ConfirmButtonComp
import cat.copernic.abp_project_3.application.presentation.components.divider.CustomDivider
import cat.copernic.abp_project_3.application.presentation.components.label.CustomInfoChipLabel
import cat.copernic.abp_project_3.application.presentation.components.label.CustomInfoLabel
import cat.copernic.abp_project_3.application.presentation.components.label.CustomLongInfoLabel
import cat.copernic.abp_project_3.application.data.model.Category
import cat.copernic.abp_project_3.application.data.model.Offer
import cat.copernic.abp_project_3.application.data.model.Profile
import coil.compose.AsyncImage
import coil.request.ImageRequest
import java.text.SimpleDateFormat
import java.util.Locale

/**
 * Composable function representing the details component of an offer.
 *
 * This function displays detailed information about a specific offer, including images, description,
 * category, and related profile details. It also provides actions for viewing the owner's profile
 * and initiating a conversation.
 *
 * @param modifier The modifier for the component layout.
 * @param offer The offer object containing information to be displayed.
 * @param profile The profile associated with the offer owner.
 * @param category The category of the offer.
 * @param authUserProfileStatus The status indicating if the current user's profile is authenticated.
 * @param authUserUid The unique identifier of the authenticated user.
 * @param handleCreateConversation Callback function to handle the creation of a conversation.
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun OfferDetailsComponent(
    modifier: Modifier = Modifier,
    offer: Offer,
    profile: Profile,
    category: Category,
    authUserProfileStatus: Boolean = false,
    authUserUid: String = "",
    handleCreateConversation: () -> Unit
) {

    val context = LocalContext.current

    val dateFormat = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())

    var ownerProfileDialogState by rememberSaveable {
        mutableStateOf(false)
    }

    var categoryDataDialogState by rememberSaveable {
        mutableStateOf(false)
    }

    Box(
        modifier = modifier.fillMaxSize()
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
        ) {

            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(250.dp)
            ) {

                AsyncImage(
                    model = ImageRequest.Builder(context)
                        .data(offer.imageUrl)
                        .crossfade(true)
                        .build(),
                    contentDescription = null,
                    placeholder = painterResource(R.drawable.ic_launcher_background),
                    contentScale = ContentScale.Crop,
                    modifier = Modifier
                        .fillMaxSize()
                )

                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(
                            brush = Brush.verticalGradient(
                                colors = listOf(
                                    Color.Transparent,
                                    colorResource(R.color.transluciend_black)
                                )
                            )
                        )
                )

                Column(
                    modifier = Modifier
                        .align(Alignment.BottomStart)
                        .padding(16.dp)
                ) {
                    Text(
                        style = MaterialTheme.typography.displayMedium,
                        color = Color.White,
                        text = offer.myItem
                    )

                    Row(
                        horizontalArrangement = Arrangement.spacedBy(8.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Icon(
                            painter = painterResource(R.drawable.change_circle),
                            contentDescription = null,
                            tint = Color.White,
                            modifier = Modifier
                                .size(30.dp)
                        )

                        Text(
                            style = MaterialTheme.typography.displaySmall,
                            color = Color.White,
                            text = offer.desiredItem
                        )
                    }
                }


                AsyncImage(
                    model = ImageRequest.Builder(context)
                        .data(profile.imageUrl)
                        .crossfade(true)
                        .build(),
                    contentDescription = stringResource(R.string.offer_owner_image),
                    placeholder = painterResource(R.drawable.ic_launcher_background),
                    contentScale = ContentScale.Crop,
                    modifier = Modifier
                        .size(75.dp)
                        .padding(8.dp)
                        .align(Alignment.BottomEnd)
                        .clip(MaterialTheme.shapes.medium)
                        .clickable {
                            ownerProfileDialogState = true
                        }
                )

            }

            CustomLongInfoLabel(
                modifier = Modifier
                    .padding(16.dp),
                title = stringResource(R.string.offer_details_description_label),
                value = offer.description
            )

            CustomDivider(space = 2.dp)

            Column(
                modifier = Modifier
                    .padding(8.dp)
            ) {
                CustomInfoLabel(
                    title = stringResource(R.string.conversations_conversation_message_date_label),
                    value = dateFormat.format(offer.date.toDate())
                )

                CustomInfoLabel(
                    title = stringResource(R.string.offer_details_offer_average_rating_label),
                    value = "%.1f".format(offer.averageStars)
                )

                CustomInfoChipLabel(
                    title = stringResource(R.string.offer_details_category_ref_label),
                    value = category.title,
                    onClick = {
                        categoryDataDialogState = true
                    }
                )

                CustomInfoLabel(
                    title = stringResource(R.string.offer_details_offer_state_label),
                    value = offer.offerState.title
                )

                Spacer(modifier = Modifier.height(48.dp))
            }

            // User profile info dialog
            if (ownerProfileDialogState) {
                Dialog(
                    onDismissRequest = {
                        ownerProfileDialogState = false
                    },
                ) {
                    Card(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(8.dp),
                        shape = RoundedCornerShape(16.dp),
                    ) {

                        Column(
                            modifier = Modifier.padding(16.dp)
                        ) {

                            Row(
                                verticalAlignment = Alignment.Bottom,
                                horizontalArrangement = Arrangement
                                    .spacedBy(8.dp)
                            ) {
                                AsyncImage(
                                    model = ImageRequest.Builder(context)
                                        .data(profile.imageUrl)
                                        .crossfade(true)
                                        .build(),
                                    contentDescription = null,
                                    placeholder = painterResource(R.drawable.ic_launcher_background),
                                    contentScale = ContentScale.Crop,
                                    modifier = Modifier
                                        .size(75.dp)
                                        .clip(MaterialTheme.shapes.medium)
                                )

                                Text(
                                    modifier = Modifier.padding(bottom = 8.dp),
                                    style = MaterialTheme.typography.titleLarge,
                                    text = "${profile.name} ${profile.surname}"
                                )
                            }

                            Spacer(modifier = Modifier.height(16.dp))

                            Text(
                                style = MaterialTheme.typography.titleMedium,
                                text = profile.description
                            )

                            Spacer(modifier = Modifier.height(16.dp))

                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.End
                            ) {
                                ConfirmButtonComp(
                                    modifier = Modifier,
                                    title = stringResource(R.string.offer_details_close_profile_info_dialog),
                                    onClick = { ownerProfileDialogState = false }
                                )
                            }
                        }
                    }
                }
            }

            if (categoryDataDialogState) {
                ModalBottomSheet(
                    onDismissRequest = {
                        categoryDataDialogState = false
                    },
                    sheetState = rememberModalBottomSheetState()
                ) {

                    Column(
                        modifier = Modifier
                            .fillMaxWidth(),
                        verticalArrangement = Arrangement.spacedBy(10.dp)
                    ) {
                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(175.dp)
                                .padding(16.dp)
                                .clip(MaterialTheme.shapes.medium),

                            ) {

                            AsyncImage(
                                model = ImageRequest.Builder(context)
                                    .data(category.imageUrl)
                                    .crossfade(true)
                                    .build(),
                                contentDescription = null,
                                placeholder = painterResource(R.drawable.ic_launcher_background),
                                contentScale = ContentScale.Crop,
                                modifier = Modifier
                                    .fillMaxSize()
                            )

                            Box(
                                modifier = Modifier
                                    .fillMaxSize()
                                    .background(
                                        Brush.verticalGradient(
                                            colors = listOf(
                                                Color.Transparent,
                                                colorResource(R.color.transluciend_black)
                                            ),
                                            startY = 100f
                                        )
                                    ),
                                contentAlignment = Alignment.BottomStart
                            ) {
                                Text(
                                    text = category.title,
                                    color = Color.White,
                                    fontSize = 40.sp,
                                    fontWeight = FontWeight.Bold,
                                    modifier = Modifier.padding(16.dp)
                                )
                            }

                        }

                        Text(
                            text = category.description,
                            fontSize = 20.sp,
                            modifier = Modifier.padding(horizontal = 16.dp)
                        )
                        Spacer(modifier = Modifier.height(48.dp))
                    }

                }
            }
        }

        if (authUserProfileStatus && offer.ownerRef != authUserUid) {
            ConfirmButtonComp(
                modifier = Modifier
                    .fillMaxWidth()
                    .align(Alignment.BottomCenter)
                    .padding(16.dp),
                icon = painterResource(R.drawable.chat_bubble),
                iconDescription = stringResource(R.string.start_conversation_button_helper),
                onClick = handleCreateConversation
            )
        }
    }
}