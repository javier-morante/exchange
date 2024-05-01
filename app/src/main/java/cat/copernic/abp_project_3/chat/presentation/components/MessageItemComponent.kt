package cat.copernic.abp_project_3.chat.presentation.components

import android.annotation.SuppressLint
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.widthIn
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import cat.copernic.abp_project_3.R
import cat.copernic.abp_project_3.application.data.model.Message

/**
 * Composable function that displays a message item within a conversation.
 *
 * @param modifier The modifier for positioning and sizing the component.
 * @param isAuthUserOwner A boolean flag indicating whether the authenticated user owns the message.
 * @param message The message to be displayed.
 * @param onSelectMessage Callback function invoked when the message item is selected.
 */
@OptIn(ExperimentalFoundationApi::class)
@SuppressLint("SimpleDateFormat")
@Composable
fun MessageItemComponent(
    modifier: Modifier = Modifier,
    isAuthUserOwner: Boolean,
    message: Message,
    onSelectMessage: (Message) -> Unit
) {
    val primaryColor = MaterialTheme.colorScheme.primary
    val secondaryColor = MaterialTheme.colorScheme.primaryContainer

    Box(
        contentAlignment = if(isAuthUserOwner) Alignment.CenterEnd else Alignment.CenterStart,
        modifier = modifier.clip(MaterialTheme.shapes.medium)
    ) {
        Card(
            colors = CardDefaults.cardColors(
                containerColor = if(isAuthUserOwner) primaryColor else secondaryColor
            ),
            modifier = Modifier
                .widthIn(0.dp, 250.dp)
                .combinedClickable(
                    onClick = {},
                    onLongClick = { onSelectMessage(message) }
                )
        ) {
            Column(
                verticalArrangement = Arrangement.spacedBy(8.dp),
                modifier = Modifier.padding(16.dp)
            ) {
                if(!message.isEnabled) {
                    Icon(
                        modifier = Modifier.size(15.dp),
                        painter = painterResource(R.drawable.not_interested),
                        contentDescription = null
                    )
                } else {
                    Text(
                        style = MaterialTheme.typography.titleSmall,
                        text = message.content
                    )
                }
            }
        }
    }
}