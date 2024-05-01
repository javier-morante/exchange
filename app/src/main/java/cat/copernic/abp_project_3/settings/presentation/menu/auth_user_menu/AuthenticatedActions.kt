package cat.copernic.abp_project_3.settings.presentation.menu.auth_user_menu

import androidx.compose.foundation.layout.Column
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import cat.copernic.abp_project_3.R
import cat.copernic.abp_project_3.application.presentation.components.button.StylizedButtonComponent
import cat.copernic.abp_project_3.application.presentation.components.button.StylizedNavigationButtonComponent
import cat.copernic.abp_project_3.application.presentation.components.profile_info.UserProfileComponent
import cat.copernic.abp_project_3.application.data.model.Profile
import cat.copernic.abp_project_3.settings.presentation.SettingsBaseNav

/**
 * Component that contains all the option buttons that an authenticated user can perform inside of the
 * settings screen menu
 *
 * @param userProfile
 * @param userEmail
 * @param handleNavigation
 * @param handleLogOut
 */
@Composable
fun AuthenticatedActions(
    userProfile: Profile,
    userEmail: String,

    handleNavigation: (String) -> Unit,
    handleLogOut: () -> Unit
) {
    Column {

        UserProfileComponent(
            userProfile = userProfile,
            userEmail = userEmail,
            handleNavigation = {
                handleNavigation(SettingsBaseNav.PROFILE.name)
            }
        )

        StylizedNavigationButtonComponent(
            title = stringResource(R.string.offers_management_menu_auth_user_label),
            leadingIcon = painterResource(R.drawable.baseline_book),
            onClick = {
                handleNavigation(SettingsBaseNav.MY_OFFERS.name)
            }
        )

        StylizedButtonComponent(
            title = stringResource(R.string.log_out_label),
            leadingIcon = painterResource(R.drawable.logout),
            onClick = handleLogOut
        )

    }
}