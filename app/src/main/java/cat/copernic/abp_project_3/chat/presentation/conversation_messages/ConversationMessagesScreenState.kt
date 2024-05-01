package cat.copernic.abp_project_3.chat.presentation.conversation_messages

import cat.copernic.abp_project_3.application.data.model.Message
import cat.copernic.abp_project_3.application.data.model.Profile

/**
 * Represents the state of the conversation messages screen.
 *
 * @param currentAuthUserUid The ID of the current authenticated user.
 * @param conversationId The ID of the conversation being displayed.
 * @param userProfile The profile associated with the conversation.
 * @param sendMessageFrameValue The value of the message composition frame.
 * @param selectedMessage The currently selected message, if any.
 */
data class ConversationMessagesScreenState(
    var currentAuthUserUid: String = "",
    var conversationId: String = "",
    val userProfile: Profile = Profile(),
    var sendMessageFrameValue: String = "",
    var selectedMessage: Message? = null
) {

    /**
     * Updates the current authenticated user ID.
     *
     * @param newAuthUserUid The new authenticated user ID.
     * @return Updated state with the new authenticated user ID.
     */
    fun setCurrentAuthUserUid(newAuthUserUid: String): ConversationMessagesScreenState {
        return this.copy(currentAuthUserUid = newAuthUserUid)
    }

    /**
     * Updates the conversation ID.
     *
     * @param newConversationId The new conversation ID.
     * @return Updated state with the new conversation ID.
     */
    fun setConversationId(newConversationId: String): ConversationMessagesScreenState {
        return this.copy(conversationId = newConversationId)
    }

    /**
     * Updates the user profile associated with the conversation.
     *
     * @param newUserProfile The new user profile.
     * @return Updated state with the new user profile.
     */
    fun setProfile(newUserProfile: Profile): ConversationMessagesScreenState {
        return this.copy(userProfile = newUserProfile)
    }

    /**
     * Updates the value of the message composition frame.
     *
     * @param newSendMessageFrameValue The new value of the message composition frame.
     * @return Updated state with the new message composition frame value.
     */
    fun setSendMessageFrameValue(newSendMessageFrameValue: String): ConversationMessagesScreenState {
        return this.copy(sendMessageFrameValue = newSendMessageFrameValue)
    }

    /**
     * Updates the currently selected message.
     *
     * @param newSelectedMessage The new selected message, or null if no message is selected.
     * @return Updated state with the new selected message.
     */
    fun setSelectedMessage(newSelectedMessage: Message?): ConversationMessagesScreenState {
        return this.copy(selectedMessage = newSelectedMessage)
    }
}

