package cat.copernic.abp_project_3.settings.presentation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import cat.copernic.abp_project_3.settings.presentation.authentication.password_recovery.PasswordRecoveryScreen
import cat.copernic.abp_project_3.settings.presentation.authentication.sign_in.SignInScreen
import cat.copernic.abp_project_3.settings.presentation.authentication.sign_up.SignUpScreen
import cat.copernic.abp_project_3.settings.presentation.categories.CategoryManagerScreen
import cat.copernic.abp_project_3.settings.presentation.menu.SettingsMenuScreen
import cat.copernic.abp_project_3.settings.presentation.offers.OffersManagementScreen
import cat.copernic.abp_project_3.settings.presentation.profile.ProfileScreen
import cat.copernic.abp_project_3.settings.presentation.users.UserManagerScreen


/**
 * Enum that contains all the navigation paths for the navController
 */
enum class SettingsBaseNav {
    MENU,
    PROFILE,
    MY_OFFERS,
    USER_MANAGEMENT,
    CATEGORIES_MANAGEMENT,
    SIGN_IN,
    SIGN_UP,
    PASSWORD_RECOVERY
}


/**
 * Component that contains the navigation referring to the components inside of the
 * settings screen
 *
 * @param navController The nav host controller utilized by the NavHost.
 * @Param displayNotification The method for displaying notifications
 */
@Composable
fun SettingsScreen(
    navController: NavHostController = rememberNavController(),
    displayNotification: (String) -> Unit
) {
    NavHost(
        navController = navController,
        startDestination = SettingsBaseNav.MENU.name
    ) {
        composable(SettingsBaseNav.MENU.name){
            SettingsMenuScreen(
                displayNotification = displayNotification,
                handleNavigation = {
                    navController.navigate(it)
                }
            )
        }
        composable(SettingsBaseNav.PROFILE.name){
            ProfileScreen(
                displayNotification = displayNotification,
                handleNavigation = {
                    navController.navigate(it)
                },
                backNavigation = {
                    navController.popBackStack()
                }
            )
        }
        composable(SettingsBaseNav.MY_OFFERS.name) {
            OffersManagementScreen(
                displayNotification = displayNotification,
                backNavigation = {
                    navController.popBackStack()
                }
            )
        }
        composable(SettingsBaseNav.USER_MANAGEMENT.name){
            UserManagerScreen(
                displayNotification = displayNotification,
                backNavigation = {
                    navController.popBackStack()
                }
            )
        }
        composable(SettingsBaseNav.CATEGORIES_MANAGEMENT.name){
            CategoryManagerScreen(
                displayNotification = displayNotification,
                backNavigation = {
                    navController.popBackStack()
                }
            )
        }
        composable(SettingsBaseNav.SIGN_IN.name){
            SignInScreen(
                displayNotification = displayNotification,
                handleNavigation =  {
                    navController.navigate(it)
                },
                backNavigation = {
                    navController.popBackStack()
                }
            )
        }
        composable(SettingsBaseNav.SIGN_UP.name){
            SignUpScreen(
                displayNotification = displayNotification,
                handleNavigation = {
                    navController.navigate(it)
                },
                backNavigation = {
                    navController.popBackStack()
                }
            )
        }
        composable(SettingsBaseNav.PASSWORD_RECOVERY.name){
            PasswordRecoveryScreen(
                displayNotification = displayNotification,
                backNavigation = {
                    navController.popBackStack()
                }
            )
        }
    }
}