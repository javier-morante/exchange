package cat.copernic.abp_project_3.application.presentation

import android.content.ContentValues.TAG
import android.content.pm.ActivityInfo
import android.util.Log
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Snackbar
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import cat.copernic.abp_project_3.R
import cat.copernic.abp_project_3.application.domain.MainViewModel
import cat.copernic.abp_project_3.chat.presentation.ConversationsScreen
import cat.copernic.abp_project_3.application.presentation.components.screen_lock.LockScreenOrientation
import cat.copernic.abp_project_3.create_offer.presentation.OfferCreationScreen
import cat.copernic.abp_project_3.offers_catalogue.presentation.OffersCatalogueScreen
import cat.copernic.abp_project_3.settings.presentation.SettingsScreen
import kotlinx.coroutines.launch

/**
 * Enum representing the base navigation destinations of the application.
 * Each enum value corresponds to a specific screen title and icon resource.
 *
 * This enum is used to define the primary navigation destinations within the application.
 */
enum class AppBaseNav(
    val title: String,
    val icon: Int,
    val description: Int,
    val authRequired: Boolean
) {
    /**
     * Catalogue navigation destination.
     */
    CATALOGUE(
        "Catalogue",
        R.drawable.baseline_book,
        R.string.catalogue_bottom_bar_helper,
        false
    ),

    /**
     * Conversations navigation destination.
     */
    CONVERSATIONS(
        "Conversations",
        R.drawable.chat_bubble,
        R.string.conversations_bottom_bar_helper,
        true
    ),

    /**
     * Create navigation destination.
     */
    CREATE(
        "Create",
        R.drawable.baseline_add_circle_outline,
        R.string.create_offer_bottom_bar_helper,
        true
    ),

    /**
     * Settings navigation destination.
     */
    SETTINGS(
        "Settings",
        R.drawable.baseline_settings,
        R.string.settings_bottom_bar_helper,
        false
    )
}

/**
 * Composable function representing the main screen of the application.
 * This screen handles navigation and displays the appropriate UI based on the current screen state.
 *
 * @param navController The navigation controller used for navigating between composables.
 * @param mainViewModel The view model responsible for managing the main screen state and navigation.
 */
@Composable
fun MainScreen(
    navController: NavHostController = rememberNavController(),
    mainViewModel: MainViewModel = hiltViewModel()
) {
    // Automate Navigation
    val actualScreen by mainViewModel.actualScreen.collectAsState()
    val authUserStatus by mainViewModel.authUserStatus.collectAsState()

    LaunchedEffect(key1 = actualScreen) {
        navController.navigate(actualScreen)
    }

    MainScreenUI(
        navController = navController,
        actualScreen = actualScreen,
        authUserStatus = authUserStatus,
        handleSetNewScreen = { mainViewModel.setActualScreen(it) }
    )

}

/**
 * Composable function representing the main UI of the application.
 * This function defines the layout and navigation for the main screen, including a bottom navigation bar.
 *
 * @param navController The navigation controller used for navigating between composables.
 * @param actualScreen The current screen being displayed based on navigation state.
 * @param handleSetNewScreen Callback function to handle setting a new screen based on user interaction.
 */
@Composable
fun MainScreenUI(
    navController: NavHostController = rememberNavController(),
    actualScreen: String = "",
    authUserStatus: Boolean = false,
    handleSetNewScreen: (String) -> Unit = {}
) {
    val scope = rememberCoroutineScope()
    val snackbarHostState = remember { SnackbarHostState() }

    val displayNotification = { message: String ->
        scope.launch {
            snackbarHostState.showSnackbar(
                message = message,
                withDismissAction = true
            )
        }
    }

    LockScreenOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT)

    Scaffold(
        snackbarHost = {
            SnackbarHost(
                hostState = snackbarHostState,
                snackbar = { data ->
                    Snackbar(
                        modifier = Modifier.padding(16.dp),
                        snackbarData = data
                    )
                }
            )
        },
        bottomBar = {
            NavigationBar {
                AppBaseNav.entries.forEach { item ->
                    if(item.authRequired && !authUserStatus) {
                        Log.d(TAG, "User is not authenticated")
                    } else {
                        NavigationBarItem(
                            selected = actualScreen == item.name,
                            onClick = { handleSetNewScreen(item.name) },
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
        }
    ) { innerPadding ->

        NavHost(
            navController = navController,
            startDestination = actualScreen,
            modifier = Modifier.padding(innerPadding)
        ) {
            composable(AppBaseNav.CATALOGUE.name) {
                OffersCatalogueScreen(
                    handleNavigation = {
                        navController.navigate(it)
                    }
                )
            }
            composable(AppBaseNav.CONVERSATIONS.name) {
                ConversationsScreen(
                    displayNotification = { displayNotification(it) }
                )
            }
            composable(AppBaseNav.CREATE.name) {
                OfferCreationScreen(
                    displayNotification = { displayNotification(it) },
                    handleNavigation = { handleSetNewScreen(it) },
                    handleBackNavigation = {
                        navController.popBackStack()
                    }
                )
            }
            composable(AppBaseNav.SETTINGS.name) {
                SettingsScreen(
                    displayNotification = { displayNotification(it) }
                )
            }
        }

    }
}