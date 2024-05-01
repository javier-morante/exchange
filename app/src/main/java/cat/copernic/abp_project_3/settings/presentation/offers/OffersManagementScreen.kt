package cat.copernic.abp_project_3.settings.presentation.offers

import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import cat.copernic.abp_project_3.application.data.model.Offer
import cat.copernic.abp_project_3.settings.domain.offers.OffersManagementViewModel
import cat.copernic.abp_project_3.settings.presentation.offers.offer_details.OfferDetailsComponent
import cat.copernic.abp_project_3.settings.presentation.offers.offers_list.OffersListComponent

/**
 * Enum class that contains all the navigation references for the navController of the
 * Offers Manager Screen
 *
 */
enum class OffersManagerBaseNav {
    LIST,
    DETAILS
}

/**
 * Hoisted component that contains the offerManagementViewModel injection and the reference to
 * the offerManagementScreenUI component
 *
 * @param offersManagementViewModel
 * @param displayNotification
 * @param navController
 * @param backNavigation
 */
@Composable
fun OffersManagementScreen(
    offersManagementViewModel: OffersManagementViewModel = hiltViewModel(),
    displayNotification: (String) -> Unit,
    navController: NavHostController = rememberNavController(),
    backNavigation: () -> Unit
) {
    OfferManagementScreenUI(
        navController = navController,
        displayNotification = displayNotification,
        handleMenuBackNavigation = backNavigation,
        offersList = offersManagementViewModel.offersList
    )
}

/**
 * Component that contains all the ui elements referent to the Offer Management Screen
 *
 * @param navController
 * @param displayNotification
 * @param handleMenuBacKNavigation
 * @param offersList
 */
@Composable
fun OfferManagementScreenUI(
    navController: NavHostController = rememberNavController(),
    displayNotification: (String) -> Unit,
    handleMenuBackNavigation: () -> Unit,
    offersList: List<Offer>,
) {
    NavHost(
        navController = navController,
        startDestination = OffersManagerBaseNav.LIST.name
    ) {
        composable(OffersManagerBaseNav.LIST.name) {
            OffersListComponent(
                handleMenuBackNavigation = handleMenuBackNavigation,
                offersListComponent = offersList,
                handleDetails = { offerId ->
                    navController.navigate(
                        "${OffersManagerBaseNav.DETAILS.name}/${offerId}"
                    )
                }
            )
        }
        composable(
            route = "${OffersManagerBaseNav.DETAILS.name}/{offerId}",
            arguments = listOf(navArgument("offerId") { defaultValue = "" })
        ) {backStackEntry ->
            OfferDetailsComponent(
                displayNotification = displayNotification,
                handleBackNavigation = { navController.popBackStack() },
                offerProfileId = backStackEntry.arguments?.getString("offerId")
            )
        }
    }
}
