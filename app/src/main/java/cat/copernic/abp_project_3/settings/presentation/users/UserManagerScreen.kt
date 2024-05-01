package cat.copernic.abp_project_3.settings.presentation.users

import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import cat.copernic.abp_project_3.application.data.model.Profile
import cat.copernic.abp_project_3.settings.domain.users.UsersManagerViewModel
import cat.copernic.abp_project_3.settings.presentation.users.user_details.UserDetailsComponent
import cat.copernic.abp_project_3.settings.presentation.users.user_list.UserListComponent

/**
 * Enum class that contains all the available navigation references for the navController
 *
 */
enum class UserManagerBaseNav {
    LIST,
    DETAILS
}

/**
 * Hoisted component that contains the injected view model and attributes that have to be
 * provided to the UserManagerScreenUI
 *
 * @param navController The nav host controller utilized by the NavHost.
 * @Param displayNotification The method for displaying notifications
 */
@Composable
fun UserManagerScreen(
    usersManagerViewModel: UsersManagerViewModel = hiltViewModel(),
    displayNotification: (String) -> Unit,
    navController: NavHostController = rememberNavController(),
    backNavigation: () -> Unit
) {
    UserManagerScreenUI(
        navController = navController,
        displayNotification = displayNotification,
        handleMenuBackNavigation = backNavigation,
        usersList = usersManagerViewModel.usersList
    )
}

/**
 * Component that contains the nav controller for the user manager screen
 *
 * @param navController
 * @param displayNotification
 * @param handleMenuBackNavigation
 * @param usersList
 */
@Composable
fun UserManagerScreenUI(
    navController: NavHostController = rememberNavController(),
    displayNotification: (String) -> Unit,
    handleMenuBackNavigation: () -> Unit,
    usersList: List<Profile>
) {
    NavHost(
        navController = navController,
        startDestination = UserManagerBaseNav.LIST.name
    ) {
        // User Manager List Screen
        composable(UserManagerBaseNav.LIST.name) {
            UserListComponent(
                handleMenuBackNavigation = handleMenuBackNavigation,
                usersListComponent = usersList,
                handleDetails = { userId ->
                    navController.navigate(
                        "${UserManagerBaseNav.DETAILS.name}/${userId}"
                    )
                }
            )
        }
        // User Manager Details Screen
        composable(
            route = "${UserManagerBaseNav.DETAILS.name}/{profileId}",
            arguments = listOf(navArgument("profileId") { defaultValue = "" })
        ){ backStackEntry ->
            UserDetailsComponent(
                displayNotification = displayNotification,
                handleBackNavigation = { navController.popBackStack() },
                userProfileId = backStackEntry.arguments?.getString("profileId"),
            )
        }
    }
}