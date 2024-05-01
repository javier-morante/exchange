package cat.copernic.abp_project_3.settings.presentation.profile.profile_settings

import android.net.Uri
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import cat.copernic.abp_project_3.R
import cat.copernic.abp_project_3.application.presentation.components.button.ConfirmButtonComp
import cat.copernic.abp_project_3.application.presentation.components.divider.CustomDivider
import cat.copernic.abp_project_3.application.presentation.components.label.CustomTitle
import cat.copernic.abp_project_3.application.presentation.components.resource_picker.ImagePicker
import cat.copernic.abp_project_3.application.presentation.components.text_field.CustomLongTextField
import cat.copernic.abp_project_3.application.presentation.components.text_field.CustomTextField
import cat.copernic.abp_project_3.application.data.model.Profile

/**
 * Component that contains all the ui elements referent to the profile settings screen
 *
 * @param modifier
 * @param userProfileState
 * @param onNameValueChange
 * @param onSurnameValueChange
 * @param onPhoneValueChange
 * @param onDescriptionValueChange
 * @param onProfileImageValueChange
 * @param handleSaveData
 */
@Composable
fun ProfileSettings(
    modifier: Modifier = Modifier,

    userProfileState: Profile,
    onNameValueChange: (String) -> Unit,
    onSurnameValueChange: (String) -> Unit,
    onPhoneValueChange: (String) -> Unit,
    onDescriptionValueChange: (String) -> Unit,
    onProfileImageValueChange: (Uri?) -> Unit,

    handleSaveData: () -> Unit
) {
    Box(
        modifier = Modifier.fillMaxSize()
    ){
        Column(
            modifier = modifier
                .fillMaxWidth()
        ) {

            // Title
            CustomTitle(
                title = stringResource(R.string.account_profile_settings_title)
            )

            Spacer(modifier = Modifier.height(28.dp))

            // Profile Image Picker
            ImagePicker(
                title = stringResource(R.string.account_profile_settings_image_picker_label),
                profileImageUrl = userProfileState.imageUrl,
                handleImageChange = onProfileImageValueChange
            )

            CustomDivider()

            // Name Text Field
            CustomTextField(
                title = stringResource(R.string.account_profile_settings_name_label),
                value = userProfileState.name,
                onValueChange = onNameValueChange
            )

            CustomDivider()

            // Surname Text Field
            CustomTextField(
                title = stringResource(R.string.account_profile_settings_surname_label),
                value = userProfileState.surname,
                onValueChange = onSurnameValueChange
            )

            CustomDivider()

            // Phone Text Field
            CustomTextField(
                title = stringResource(R.string.account_profile_settings_phone_label),
                value = userProfileState.phone,
                onValueChange = onPhoneValueChange
            )

            CustomDivider()

            // Description Text Field
            CustomLongTextField(
                title = stringResource(R.string.account_profile_settings_description_label),
                value = userProfileState.description,
                onValueChange = onDescriptionValueChange
            )
        }

        // Save Profile Button
        ConfirmButtonComp(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 12.dp)
                .align(Alignment.BottomCenter),
            title = stringResource(R.string.account_profile_settings_save_data_label),
            onClick = handleSaveData
        )
    }
}