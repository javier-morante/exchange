package cat.copernic.abp_project_3.application.data.service.conversation

import cat.copernic.abp_project_3.application.data.model.Conversation
import kotlinx.coroutines.flow.Flow

/**
 * Interface that contains all the methods related to conversation
 */
interface ConversationService {

    val actualAuthUserConversationsList: Flow<List<Conversation>>

    /**
     * @param newConversation New conversation instance
     */
    suspend fun createConversation(newConversation: Conversation)

    /**
     * @param userRefs List containing all the references to the users that belong to
     * that conversation
     *
     * @return Return the conversation Id if the users belong to that conversation or null if
     * they dont belong to that conversation
     */
    suspend fun checkUsersChatExistence(userRefs: List<String>): String?

    /**
     * @param conversation Conversation Instance
     */
    suspend fun updateConversation(conversation: Conversation)

    /**
     * @param id Conversation Id
     */
    suspend fun deleteConversation(id: String)

}