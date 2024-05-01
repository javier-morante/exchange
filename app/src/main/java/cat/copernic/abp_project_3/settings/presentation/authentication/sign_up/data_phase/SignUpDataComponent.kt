package cat.copernic.abp_project_3.settings.presentation.authentication.sign_up.data_phase

import android.Manifest
import android.content.ContentValues
import android.content.ContentValues.TAG
import android.content.Context
import android.net.Uri
import android.util.Log
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import cat.copernic.abp_project_3.R

import cat.copernic.abp_project_3.application.presentation.components.button.ConfirmButtonComp
import cat.copernic.abp_project_3.application.presentation.components.text_field.TextFieldComp
import cat.copernic.abp_project_3.application.presentation.components.label.AuthTitleComponent

/**
 * Component that contains all the ui elements referent to the Sign Up Data phase Screen
 *
 * @param nameValue
 * @param onNameChange
 * @param surnameValue
 * @param onSurnameChange
 * @param phoneValue
 * @param onPhoneChange
 * @param accountSelectMessage
 * @param onAccountImageChange
 * @param handleSubmitData
 */
@Composable
fun SignUpDataComponent(
    nameValue: String,
    onNameChange: (String) -> Unit,
    surnameValue: String,
    onSurnameChange: (String) -> Unit,
    phoneValue: String,
    onPhoneChange: (String) -> Unit,

    accountSelectMessage: String,
    onAccountImageChange: (Uri?) -> Unit,

    handleSubmitData: () -> Unit
) {

    val context: Context = LocalContext.current

    // Launcher for the profile image Selection ------------------------------------------------------------
    val launcher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent()
    ) { uri: Uri? ->
        onAccountImageChange(uri)
        Log.d(ContentValues.TAG, uri.toString())
    }

    val permissionRequest = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.RequestMultiplePermissions(),
        onResult = { permissions ->
            if(!permissions.values.all { it }) {
                Log.d(TAG, "No location permissions provided")
            }else {
                handleSubmitData()
            }
        }
    )

    Column(
        verticalArrangement = Arrangement.spacedBy(16.dp),
        modifier = Modifier.padding(48.dp)
    ) {
        // Title
        AuthTitleComponent(
            modifier = Modifier,
            title = stringResource(R.string.sign_up_data_screen_title),
            subtitle = stringResource(R.string.sign_up_data_screen_subtitle)
        )
        // Name Text Field
        TextFieldComp(
            modifier = Modifier.fillMaxWidth(),
            title = stringResource(R.string.name_form_label),
            value = nameValue,
            onValueChange = onNameChange
        )
        // Surname Text Field
        TextFieldComp(
            modifier = Modifier.fillMaxWidth(),
            title = stringResource(R.string.surname_form_label),
            value = surnameValue,
            onValueChange = onSurnameChange
        )
        // Phone Text Field
        TextFieldComp(
            modifier = Modifier.fillMaxWidth(),
            title = stringResource(R.string.phone_form_label),
            value = phoneValue,
            onValueChange = onPhoneChange
        )
        // Pick Image Button
        ConfirmButtonComp(
            modifier = Modifier.fillMaxWidth(),
            title = accountSelectMessage,
            icon = painterResource(R.drawable.baseline_save),
            iconDescription = stringResource(R.string.save_profile_button_helper),
            onClick = { launcher.launch("image/*")}
        )

        Row {
            Spacer(modifier = Modifier.weight(2f))

            // Send Data Button
            ConfirmButtonComp(
                modifier = Modifier.weight(1f),
                icon = painterResource(R.drawable.baseline_arrow_forward),
                iconDescription = stringResource(R.string.submit_sign_up_data_button_helper),
                onClick = {
                    permissionRequest.launch(
                        arrayOf(
                            Manifest.permission.ACCESS_FINE_LOCATION,
                            Manifest.permission.ACCESS_COARSE_LOCATION
                        )
                    )
                }
            )
        }
    }
}