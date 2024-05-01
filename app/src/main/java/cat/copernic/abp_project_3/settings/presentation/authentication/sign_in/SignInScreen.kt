package cat.copernic.abp_project_3.settings.presentation.authentication.sign_in

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
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
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import cat.copernic.abp_project_3.R
import cat.copernic.abp_project_3.application.presentation.components.button.ConfirmButtonComp
import cat.copernic.abp_project_3.application.presentation.components.text_field.IconTextFieldComp
import cat.copernic.abp_project_3.application.presentation.components.text_field.PasswordTextFieldComp
import cat.copernic.abp_project_3.settings.domain.authentication.sign_in.SignInViewModel
import cat.copernic.abp_project_3.settings.presentation.SettingsBaseNav
import cat.copernic.abp_project_3.application.presentation.components.label.AuthTitleComponent

/**
 * Hoisted component that contains a viewModel injection and a reference to the SignInScreenUI
 * component
 *
 * @param signInViewModel
 * @param displayNotification
 * @param handleNavigation
 * @param backNavigation
 */
@Composable
fun SignInScreen(
    signInViewModel: SignInViewModel = hiltViewModel(),
    displayNotification: (String) -> Unit,
    handleNavigation: (String) -> Unit,
    backNavigation: () -> Unit
) {
    val signInState by signInViewModel.signInState.collectAsState()

    SignInScreenUI(
        signInState = signInState,
        onEmailChange = { signInViewModel.setEmail(it) },
        onPasswordChange = { signInViewModel.setPassword(it) },
        handleSubmit = {
            if(signInViewModel.validateData(displayNotification)) {
                signInViewModel.handleSignIn(displayNotification, backNavigation)
            }
        },
        handlePasswordRecovery = {
            handleNavigation(SettingsBaseNav.PASSWORD_RECOVERY.name)
        },
        handleBackNavigation = backNavigation
    )
}

/**
 * Component that contains all the ui elements referent to the Sign In Screen
 *
 * @param signInState
 * @param onEmailChange
 * @param onPasswordChange
 * @param handleSubmit
 * @param handlePasswordRecovery
 * @param handleBackNavigation
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SignInScreenUI(
    signInState: SignInState = SignInState(),
    onEmailChange: (String) -> Unit = {},
    onPasswordChange: (String) -> Unit = {},
    handleSubmit: () -> Unit = {},
    handlePasswordRecovery: () -> Unit = {},
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

            // Form Column
            Column(
                verticalArrangement = Arrangement.spacedBy(16.dp),
                modifier = Modifier
                    .padding(innerPadding)
                    .padding(48.dp)
            ) {
                // Title
                AuthTitleComponent(
                    modifier = Modifier,
                    title = stringResource(R.string.sign_in_screen_title),
                    subtitle = stringResource(R.string.sign_in_screen_subtitle)
                )

                // Email Text Field
                IconTextFieldComp(
                    modifier = Modifier.fillMaxWidth(),
                    title = stringResource(R.string.email_form_label),
                    icon = painterResource(R.drawable.baseline_email),
                    iconDescription = stringResource(R.string.email_form_icon_helper),
                    value = signInState.email,
                    onValueChange = onEmailChange
                )
                // Password Text Field
                PasswordTextFieldComp(
                    modifier = Modifier.fillMaxWidth(),
                    title = stringResource(R.string.password_form_label),
                    value = signInState.password,
                    onValueChange = onPasswordChange
                )

                Text(
                    text = stringResource(R.string.password_recovery_link),
                    color = colorResource(R.color.secondaryColor),
                    modifier = Modifier.clickable {
                        handlePasswordRecovery()
                    }
                )

                Row {
                    Spacer(modifier = Modifier.weight(2f))

                    // Send Data Button
                    ConfirmButtonComp(
                        modifier = Modifier.weight(1f),
                        icon = painterResource(R.drawable.baseline_arrow_forward),
                        iconDescription = stringResource(R.string.submit_sign_in_button_helper),
                        onClick = handleSubmit
                    )
                }

            }
        }

    }
}
