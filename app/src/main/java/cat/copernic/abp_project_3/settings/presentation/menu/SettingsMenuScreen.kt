package cat.copernic.abp_project_3.settings.presentation.menu

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import cat.copernic.abp_project_3.application.data.enums.UserRole
import cat.copernic.abp_project_3.application.data.model.Profile
import cat.copernic.abp_project_3.settings.domain.menu.SettingsMenuViewModel
import cat.copernic.abp_project_3.settings.presentation.menu.admin_menu.AdministratorActions
import cat.copernic.abp_project_3.settings.presentation.menu.auth_user_menu.AuthenticatedActions
import cat.copernic.abp_project_3.settings.presentation.menu.visitor_menu.UnauthenticatedActions

/**
 * Hoisted component that contains the viewModel injection and references the SettingsMenuScreenUi
 * component
 *
 * @param settingsMenuViewModel
 * @param displayNotification
 * @param handleNavigation
 */
@Composable
fun SettingsMenuScreen(
    settingsMenuViewModel: SettingsMenuViewModel = hiltViewModel(),
    displayNotification: (String) -> Unit,
    handleNavigation: (String) -> Unit
) {
    val userEmail by settingsMenuViewModel.email.collectAsState()
    val userProfile by settingsMenuViewModel.userProfile.collectAsState()
    val authStatus by settingsMenuViewModel.authStatus.collectAsState()

    SettingsMenuScreenUI(
        userEmail = userEmail ?: "",
        userProfile = userProfile ?: Profile(),
        handleNavigation = handleNavigation,
        authenticationStatus = authStatus,
        handleLogOut = { settingsMenuViewModel.handleSignOut(displayNotification) }
    )
}

/**
 * Component that contains all the ui elements referent to the Settings Menu Screen
 *
 * @param userEmail
 * @param userProfile
 * @param authenticationStatus
 * @param handleNavigation
 * @param handleLogOut
 */
@Composable
fun SettingsMenuScreenUI(
    userEmail: String,
    userProfile: Profile,
    authenticationStatus: Boolean,

    handleNavigation: (String) -> Unit,
    handleLogOut: () -> Unit
) {

    val isAdministrator = userProfile.role == UserRole.ADMINISTRATOR

    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {

        if(authenticationStatus && isAdministrator) {
            AdministratorActions(
                userProfile = userProfile,
                userEmail = userEmail,
                handleNavigation = handleNavigation,
                handleLogOut = handleLogOut
            )
        } else if(authenticationStatus) {
            AuthenticatedActions(
                userProfile = userProfile,
                userEmail = userEmail,
                handleNavigation = handleNavigation,
                handleLogOut = handleLogOut
            )
        } else {
            UnauthenticatedActions(
                handleNavigation = handleNavigation
            )
        }

    }
}