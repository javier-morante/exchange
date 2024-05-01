package cat.copernic.abp_project_3.settings.presentation.menu.admin_menu

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
 * Component that contains all the option buttons that an administrator user can perform inside of the
 * settings screen menu
 *
 * @param userProfile
 * @param userEmail
 * @param handleNavigation
 * @param handleLogOut
 */
@Composable
fun AdministratorActions(
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
            title = stringResource(R.string.offers_management_menu_administrator_user_label),
            leadingIcon = painterResource(R.drawable.baseline_book),
            onClick = {
                handleNavigation(SettingsBaseNav.MY_OFFERS.name)
            }
        )

        StylizedNavigationButtonComponent(
            title = stringResource(R.string.category_management_label),
            leadingIcon = painterResource(R.drawable.grid_view),
            onClick = {
                handleNavigation(SettingsBaseNav.CATEGORIES_MANAGEMENT.name)
            }
        )

        StylizedNavigationButtonComponent(
            title = stringResource(R.string.users_management_label),
            leadingIcon = painterResource(R.drawable.group),
            onClick = {
                handleNavigation(SettingsBaseNav.USER_MANAGEMENT.name)
            }
        )

        StylizedButtonComponent(
            title = stringResource(R.string.log_out_label),
            leadingIcon = painterResource(R.drawable.logout),
            onClick = handleLogOut
        )

    }
}