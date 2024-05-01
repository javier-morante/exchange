package cat.copernic.abp_project_3.application.data.service.conversations_management

import cat.copernic.abp_project_3.application.data.model.Conversation
import cat.copernic.abp_project_3.application.data.model.Message

/**
 * Interface that contains all the methods related to conversation management
 */
interface ConversationsManagementService {

    /**
     * @param newConversation New conversation instance
     * @param startMessage Fist conversation message
     */
    suspend fun createConversation(
        newConversation: Conversation,
        startMessage: Message
    )

    /**
     * @param conversationId Id of the conversation
     */
    suspend fun deleteFullConversation(
        conversationId: String
    )

}