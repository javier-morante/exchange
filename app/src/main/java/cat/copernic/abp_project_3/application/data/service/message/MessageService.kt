package cat.copernic.abp_project_3.application.data.service.message

import cat.copernic.abp_project_3.application.data.model.Message
import kotlinx.coroutines.flow.Flow

/**
 * Interface that contains all the methods related to messages
 */
interface MessageService {

    /**
     * @param conversationId Id of the conversation
     * @return Flow containing the list off actual messages
     */
    fun getActualConversationMessages(conversationId: String): Flow<List<Message>>

    /**
     * @param conversationId Id of the conversation
     * @param newMessage New message instance
     */
    suspend fun createConversationMessage(conversationId: String, newMessage: Message)

    /**
     * @param conversationId Id of the conversation
     * @return List of the conversation messages
     */
    suspend fun getConversationMessages(conversationId: String): List<Message>

    /**
     * @param conversationId Id of the conversation
     * @param message Message Instance
     */
    suspend fun updateConversationMessage(conversationId: String, message: Message)

    /**
     * @param conversationId If of the conversation
     * @param message Message Instance
     */
    suspend fun deleteConversationMessage(conversationId: String, message: Message)

}