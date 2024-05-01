package cat.copernic.abp_project_3.chat.domain

import androidx.compose.runtime.mutableStateListOf
import cat.copernic.abp_project_3.application.domain.ApplicationViewModel
import cat.copernic.abp_project_3.chat.presentation.conversation_messages.ConversationMessagesScreenState
import cat.copernic.abp_project_3.application.data.model.Message
import cat.copernic.abp_project_3.application.data.model.Profile
import cat.copernic.abp_project_3.application.data.service.authentication.AuthenticationService
import cat.copernic.abp_project_3.application.data.service.conversations_management.ConversationsManagementService
import cat.copernic.abp_project_3.application.data.service.message.MessageService
import cat.copernic.abp_project_3.application.data.service.profile.ProfileService
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import javax.inject.Inject

/**
 * ViewModel responsible for managing the state and business logic related to conversation messages.
 *
 * This ViewModel integrates with various services including authentication, profiles, messages,
 * and conversations to handle message-related operations and state management.
 *
 * @param authenticationService The service responsible for handling authentication.
 * @param profileService The service used to retrieve and manage user profiles.
 * @param messageService The service for interacting with message-related data and operations.
 * @param conversationService The service handling conversation-related data and operations.
 */
@HiltViewModel
class ConversationMessagesViewModel @Inject constructor(
    private val authenticationService: AuthenticationService,
    private val profileService: ProfileService,
    private val messageService: MessageService,
    private val conversationsManagementService: ConversationsManagementService,
): ApplicationViewModel() {

    private var _currentConvMessState = MutableStateFlow(
        ConversationMessagesScreenState()
    )
    val currentConvMessState get() = _currentConvMessState.asStateFlow()

    /**
     * Sets the current authenticated user's UID in the view model state.
     *
     * @param newAuthUserUid The UID of the authenticated user.
     */
    fun setCurrentAuthUserUid(newAuthUserUid: String) {
        _currentConvMessState.update { currentState ->
            currentState.setCurrentAuthUserUid(newAuthUserUid)
        }
    }

    /**
     * Sets the conversation ID in the view model state.
     *
     * @param newConversationId The ID of the conversation.
     */
    fun setConversationId(newConversationId: String) {
        _currentConvMessState.update { currentState ->
            currentState.setConversationId(newConversationId)
        }
    }

    /**
     * Sets the user profile in the view model state.
     *
     * @param newUserProfile The profile of the user.
     */
    fun setUserProfile(newUserProfile: Profile) {
        _currentConvMessState.update { currentState ->
            currentState.setProfile(newUserProfile)
        }
    }

    /**
     * Sets the value of the message to be sent in the view model state.
     *
     * @param newSendMessageFrameValue The content of the message to be sent.
     */
    fun setSendMessageFrameValue(newSendMessageFrameValue: String) {
        _currentConvMessState.update { currentState ->
            currentState.setSendMessageFrameValue(newSendMessageFrameValue)
        }
    }

    /**
     * Sets the selected message in the view model state.
     *
     * @param newSelectedMessage The message selected by the user.
     */
    fun setSelectedMessage(newSelectedMessage: Message?) {
        _currentConvMessState.update { currentState ->
            currentState.setSelectedMessage(newSelectedMessage)
        }
    }


    private var _messagesList = mutableStateListOf<Message>()
    val messagesList get() = _messagesList.toList()


    /**
     * Gathers and populates the list of messages for a given profile ID.
     *
     * @param profileId The ID of the profile for which messages are gathered.
     */
    fun gatherMessages(
        profileId: String
    ) {
        launchCatching {
            setCurrentAuthUserUid(
                authenticationService.currentAuthUserUid ?: ""
            )

            setUserProfile(
                profileService.getProfile(profileId) ?: Profile()
            )

            messageService.getActualConversationMessages(
                _currentConvMessState.value.conversationId
            ).collect { messages ->
                _messagesList.clear()
                _messagesList.addAll(messages)
            }
        }
    }

    /**
     * Handles sending a new message in the conversation.
     */
    fun handleSendMessage() {
        launchCatching {
            messageService.createConversationMessage(
                _currentConvMessState.value.conversationId,
                Message(
                    ownerRef = authenticationService.currentAuthUserUid ?: "",
                    orderId = if(_messagesList.toList().isEmpty()) { 1 } else { _messagesList.toList()[0].orderId + 1 },
                    content = _currentConvMessState.value.sendMessageFrameValue
                )
            )
            setSendMessageFrameValue("")
        }
    }

    /**
     * Handles updating the state of a message (e.g., toggling its status or deleting it).
     *
     * @param displayToast Function to display a toast message.
     */
    fun handleMessageState(
        displayToast: (String) -> Unit
    ) {
        launchCatching(
            displayToast
        ) {
            _currentConvMessState.value.selectedMessage?.isEnabled = !_currentConvMessState.value.selectedMessage?.isEnabled!!

            messageService.updateConversationMessage(
                _currentConvMessState.value.conversationId,
                _currentConvMessState.value.selectedMessage ?: Message()
            )

            displayToast("Message Successfully Updated")
        }
    }

    /**
     * Handles deleting the current conversation and navigating back.
     *
     * @param displayToast Function to display a toast message.
     * @param handleBackNavigation Function to navigate back after deletion.
     */
    fun handleDeleteConversation(
        displayToast: (String) -> Unit,
        handleBackNavigation: () -> Unit
    ) {
        launchCatching(
            displayToast
        ) {
            conversationsManagementService.deleteFullConversation(
                _currentConvMessState.value.conversationId
            )
            displayToast("Conversation deleted successfully")
            handleBackNavigation()
        }
    }
}