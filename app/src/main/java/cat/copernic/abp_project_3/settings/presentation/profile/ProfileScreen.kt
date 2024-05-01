package cat.copernic.abp_project_3.settings.presentation.profile

import android.net.Uri
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationRail
import androidx.compose.material3.NavigationRailItem
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import cat.copernic.abp_project_3.R
import cat.copernic.abp_project_3.settings.domain.profile.ProfileViewModel
import cat.copernic.abp_project_3.settings.presentation.SettingsBaseNav
import cat.copernic.abp_project_3.settings.presentation.profile.account_settings.AccountSettings
import cat.copernic.abp_project_3.settings.presentation.profile.profile_settings.ProfileSettings

/**
 * Enum class that contains all the navigation references inside of the profile screen
 *
 * @property icon
 */
enum class ProfileBaseNav(
    val icon: Int,
    val description: Int
) {
    ACCOUNT_SETTINGS(
        R.drawable.account_circle,
        R.string.account_settings_side_bar_helper
    ),
    PROFILE_SETTINGS(
        R.drawable.badge,
        R.string.profile_settings_side_bar_helper
    ),
}

/**
 * Hoisted component that contains the profileViewModel injection and all the required attributes
 * that have to be provided to the profileScreenUI component
 *
 * @param profileViewModel
 * @param displayNotification
 * @param handleNavigation
 * @param backNavigation
 */
@Composable
fun ProfileScreen(
    profileViewModel: ProfileViewModel = hiltViewModel(),
    displayNotification: (String) -> Unit,
    handleNavigation: (String) -> Unit,
    backNavigation: () -> Unit
) {
    val profileState by profileViewModel.profileState.collectAsState()

    ProfileScreenUI(
        userProfileState = profileState,
        onEmailChange = { profileViewModel.setEmail(it) },
        onNameChange = { profileViewModel.setName(it) },
        onSurnameChange = { profileViewModel.setSurname(it) },
        onDescriptionChange = { profileViewModel.setDescription(it) },
        onPhoneChange = { profileViewModel.setPhone(it) },
        onProfileImageChange = { profileViewModel.setImageUri(it) },

        handleUpdateEmail = {
            profileViewModel.handleEmailRestore(displayNotification)
        },
        handlePasswordRecovery = {
            handleNavigation(SettingsBaseNav.PASSWORD_RECOVERY.name)
        },
        handleSave = {
            profileViewModel.handleProfileUpdate(displayNotification)
        },
        handleDeleteAccount = {
            profileViewModel.handleDeleteAccount(
                displayNotification,
                backNavigation
            )
        },
        handleBackNavigation = backNavigation
    )
}

/**
 * Component that contains the lateral navBar and also the navHost referent to the profile screen
 *
 * @param navController
 * @param userProfileState
 * @param onEmailChange
 * @param onNameChange
 * @param onSurnameChange
 * @param onDescriptionChange
 * @param onPhoneChange
 * @param onProfileImageChange
 * @param handleUpdateEmail
 * @param handlePasswordRecovery
 * @param handleSave
 * @param handleDeleteAccount
 * @param handleBackNavigation
 */
@Composable
fun ProfileScreenUI(
    navController: NavHostController = rememberNavController(),

    userProfileState: ProfileState,
    onEmailChange: (String) -> Unit,
    onNameChange: (String) -> Unit,
    onSurnameChange: (String) -> Unit,
    onDescriptionChange: (String) -> Unit,
    onPhoneChange: (String) -> Unit,
    onProfileImageChange: (Uri?) -> Unit,

    handleUpdateEmail: () -> Unit,
    handlePasswordRecovery: () -> Unit,
    handleSave: () -> Unit,
    handleDeleteAccount: () -> Unit,
    handleBackNavigation: () -> Unit,
) {
    var selectedItem by rememberSaveable {
        mutableStateOf(ProfileBaseNav.ACCOUNT_SETTINGS)
    }

    Row {
        NavigationRail(
            modifier = Modifier
                .width(65.dp)
                .fillMaxHeight()
                .shadow(
                    elevation = 2.dp,
                    ambientColor = Color.Black
                ),
            header = {
                Icon(
                    modifier = Modifier
                        .padding(vertical = 16.dp)
                        .clickable {
                            handleBackNavigation()
                        },
                    painter = painterResource(R.drawable.arrow_back),
                    contentDescription = stringResource(R.string.back_button_helper)
                )
            }
        ) {
            ProfileBaseNav.entries.forEach { item ->
                NavigationRailItem(
                    selected = selectedItem == item,
                    onClick = {
                        selectedItem = item
                        navController.navigate(item.name)
                    },
                    icon = {
                        Icon(
                            painter = painterResource(item.icon),
                            contentDescription = stringResource(item.description)
                        )
                    }
                )
            }
        }

        NavHost(
            navController = navController,
            startDestination = selectedItem.name,
            modifier = Modifier
                .fillMaxSize()
                .padding(8.dp)
        )  {
            composable(ProfileBaseNav.ACCOUNT_SETTINGS.name) {
                AccountSettings(
                    emailValue = userProfileState.email,
                    onEmailValueChange = onEmailChange,
                    handleUpdateEmail = handleUpdateEmail,
                    handlePasswordRecovery = handlePasswordRecovery,
                    handleDeleteAccount = handleDeleteAccount
                )
            }
            composable(ProfileBaseNav.PROFILE_SETTINGS.name) {
                ProfileSettings(
                    userProfileState = userProfileState.userProfile,
                    onNameValueChange = onNameChange,
                    onSurnameValueChange = onSurnameChange,
                    onPhoneValueChange = onPhoneChange,
                    onDescriptionValueChange = onDescriptionChange,
                    onProfileImageValueChange = onProfileImageChange,
                    handleSaveData = handleSave
                )
            }
        }
    }
}




