package cat.copernic.abp_project_3.chat.presentation.components

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import cat.copernic.abp_project_3.R
import cat.copernic.abp_project_3.application.presentation.components.button.ConfirmButtonComp

/**
 * Composable function that displays a send bar for sending conversation messages.
 *
 * @param modifier The modifier for positioning and sizing the component.
 * @param value The current value of the text input field.
 * @param onValueChange The lambda function to update the text input field value.
 * @param handleSubmit The lambda function to execute when the send action is triggered.
 */
@Composable
fun ConversationSendBar(
    modifier: Modifier = Modifier,
    value: String,
    onValueChange: (String) -> Unit,
    handleSubmit: () -> Unit,
) {
    Box(
        contentAlignment = Alignment.Center,
        modifier = modifier
    ) {
        OutlinedTextField(
            value = value,
            onValueChange = onValueChange,
            keyboardOptions = KeyboardOptions(imeAction = ImeAction.Send),
            keyboardActions = KeyboardActions(
                onSend = {
                    handleSubmit()
                }
            ),
            shape = MaterialTheme.shapes.medium,
            maxLines = 1,
            trailingIcon = {
                ConfirmButtonComp(
                    modifier = Modifier.padding(end = 8.dp),
                    icon = painterResource(R.drawable.send),
                    iconDescription = stringResource(R.string.send_message_button_helper),
                    onClick = {
                        handleSubmit()
                    }
                )
            },
            colors = TextFieldDefaults.colors(
                disabledTextColor = Color.Transparent,
                unfocusedContainerColor = Color.Transparent,
                focusedIndicatorColor = Color.Transparent,
                focusedContainerColor = Color.Transparent,
                unfocusedIndicatorColor = Color.Transparent,
                disabledIndicatorColor = Color.Transparent
            ),
            modifier = Modifier
                .fillMaxWidth()
                .border(
                    2.dp,
                    MaterialTheme.colorScheme.primaryContainer,
                    MaterialTheme.shapes.medium
                ),
            placeholder = {
                Text(
                    text = stringResource(R.string.conversations_conversation_send_bar_label)
                )
            }
        )
    }
}