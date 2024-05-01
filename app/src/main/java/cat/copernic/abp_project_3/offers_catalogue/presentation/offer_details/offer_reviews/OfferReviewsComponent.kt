package cat.copernic.abp_project_3.offers_catalogue.presentation.offer_details.offer_reviews

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.outlined.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.hilt.navigation.compose.hiltViewModel
import cat.copernic.abp_project_3.R
import cat.copernic.abp_project_3.application.presentation.components.text_field.TextFieldComp
import cat.copernic.abp_project_3.application.data.enums.UserRole
import cat.copernic.abp_project_3.application.data.model.Offer
import cat.copernic.abp_project_3.application.data.model.Profile
import cat.copernic.abp_project_3.offers_catalogue.domain.offer_details.CreateReviewViewModel
import cat.copernic.abp_project_3.offers_catalogue.domain.offer_details.OfferOverviewViewModel
import cat.copernic.abp_project_3.offers_catalogue.presentation.catalogue.components.RatingBar
import cat.copernic.abp_project_3.offers_catalogue.presentation.offer_details.components.ReviewItem

/**
 * Composable function representing a component for displaying reviews related to a specific offer.
 *
 * This function displays reviews associated with a given offer, allows users to add new reviews,
 * and provides options for reviewing the offer.
 *
 * @param offerUid The unique identifier of the offer for which reviews are displayed.
 * @param offerOverviewViewModel The view model providing data and logic for offer overview.
 * @param createReviewViewModel The view model providing data and logic for creating reviews.
 */
@Composable
fun OfferReviewsComponent(
    offerUid: String,
    offerOverviewViewModel: OfferOverviewViewModel = hiltViewModel(),
    createReviewViewModel: CreateReviewViewModel = hiltViewModel(),
) {
    LaunchedEffect(key1 = offerUid) {
        offerOverviewViewModel.setSelectedOffer(offerUid)
        offerOverviewViewModel.fetchCurrentUserProfile()
    }

    val currentUserProfileState by offerOverviewViewModel.currentUserProfile.collectAsState()
    val currentUserProfile = currentUserProfileState ?: Profile()

    val isAdmin = currentUserProfile.role == UserRole.ADMINISTRATOR

    val offerState by offerOverviewViewModel.offer.collectAsState()
    val offer = offerState ?: Offer()

    val offerReviewsList = offerOverviewViewModel.reviews

    val userState by offerOverviewViewModel.user.collectAsState()

    val reviewProfilePairs = offerOverviewViewModel.reviewProfilePairs

    val review by createReviewViewModel.review.collectAsState()

    var createReviewDialogStatus by rememberSaveable {
        mutableStateOf(false)
    }

    Column(
        modifier = Modifier.fillMaxSize()
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp)
        ) {
            Box(
                modifier = Modifier.fillMaxWidth(),
                contentAlignment = Alignment.CenterEnd
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Text(
                        text = stringResource(R.string.reviews_list_label),
                        style = TextStyle(fontSize = 25.sp)
                    )
                    Spacer(modifier = Modifier.weight(1f))

                    if(offerState?.ownerRef != currentUserProfile.id)  {
                        IconButton(
                            onClick = {
                                createReviewDialogStatus = true
                            }
                        ) {
                            Icon(
                                painter = painterResource(R.drawable.baseline_add_circle_outline),
                                contentDescription = null
                            )
                        }
                    }
                }
            }
            Box(
                modifier = Modifier.fillMaxWidth(),
                contentAlignment = Alignment.TopCenter
            ) {
                Divider(
                    thickness = 2.dp,
                    color = colorResource(R.color.primaryColor)
                )
                Spacer(modifier = Modifier.height(3.dp))
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                ) {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .weight(1f)
                    ) {
                        LazyColumn {
                            items(offerReviewsList) { review ->
                                val profile = reviewProfilePairs.find { it.first == review }?.second
                                if (profile != null) {
                                    ReviewItem(
                                        review = review,
                                        profile = profile,
                                        isAdmin = isAdmin,
                                        createReviewViewModel = createReviewViewModel,
                                        offerId = offer.id
                                    )
                                }
                            }
                        }
                    }
                }
            }
        }
    }


    if (createReviewDialogStatus) {
        Dialog(
            onDismissRequest = {
                createReviewDialogStatus = false
            }
        ) {
            Card {
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(16.dp)
                ) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        IconButton(
                            onClick = {
                                createReviewDialogStatus = false
                            }
                        ) {
                            Icon(
                                imageVector = Icons.Outlined.ArrowBack,
                                contentDescription = "Back"
                            )
                        }
                        Text(
                            text = stringResource(R.string.add_a_review_label),
                            style = TextStyle(
                                fontWeight = FontWeight.Bold,
                                fontSize = 25.sp
                            )
                        )
                    }
                    Divider(
                        thickness = 2.dp,
                        color = colorResource(R.color.primaryColor)
                    )

                    Spacer(modifier = Modifier.height(16.dp))

                    Column(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(16.dp),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        RatingBar(
                            onRatingChanged = { newRating ->
                                createReviewViewModel.onUpdateStars(newRating)
                            },
                            maxStars = 5,
                            isIndicator = false,
                            ratingIcon = { index ->
                                if (index < review.stars) {
                                    Icon(
                                        imageVector = Icons.Default.Star,
                                        contentDescription = null,
                                        tint = colorResource(R.color.primaryColor),
                                        modifier = Modifier.size(40.dp)
                                    )
                                } else {
                                    Icon(
                                        painter = painterResource(R.drawable.star_outline),
                                        contentDescription = null,
                                        tint = colorResource(R.color.secondaryColor),
                                        modifier = Modifier.size(40.dp)
                                    )
                                }
                            },
                            modifier = Modifier.padding(8.dp)
                        )

                        Spacer(modifier = Modifier.height(16.dp))

                        TextFieldComp(
                            title = stringResource(R.string.message_text_field_label),
                            value = review.message,
                            onValueChange = { createReviewViewModel.updateReviewMessage(it) },
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(100.dp),
                        )

                        Spacer(modifier = Modifier.height(16.dp))

                        Button(
                            onClick = {
                                createReviewViewModel.createReview(offerUid)
                                createReviewDialogStatus = false
                            },
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(8.dp)
                        ) {
                            Text(text = stringResource(R.string.add_button_label))
                        }
                    }
                }
            }
        }
    }
}