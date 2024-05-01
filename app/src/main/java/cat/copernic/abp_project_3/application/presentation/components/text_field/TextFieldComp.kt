package cat.copernic.abp_project_3.application.presentation.components.text_field

import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import cat.copernic.abp_project_3.R

/**
 * Composable function representing a custom text field component with outlined style.
 * This component provides a labeled text field for user input.
 *
 * @param modifier The modifier to be applied to the text field.
 * @param title The title or label displayed above the text field.
 * @param value The current value of the text field.
 * @param onValueChange The callback function invoked when the text field value changes.
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TextFieldComp(
    modifier: Modifier,
    title: String,
    value: String,
    onValueChange: (String) -> Unit,
) {
    OutlinedTextField(
        value = value,
        onValueChange = onValueChange,
        label = {
            Text(
                style = MaterialTheme.typography.titleSmall,
                text = title
            )
        },
        maxLines = 1,
        shape = MaterialTheme.shapes.medium,
        colors = TextFieldDefaults.outlinedTextFieldColors(
            unfocusedBorderColor = colorResource(R.color.thirdColor),
            unfocusedTextColor = colorResource(R.color.subtitle)
        ),
        modifier = modifier
    )
}

/**
 * Composable function representing a custom text field component with an icon and outlined style.
 * This component provides a labeled text field with an icon leading the input field.
 *
 * @param modifier The modifier to be applied to the text field.
 * @param title The title or label displayed above the text field.
 * @param icon The icon to be displayed at the beginning of the text field.
 * @param iconDescription The description of the icon for accessibility purposes.
 * @param value The current value of the text field.
 * @param onValueChange The callback function invoked when the text field value changes.
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun IconTextFieldComp(
    modifier: Modifier,
    title: String,
    icon: Painter,
    iconDescription: String,
    value: String,
    onValueChange: (String) -> Unit
) {
    OutlinedTextField(
        value = value,
        onValueChange = onValueChange,
        label = {
            Text(
                style = MaterialTheme.typography.titleSmall,
                text = title
            )
        },
        leadingIcon = {
            Icon(
                painter = icon,
                contentDescription = iconDescription,
            )
        },
        maxLines = 1,
        shape = MaterialTheme.shapes.medium,
        colors = TextFieldDefaults.outlinedTextFieldColors(
            unfocusedBorderColor = colorResource(R.color.thirdColor),
            unfocusedTextColor = colorResource(R.color.subtitle)
        ),
        modifier = modifier
    )
}

/**
 * Composable function representing a password text field component with outlined style.
 * This component provides a labeled password input field with a toggle button to show/hide the password.
 *
 * @param modifier The modifier to be applied to the text field.
 * @param title The title or label displayed above the password field.
 * @param value The current value of the password field.
 * @param onValueChange The callback function invoked when the password field value changes.
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PasswordTextFieldComp(
    modifier: Modifier,
    title: String,
    value: String,
    onValueChange: (String) -> Unit
) {
    var passwordVisibility by rememberSaveable { mutableStateOf(false) }

    // Icon State
    val visibleIcon = painterResource(R.drawable.baseline_visibility)
    val hiddenIcon = painterResource(R.drawable.baseline_visibility_off)
    val visibilityIcon = if(passwordVisibility) visibleIcon else hiddenIcon

    // Visual Transformation State
    val defaultTransform = VisualTransformation.None
    val passwordTransform = PasswordVisualTransformation()
    var actualTransformation = if(passwordVisibility) defaultTransform else passwordTransform

    OutlinedTextField(
        value = value,
        onValueChange = onValueChange,
        visualTransformation = actualTransformation,
        label = {
            Text(
                style = MaterialTheme.typography.titleSmall,
                text = title
            )
        },
        leadingIcon = {
            Icon(
                painter = painterResource(R.drawable.baseline_lock),
                contentDescription = stringResource(R.string.password_form_icon_helper),
            )
        },
        trailingIcon = {
            IconButton(
                onClick = {
                    passwordVisibility = !passwordVisibility
                }
            ) {
                Icon(
                    painter = visibilityIcon,
                    contentDescription = stringResource(R.string.password_field_visibility_button)
                )
            }
        },
        maxLines = 1,
        shape = MaterialTheme.shapes.medium,
        colors = TextFieldDefaults.outlinedTextFieldColors(
            unfocusedBorderColor = colorResource(R.color.thirdColor),
            unfocusedTextColor = colorResource(R.color.subtitle)
        ),
        modifier = modifier
    )
}