package cat.copernic.abp_project_3.chat.presentation.conversation_messages

import android.view.Gravity
import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Scaffold
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import cat.copernic.abp_project_3.R
import cat.copernic.abp_project_3.chat.domain.ConversationMessagesViewModel
import cat.copernic.abp_project_3.chat.presentation.components.ConversationSendBar
import cat.copernic.abp_project_3.chat.presentation.components.ConversationTopUserProfile
import cat.copernic.abp_project_3.chat.presentation.components.MessageItemComponent
import cat.copernic.abp_project_3.application.presentation.components.button.ConfirmButtonComp
import cat.copernic.abp_project_3.application.presentation.components.button.DangerButtonComp
import cat.copernic.abp_project_3.application.presentation.components.button.StylizedButtonComponent
import cat.copernic.abp_project_3.application.presentation.components.divider.CustomDivider
import cat.copernic.abp_project_3.application.presentation.components.label.CustomInfoLabel
import cat.copernic.abp_project_3.application.presentation.components.label.CustomTitle
import java.text.SimpleDateFormat

/**
 * Composable function that displays a screen for viewing and interacting with conversation messages.
 *
 * @param conversationsMessagesViewModel The view model providing data and logic for the conversation messages.
 * @param conversationId The ID of the conversation to display messages for.
 * @param profileId The ID of the user's profile associated with the conversation.
 * @param handleBackNavigation Callback function invoked to handle back navigation.
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ConversationMessagesScreen(
    conversationsMessagesViewModel: ConversationMessagesViewModel = hiltViewModel(),
    conversationId: String,
    profileId: String,
    displayNotification: (String) -> Unit,
    handleBackNavigation: () -> Unit
) {
    val currentConvMessState by conversationsMessagesViewModel.currentConvMessState.collectAsState()

    val messagesList = conversationsMessagesViewModel.messagesList

    val simpleDateFormat = SimpleDateFormat("dd/MM/yyyy HH:mm")


    var settingsMenuStatus by rememberSaveable { mutableStateOf(false) }

    LaunchedEffect(key1 = conversationId) {
        conversationsMessagesViewModel.setConversationId(conversationId)
        conversationsMessagesViewModel.gatherMessages(profileId = profileId)
    }


    Scaffold(
        topBar = {
            TopAppBar(
                modifier = Modifier
                    .shadow(2.dp),
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
                actions = {
                    IconButton(
                        onClick = { settingsMenuStatus = true }
                    ) {
                        Icon(
                            painter = painterResource(R.drawable.baseline_settings),
                            contentDescription = stringResource(R.string.conversation_settings_button)
                        )
                    }
                },
                title = {
                    ConversationTopUserProfile(
                        profile = currentConvMessState.userProfile
                    )
                }
            )
        }
    ) { innerPadding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
        ) {

            Column {
                
                Spacer(modifier = Modifier.height(16.dp))
                
                LazyColumn(
                    verticalArrangement = Arrangement.spacedBy(8.dp),
                    reverseLayout = true,
                    modifier = Modifier
                        .fillMaxWidth()
                        .weight(6f)
                        .padding(horizontal = 16.dp)
                ) {
                    items(messagesList) { item ->
                        MessageItemComponent(
                            modifier = Modifier.fillMaxWidth(),
                            isAuthUserOwner = currentConvMessState.currentAuthUserUid == item.ownerRef,
                            message = item,
                            onSelectMessage = {
                                if(currentConvMessState.currentAuthUserUid == it.ownerRef) {
                                    conversationsMessagesViewModel.setSelectedMessage(it)
                                }
                            }
                        )
                    }
                }

                ConversationSendBar(
                    modifier = Modifier
                        .fillMaxWidth()
                        .weight(1f)
                        .background(MaterialTheme.colorScheme.background)
                        .padding(8.dp)
                        .padding(horizontal = 16.dp),
                    value = currentConvMessState.sendMessageFrameValue,
                    onValueChange = { conversationsMessagesViewModel.setSendMessageFrameValue(it) },
                    handleSubmit = {
                        conversationsMessagesViewModel.handleSendMessage()
                    }
                )
            }
        }

        if(currentConvMessState.selectedMessage != null) {
            ModalBottomSheet(
                onDismissRequest = {
                    conversationsMessagesViewModel.setSelectedMessage(null)
                },
                sheetState = rememberModalBottomSheetState()
            ) {

                Column(
                    modifier = Modifier.padding(16.dp)
                ) {

                    CustomInfoLabel(
                        title = stringResource(R.string.conversations_conversation_message_date_label),
                        value = simpleDateFormat.format(currentConvMessState.selectedMessage?.date?.toDate()!!)
                    )

                    CustomDivider(space = 8.dp)

                    CustomInfoLabel(
                        title = stringResource(R.string.conversations_conversation_message_content_label),
                        value = currentConvMessState.selectedMessage?.content!!
                    )
                    
                    Spacer(modifier = Modifier.height(32.dp))

                    if(currentConvMessState.selectedMessage?.isEnabled!!) {
                        DangerButtonComp(
                            modifier = Modifier.fillMaxWidth(),
                            title = stringResource(R.string.conversations_conversation_delete_message_label),
                            onClick = {
                                conversationsMessagesViewModel.handleMessageState(displayNotification)
                                conversationsMessagesViewModel.setSelectedMessage(null)
                            }
                        )
                    } else {
                        ConfirmButtonComp(
                            modifier = Modifier.fillMaxWidth(),
                            title = stringResource(R.string.conversations_conversation_restore_message_label),
                            onClick = {
                                conversationsMessagesViewModel.handleMessageState(displayNotification)
                                conversationsMessagesViewModel.setSelectedMessage(null)
                            }
                        )
                    }

                    Spacer(modifier = Modifier.height(64.dp))
                }
            }
        }

        if(settingsMenuStatus) {
            ModalBottomSheet(
                onDismissRequest = {
                    settingsMenuStatus = false
                },
                sheetState = rememberModalBottomSheetState()
            ) {

                Column(
                    modifier = Modifier.padding(16.dp)
                ) {

                    CustomTitle(
                        title = stringResource(R.string.conversations_conversation_settings_title)
                    )
                    
                    StylizedButtonComponent(
                        title = stringResource(R.string.conversations_conversation_delete_conversation_label),
                        leadingIcon = painterResource(R.drawable.baseline_delete),
                        onClick = {
                            conversationsMessagesViewModel.handleDeleteConversation(
                                displayNotification,
                                handleBackNavigation
                            )
                        }
                    )

                    Spacer(modifier = Modifier.height(64.dp))
                }
            }
        }

    }
}