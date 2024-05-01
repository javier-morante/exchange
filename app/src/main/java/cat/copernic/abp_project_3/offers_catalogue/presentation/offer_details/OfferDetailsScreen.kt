package cat.copernic.abp_project_3.offers_catalogue.presentation.offer_details

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationRail
import androidx.compose.material3.NavigationRailItem
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import cat.copernic.abp_project_3.R
import cat.copernic.abp_project_3.application.data.model.Category
import cat.copernic.abp_project_3.application.data.model.Offer
import cat.copernic.abp_project_3.application.data.model.Profile
import cat.copernic.abp_project_3.offers_catalogue.domain.offer_details.OfferOverviewViewModel
import cat.copernic.abp_project_3.offers_catalogue.presentation.offer_details.offer_details.OfferDetailsComponent
import cat.copernic.abp_project_3.offers_catalogue.presentation.offer_details.offer_location.OfferLocationComponent
import cat.copernic.abp_project_3.offers_catalogue.presentation.offer_details.offer_reviews.OfferReviewsComponent

/**
 * Enum class representing navigation options for the base details screen of an offer.
 *
 * This enum class defines navigation options used in the base details screen of an offer,
 * typically displayed as tabs or buttons for navigating to different sections of offer details.
 *
 * Each enum member corresponds to a specific navigation option and includes an associated icon.
 *
 * @property icon The resource ID of the icon representing this navigation option.
 */
enum class OfferDetailsBaseNav(
    val icon: Int,
    val description: Int
) {
    /**
     * Represents the "Details" navigation option, typically displaying general details about the offer.
     */
    DETAILS(
        R.drawable.menu_book,
        R.string.offer_details_side_bar_helper
    ),

    /**
     * Represents the "Reviews" navigation option, typically displaying reviews and ratings for the offer.
     */
    REVIEWS(
        R.drawable.star_outline,
        R.string.reviews_side_bar_helper
    ),

    /**
     * Represents the "Location" navigation option, typically displaying the location or map of the offer.
     */
    LOCATION(
        R.drawable.location_on,
        R.string.location_side_bar_helper
    ),
}

/**
 * Composable function representing the screen for displaying detailed information about an offer.
 *
 * This function displays various details of a specific offer, including its general details,
 * reviews, and location, navigated through a bottom navigation rail.
 *
 * @param navController The NavHostController used for navigation within the screen.
 * @param offerOverviewViewModel The view model providing data and logic for offer overview.
 * @param offerUid The unique identifier of the offer to display details for.
 * @param handleBackNavigation Callback function invoked when navigating back from the offer details screen.
 * @param handleOffersNavigation Callback function invoked to navigate to the main offers screen.
 */
@Composable
fun OfferDetailsScreen(
    navController: NavHostController = rememberNavController(),
    offerOverviewViewModel: OfferOverviewViewModel = hiltViewModel(),
    offerUid: String,
    handleBackNavigation: () -> Unit,
    handleOffersNavigation: () -> Unit
) {
    var selectedItem by rememberSaveable {
        mutableStateOf(OfferDetailsBaseNav.DETAILS)
    }

    LaunchedEffect(Unit) {
        offerOverviewViewModel.fetchActualReviews(offerUid)
        offerOverviewViewModel.setSelectedOffer(offerUid)
        offerOverviewViewModel.fetchCurrentUserProfile()
    }

    // Check Everything

    val authUserStatus by offerOverviewViewModel.authUserStatus.collectAsState()
    val authUserUid by offerOverviewViewModel.authUserUid.collectAsState()

    val offerState by offerOverviewViewModel.offer.collectAsState()

    val userState by offerOverviewViewModel.user.collectAsState()

    val categoryState by offerOverviewViewModel.currentOfferCategory.collectAsState()


    Row {
        NavigationRail(
            modifier = Modifier
                .width(65.dp)
                .fillMaxHeight()
                .shadow(
                    elevation = 2.dp,
                    ambientColor = Color.Black
                ),
            header = {
                Icon(
                    modifier = Modifier
                        .padding(vertical = 16.dp)
                        .clickable {
                            handleBackNavigation()
                        },
                    painter = painterResource(R.drawable.arrow_back),
                    contentDescription = stringResource(R.string.back_button_helper)
                )
            }
        ) {
            OfferDetailsBaseNav.entries.forEach { item ->
                if(item == OfferDetailsBaseNav.REVIEWS && !authUserStatus) {

                } else {
                    NavigationRailItem(
                        selected = selectedItem == item,
                        onClick = {
                            selectedItem = item
                            navController.navigate(item.name)
                        },
                        icon = {
                            Icon(
                                painter = painterResource(item.icon),
                                contentDescription = stringResource(item.description)
                            )
                        }
                    )
                }
            }
        }

        NavHost(
            navController = navController,
            startDestination = OfferDetailsBaseNav.DETAILS.name
        ) {
            composable(OfferDetailsBaseNav.DETAILS.name) {
                OfferDetailsComponent(
                    offer = offerState ?: Offer(),
                    profile = userState ?: Profile(),
                    category = categoryState ?: Category(),
                    authUserProfileStatus = authUserStatus,
                    authUserUid = authUserUid,
                    handleCreateConversation = {
                        offerOverviewViewModel.createNewConversation()
                        handleOffersNavigation()
                    }
                )
            }
            composable(OfferDetailsBaseNav.REVIEWS.name) {
                OfferReviewsComponent(
                    offerUid = offerUid,
                )
            }
            composable(OfferDetailsBaseNav.LOCATION.name) {
                OfferLocationComponent(
                    offer = offerState ?: Offer()
                )
            }
        }

    }
}