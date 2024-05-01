package cat.copernic.abp_project_3.application.data.service.conversations_management

import cat.copernic.abp_project_3.application.data.exceptions.services.ConversationManagementException
import cat.copernic.abp_project_3.application.data.model.Conversation
import cat.copernic.abp_project_3.application.data.model.Message
import cat.copernic.abp_project_3.application.data.service.conversation.ConversationService
import cat.copernic.abp_project_3.application.data.service.message.MessageService
import javax.inject.Inject

/**
 * Service incharged of managing all actions related to conversations and and messages
 *
 * @property conversationService
 * @property messageService
 */
class ConversationsManagementServiceImpl @Inject constructor(
    private val conversationService: ConversationService,
    private val messageService: MessageService
): ConversationsManagementService {

    /**
     * Method that creates a new conversation if both of the users that belong to the conversation
     * don't have already a conversation
     *
     * @param newConversation
     * @param startMessage
     */
    override suspend fun createConversation(
        newConversation: Conversation,
        startMessage: Message
    ) {
        try {
            // Check if actually exists a conversation with both involved users
            val existsConversation = conversationService.checkUsersChatExistence(
                newConversation.usersRef
            )

            if(existsConversation == null) {
                // Create the conversation and send the starting message
                conversationService.createConversation(newConversation)
                messageService.createConversationMessage(
                    newConversation.id,
                    startMessage
                )

            } else {
                // If conversation already exists, generate the starting message
                messageService.createConversationMessage(
                    existsConversation,
                    startMessage
                )
            }

        } catch (e: Exception) {
            throw ConversationManagementException("Error creating conversation")

        }
    }

    /**
     * Method that deletes the full conversation and all the messages that contains
     *
     * @param conversationId
     */
    override suspend fun deleteFullConversation(
        conversationId: String
    ) {
        try {
            // Delete all the messages from the conversation
            for (message in messageService.getConversationMessages(conversationId)) {
                messageService.deleteConversationMessage(conversationId, message)
            }

            // Delete the conversation
            conversationService.deleteConversation(conversationId)

        } catch (e: Exception) {
            throw ConversationManagementException("Error deleting conversation")

        }
    }


}