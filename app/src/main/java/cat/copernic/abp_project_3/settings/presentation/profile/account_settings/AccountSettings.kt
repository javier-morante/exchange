package cat.copernic.abp_project_3.settings.presentation.profile.account_settings

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import cat.copernic.abp_project_3.R
import cat.copernic.abp_project_3.application.presentation.components.button.StylizedButtonComponent
import cat.copernic.abp_project_3.application.presentation.components.button.DangerButtonComp
import cat.copernic.abp_project_3.application.presentation.components.button.StylizedNavigationButtonComponent
import cat.copernic.abp_project_3.application.presentation.components.divider.CustomDivider
import cat.copernic.abp_project_3.application.presentation.components.label.CustomTitle
import cat.copernic.abp_project_3.application.presentation.components.text_field.CustomTextField

/**
 * Component that contains all the ui elements referent to the Account settings screen
 *
 * @param modifier
 * @param emailValue
 * @param onEmailValueChange
 * @param handleUpdateEmail
 * @param handlePasswordRecovery
 * @param handleDeleteAccount
 */
@Composable
fun AccountSettings(
    modifier: Modifier = Modifier,

    emailValue: String,
    onEmailValueChange: (String) -> Unit,

    handleUpdateEmail: () -> Unit,
    handlePasswordRecovery: () -> Unit,
    handleDeleteAccount: () -> Unit
) {

    Column(
        modifier = modifier
            .fillMaxWidth()
    ) {

        CustomTitle(
            title = stringResource(R.string.account_account_settings_title)
        )

        Spacer(modifier = Modifier.height(28.dp))

        // Restore password navigation Button
        StylizedNavigationButtonComponent(
            title = stringResource(R.string.account_account_settings_password_restore_label),
            leadingIcon = painterResource(R.drawable.key),
            onClick = handlePasswordRecovery
        )

        CustomDivider()

        // Email Text Field
        CustomTextField(
            title = stringResource(R.string.account_account_settings_email_label),
            value = emailValue,
            onValueChange = onEmailValueChange
        )

        // Send Restore Email address email Button
        StylizedButtonComponent(
            title = stringResource(R.string.account_account_settings_restore_email_label),
            leadingIcon = painterResource(R.drawable.mail),
            onClick = handleUpdateEmail
        )

        Spacer(modifier = Modifier.height(48.dp))

        CustomTitle(
            title = stringResource(R.string.account_danger_zone_settings_title),
            color = colorResource(R.color.danger)
        )

        Spacer(modifier = Modifier.height(28.dp))

        // Delete account button
        DangerButtonComp(
            modifier = Modifier.padding(horizontal = 12.dp).fillMaxWidth(),
            title = stringResource(R.string.account_danger_zone_settings_delete_account_label),
            onClick = handleDeleteAccount
        )
    }
}