package cat.copernic.abp_project_3.offers_catalogue.presentation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import cat.copernic.abp_project_3.application.presentation.AppBaseNav
import cat.copernic.abp_project_3.offers_catalogue.presentation.catalogue.CatalogueScreen
import cat.copernic.abp_project_3.offers_catalogue.presentation.maps.CatalogueMapScreen
import cat.copernic.abp_project_3.offers_catalogue.presentation.offer_details.OfferDetailsScreen

/**
 * Enum class representing navigation destinations within the offers catalogue feature.
 *
 * This enum defines the possible destinations for navigating within the offers catalogue,
 * including catalogue view, detailed offer view, and map view details.
 */
enum class OffersCatalogueBaseNav {
    /**
     * Represents the catalogue view destination.
     * Navigating to this destination displays the main catalogue of offers.
     */
    CATALOGUE,

    /**
     * Represents the details view destination.
     * Navigating to this destination displays detailed information about a specific offer.
     */
    DETAILS,

    /**
     * Represents the map details view destination.
     * Navigating to this destination displays offer details on a map view.
     */
    MAP_DETAILS
}

/**
 * Composable function representing the main entry point for the offers catalogue feature.
 *
 * This function initializes the navigation host and sets up navigation routes for the offers catalogue.
 *
 * @param navController The NavHostController used for navigation within the offers catalogue.
 * @param handleNavigation A callback function for handling navigation actions within the application.
 */
@Composable
fun OffersCatalogueScreen(
    navController: NavHostController = rememberNavController(),
    handleNavigation: (String) -> Unit
) {
    OffersCatalogueScreenUI(
        navController = navController,
        handleNavigation = handleNavigation
    )
}

/**
 * Composable function representing the UI logic for the offers catalogue feature.
 *
 * This function configures the navigation routes and associated composables for the offers catalogue.
 *
 * @param navController The NavHostController used for navigation within the offers catalogue.
 * @param handleNavigation A callback function for handling navigation actions within the application.
 */
@Composable
fun OffersCatalogueScreenUI(
    navController: NavHostController = rememberNavController(),
    handleNavigation: (String) -> Unit
) {
    NavHost(
        navController = navController,
        startDestination = OffersCatalogueBaseNav.CATALOGUE.name
    ) {
        composable(OffersCatalogueBaseNav.CATALOGUE.name) {
            CatalogueScreen(
                navController = navController,
                handleMapNavigation = {
                    navController.navigate(
                        OffersCatalogueBaseNav.MAP_DETAILS.name
                    )
                }
            )
        }
        composable(
            route = "${OffersCatalogueBaseNav.DETAILS.name}/{offerId}",
            arguments = listOf(
                navArgument("offerId") { type = NavType.StringType }
            )
        ) { backStackEntry ->
            OfferDetailsScreen(
                offerUid = backStackEntry.arguments?.getString("offerId") ?: "",
                handleBackNavigation = {
                    navController.popBackStack()
                },
                handleOffersNavigation = {
                    handleNavigation(AppBaseNav.CONVERSATIONS.name)
                }
            )
        }
        composable(
            route = OffersCatalogueBaseNav.MAP_DETAILS.name
        ) {
            CatalogueMapScreen(
                handleOfferNavigation = {
                    navController.navigate("${OffersCatalogueBaseNav.DETAILS.name}/${it}")
                },
                handleBackNavigation = {
                    navController.popBackStack()
                }
            )
        }
    }
}