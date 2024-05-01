package cat.copernic.abp_project_3.settings.presentation.authentication.sign_up.credentials_phase

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import cat.copernic.abp_project_3.R

import cat.copernic.abp_project_3.application.presentation.components.button.ConfirmButtonComp
import cat.copernic.abp_project_3.application.presentation.components.text_field.IconTextFieldComp
import cat.copernic.abp_project_3.application.presentation.components.text_field.PasswordTextFieldComp
import cat.copernic.abp_project_3.application.presentation.components.label.AuthTitleComponent

/**
 * Component that contains all the ui elements referent to the Sign Up Credentials phase Screen
 *
 * @param emailValue
 * @param onEmailChange
 * @param passwordValue
 * @param onPasswordChange
 * @param repeatPasswordValue
 * @param onRepeatPasswordChange
 * @param handleSubmitCredentials
 */
@Composable
fun SignUpCredentialsComponent(
    emailValue: String,
    onEmailChange: (String) -> Unit,
    passwordValue: String,
    onPasswordChange: (String) -> Unit,
    repeatPasswordValue: String,
    onRepeatPasswordChange: (String) -> Unit,

    handleSubmitCredentials: () -> Unit
) {
    Column(
        verticalArrangement = Arrangement.spacedBy(16.dp),
        modifier = Modifier.padding(48.dp)
    ) {
        // Title
        AuthTitleComponent(
            modifier = Modifier,
            title = stringResource(R.string.sign_up_screen_title),
            subtitle = stringResource(R.string.sign_up_screen_subtitle)
        )

        // Email Text Field
        IconTextFieldComp(
            modifier = Modifier.fillMaxWidth(),
            title = stringResource(R.string.email_form_label),
            icon = painterResource(R.drawable.baseline_email),
            iconDescription = stringResource(R.string.email_form_icon_helper),
            value = emailValue,
            onValueChange = onEmailChange
        )
        // Password Text Field
        PasswordTextFieldComp(
            modifier = Modifier.fillMaxWidth(),
            title = stringResource(R.string.password_form_label),
            value = passwordValue,
            onValueChange = onPasswordChange
        )
        // Password Text Field
        PasswordTextFieldComp(
            modifier = Modifier.fillMaxWidth(),
            title = stringResource(R.string.password_repeat_label),
            value = repeatPasswordValue,
            onValueChange = onRepeatPasswordChange
        )

        Row {
            Spacer(modifier = Modifier.weight(2f))

            // Send Data Button
            ConfirmButtonComp(
                modifier = Modifier.weight(1f),
                icon = painterResource(R.drawable.baseline_arrow_forward),
                iconDescription = stringResource(R.string.submit_sign_up_credentials_button_helper),
                onClick = handleSubmitCredentials
            )
        }
    }
}