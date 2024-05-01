package cat.copernic.abp_project_3.settings.presentation.authentication.password_recovery

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import cat.copernic.abp_project_3.R
import cat.copernic.abp_project_3.application.presentation.components.button.ConfirmButtonComp
import cat.copernic.abp_project_3.application.presentation.components.label.AuthTitleComponent
import cat.copernic.abp_project_3.application.presentation.components.text_field.IconTextFieldComp
import cat.copernic.abp_project_3.settings.domain.authentication.password_recovery.PasswordRecoveryViewModel

/**
 * Hoisted component that contains a viewModel injection and a reference to the
 * PasswordRecoveryScreenUI composable component
 *
 * @param passwordRecoveryViewModel
 * @param displayNotification
 * @param backNavigation
 */
@Composable
fun PasswordRecoveryScreen(
    passwordRecoveryViewModel: PasswordRecoveryViewModel = hiltViewModel(),
    displayNotification: (String) -> Unit,
    backNavigation: () -> Unit = {}
) {
    val email by passwordRecoveryViewModel.email.collectAsState()

    PasswordRecoveryScreenUI(
        emailValue = email,
        onEmailChange = { passwordRecoveryViewModel.setEmail(it) },
        handleSubmit = {
            if(passwordRecoveryViewModel.validateData(displayNotification)) {
                passwordRecoveryViewModel.handlePasswordRecovery(displayNotification)
            }
        },
        handleBackNavigation = backNavigation
    )
}

/**
 * Composable component that contains all the ui elements referent to the Password Recovery Screen
 *
 * @param emailValue
 * @param onEmailChange
 * @param handleSubmit
 * @param handleBackNavigation
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PasswordRecoveryScreenUI(
    emailValue: String = "",
    onEmailChange: (String) -> Unit = {},
    handleSubmit: () -> Unit = {},
    handleBackNavigation: () -> Unit = {}
) {

    Scaffold(
        topBar = {
            TopAppBar(
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = Color.Transparent,
                ),
                navigationIcon = {
                    IconButton(
                        onClick = handleBackNavigation
                    ) {
                        Icon(
                            imageVector = Icons.Outlined.ArrowBack,
                            contentDescription = stringResource(R.string.back_button_helper),
                            tint = Color.Black
                        )
                    }
                },
                title = {}
            )
        }
    ) { innerPadding ->
        Box(
            contentAlignment = Alignment.Center,
            modifier = Modifier.fillMaxSize()
        ) {
            // Background Image
            Image(
                painter = painterResource(R.drawable.auth_background),
                contentDescription = stringResource(R.string.authentication_background_image_helper),
                modifier = Modifier.matchParentSize(),
                contentScale = ContentScale.Crop
            )

            Column(
                verticalArrangement = Arrangement.spacedBy(16.dp),
                modifier = Modifier
                    .padding(innerPadding)
                    .padding(48.dp)
            ) {
                // Title
                AuthTitleComponent(
                    modifier = Modifier,
                    title = stringResource(R.string.password_recovery_screen_title),
                    subtitle = stringResource(R.string.password_recovery_screen_subtitle)
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

                Row {
                    Spacer(modifier = Modifier.weight(2f))

                    // Submit Button
                    ConfirmButtonComp(
                        modifier = Modifier.weight(1f),
                        icon = painterResource(R.drawable.baseline_arrow_forward),
                        iconDescription = stringResource(R.string.submit_password_recovery_button_helper),
                        onClick = handleSubmit
                    )
                }

            }
        }
    }
}

