package cat.copernic.abp_project_3.settings.presentation.users.user_details

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import cat.copernic.abp_project_3.R
import cat.copernic.abp_project_3.application.presentation.components.button.ConfirmButtonComp
import cat.copernic.abp_project_3.application.presentation.components.divider.CustomDivider
import cat.copernic.abp_project_3.application.presentation.components.dropdown.UserRoleDropdown
import cat.copernic.abp_project_3.application.presentation.components.resource_picker.ImagePicker
import cat.copernic.abp_project_3.application.presentation.components.switch.CustomSwitch
import cat.copernic.abp_project_3.application.presentation.components.text_field.CustomLongTextField
import cat.copernic.abp_project_3.application.presentation.components.text_field.CustomTextField
import cat.copernic.abp_project_3.settings.domain.users.UserDetailsViewModel

/**
 * Component that contains all the ui elements that refer to the User details screen
 *
 * @param userCreationViewModel
 * @param displayNotification
 * @param handleBackNavigation
 * @param userProfileId
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun UserDetailsComponent(
    userCreationViewModel: UserDetailsViewModel = hiltViewModel(),
    displayNotification: (String) -> Unit,
    handleBackNavigation: () -> Unit,
    userProfileId: String?
) {
    val userProfileDetailsState by userCreationViewModel.userProfileCreationState.collectAsState()

    LaunchedEffect(key1 = userProfileId) {
        if(userProfileId != null) {
            userCreationViewModel.getProfileData(userProfileId)
        } else {
            handleBackNavigation()
        }
    }

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
                            contentDescription = stringResource(R.string.back_button_helper)
                        )
                    }
                },
                title = {
                    Text(
                        style = MaterialTheme.typography.titleLarge,
                        text = stringResource(R.string.user_management_screen_title)
                    )
                }
            )
        }
    ) { innerPadding ->

        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(16.dp)
        ) {

            Column(
                verticalArrangement = Arrangement.spacedBy(8.dp),
                modifier = Modifier.verticalScroll(
                    rememberScrollState()
                )
            ) {
                // Image Picker Field
                ImagePicker(
                    title = stringResource(R.string.account_profile_settings_image_picker_label),
                    profileImageUrl = userProfileDetailsState.userProfile.imageUrl,
                    handleImageChange = { userCreationViewModel.setProfileImageUri(it) }
                )

                CustomDivider()

                // Name text Field
                CustomTextField(
                    title = stringResource(R.string.account_profile_settings_name_label),
                    value = userProfileDetailsState.userProfile.name,
                    onValueChange = { userCreationViewModel.setName(it) }
                )

                CustomDivider()

                // Surname text Field
                CustomTextField(
                    title = stringResource(R.string.account_profile_settings_surname_label),
                    value = userProfileDetailsState.userProfile.surname,
                    onValueChange = { userCreationViewModel.setSurname(it) }
                )

                CustomDivider()

                // Phone text Field
                CustomTextField(
                    title = stringResource(R.string.account_profile_settings_phone_label),
                    value = userProfileDetailsState.userProfile.phone,
                    onValueChange = { userCreationViewModel.setPhone(it) }
                )

                CustomDivider()

                // Description text Field
                CustomLongTextField(
                    title = stringResource(R.string.account_profile_settings_description_label),
                    value = userProfileDetailsState.userProfile.description,
                    onValueChange = { userCreationViewModel.setDescription(it) }
                )

                CustomDivider()

                // Role Dropdown Field
                UserRoleDropdown(
                    title = stringResource(R.string.user_details_role_label),
                    value = userProfileDetailsState.userProfile.role.name,
                    onValueChange = { userCreationViewModel.setRole(it) }
                )

                CustomDivider()

                // Profile Enabled Switch Field
                CustomSwitch(
                    title = stringResource(R.string.user_details_profile_enabled_label),
                    value = userProfileDetailsState.userProfile.isEnabled,
                    onValueChange = {
                        userCreationViewModel.setIsEnabled(
                            !userProfileDetailsState.userProfile.isEnabled
                        )
                    }
                )
                
                Spacer(modifier = Modifier.height(48.dp))
            }

            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .align(Alignment.BottomCenter)
                    .clip(MaterialTheme.shapes.medium)
                    .background(Color.White)
            ) {
                // Submit Button
                ConfirmButtonComp(
                    modifier = Modifier
                        .fillMaxWidth()
                    ,
                    title = stringResource(R.string.category_details_update_button_label),
                    onClick = {
                        userCreationViewModel.handleUpdateProfile(
                            displayToast = displayNotification,
                            handleBackNavigation = handleBackNavigation
                        )
                    }
                )
            }

        }

    }
}