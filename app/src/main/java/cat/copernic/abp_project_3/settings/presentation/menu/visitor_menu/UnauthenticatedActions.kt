package cat.copernic.abp_project_3.settings.presentation.menu.visitor_menu

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import cat.copernic.abp_project_3.R
import cat.copernic.abp_project_3.application.presentation.components.button.StylizedNavigationButtonComponent
import cat.copernic.abp_project_3.settings.presentation.SettingsBaseNav

/**
 * Component that contains all the option buttons that an unauthenticated user can perform inside of the
 * settings screen menu
 *
 * @param handleNavigation
 */
@Composable
fun UnauthenticatedActions(
    handleNavigation: (String) -> Unit
) {
    Column(
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        StylizedNavigationButtonComponent(
            title = stringResource(R.string.sign_in_label),
            leadingIcon = painterResource(R.drawable.login),
            onClick = {
                handleNavigation(SettingsBaseNav.SIGN_IN.name)
            }
        )

        StylizedNavigationButtonComponent(
            title = stringResource(R.string.sign_up_label),
            leadingIcon = painterResource(R.drawable.edit_square),
            onClick = {
                handleNavigation(SettingsBaseNav.SIGN_UP.name)
            }
        )
    }
}