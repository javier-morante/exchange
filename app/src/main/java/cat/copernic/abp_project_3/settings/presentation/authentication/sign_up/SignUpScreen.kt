package cat.copernic.abp_project_3.settings.presentation.authentication.sign_up

import android.net.Uri
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
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
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import cat.copernic.abp_project_3.R
import cat.copernic.abp_project_3.settings.domain.authentication.sign_up.SignUpViewModel
import cat.copernic.abp_project_3.settings.presentation.authentication.sign_up.credentials_phase.SignUpCredentialsComponent
import cat.copernic.abp_project_3.settings.presentation.authentication.sign_up.data_phase.SignUpDataComponent

/**
 * Enum class that contains all the navigation references for the Sign Up Screen navigation
 *
 */
enum class SignUpBaseNav {
    CREDENTIALS,
    DATA
}

/**
 * Hoisted component that contains a viewModel injection and a reference to the
 * SignUpScreenUI component
 *
 * @param signUpViewModel
 * @param navController
 * @param displayNotification
 * @param backNavigation
 */
@Composable
fun SignUpScreen(
    signUpViewModel: SignUpViewModel = hiltViewModel(),
    navController: NavHostController = rememberNavController(),
    displayNotification: (String) -> Unit,
    handleNavigation: (String) -> Unit,
    backNavigation: () -> Unit
) {
    val actualScreen by signUpViewModel.actualScreen.collectAsState()
    val signUpState by signUpViewModel.signUpState.collectAsState()

    LaunchedEffect(key1 = actualScreen) {
        navController.navigate(actualScreen)
    }

    SignUpScreenUI(
        navController = navController,
        handleBackNavigation = backNavigation,
        signUpState = signUpState,
        onEmailChange = { signUpViewModel.setEmail(it) },
        onPasswordChange = { signUpViewModel.setPassword(it) },
        onRepeatPasswordChange = { signUpViewModel.setRepeatPassword(it) },
        onNameChange = { signUpViewModel.setName(it) },
        onSurnameChange = { signUpViewModel.setSurname(it) },
        onPhoneChange = { signUpViewModel.setPhone(it) },
        imageSelectMessage = if(signUpState.accountImageUri == null) stringResource(R.string.sign_up_data_screen_pick_image_button) else stringResource(R.string.sign_up_data_screen_image_picked_button),
        onAccountImageChange = { signUpViewModel.setAccountImageUri(it) },
        handleSubmitCredentials = {
            if(signUpViewModel.validateStepOne(displayNotification)) {
                signUpViewModel.setActualScreen(SignUpBaseNav.DATA.name)
            }
        },
        handleSubmitData = {
            if(signUpViewModel.validateStepTwo(displayNotification)) {
                signUpViewModel.handleSignUp(
                    displayNotification,
                    handleNavigation
                )
            }
        }
    )
}


/**
 * Component that contains the navHost referent to the Sign Up Screen
 *
 * @param navController
 * @param handleBackNavigation
 * @param signUpState
 * @param onEmailChange
 * @param onPasswordChange
 * @param onRepeatPasswordChange
 * @param onNameChange
 * @param onSurnameChange
 * @param onPhoneChange
 * @param imageSelectMessage
 * @param onAccountImageChange
 * @param handleSubmitCredentials
 * @param handleSubmitData
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SignUpScreenUI(
    navController: NavHostController = rememberNavController(),
    handleBackNavigation: () -> Unit,

    signUpState: SignUpState,
    onEmailChange: (String) -> Unit,
    onPasswordChange: (String) -> Unit,
    onRepeatPasswordChange: (String) -> Unit,
    onNameChange: (String) -> Unit,
    onSurnameChange: (String) -> Unit,
    onPhoneChange: (String) -> Unit,
    imageSelectMessage: String,
    onAccountImageChange: (Uri?) -> Unit,

    handleSubmitCredentials: () -> Unit,
    handleSubmitData: () -> Unit
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
            modifier = Modifier
                .fillMaxSize()
        ) {
            // Background Image
            Image(
                painter = painterResource(R.drawable.auth_background),
                contentDescription = stringResource(R.string.authentication_background_image_helper),
                modifier = Modifier.matchParentSize(),
                contentScale = ContentScale.Crop
            )

            NavHost(
                navController = navController,
                startDestination = SignUpBaseNav.CREDENTIALS.name,
                modifier = Modifier.padding(innerPadding)
            ) {
                composable(SignUpBaseNav.CREDENTIALS.name) {
                    SignUpCredentialsComponent(
                        emailValue = signUpState.email,
                        onEmailChange = onEmailChange,
                        passwordValue = signUpState.password,
                        onPasswordChange = onPasswordChange,
                        repeatPasswordValue = signUpState.repeatPassword,
                        onRepeatPasswordChange = onRepeatPasswordChange,
                        handleSubmitCredentials = handleSubmitCredentials
                    )
                }
                composable(SignUpBaseNav.DATA.name) {
                    SignUpDataComponent(
                        nameValue = signUpState.userProfile.name,
                        onNameChange = onNameChange,
                        surnameValue = signUpState.userProfile.surname,
                        onSurnameChange = onSurnameChange,
                        phoneValue = signUpState.userProfile.phone,
                        onPhoneChange = onPhoneChange,
                        accountSelectMessage = imageSelectMessage,
                        onAccountImageChange = onAccountImageChange,
                        handleSubmitData = handleSubmitData
                    )
                }
            }
        }
    }
}