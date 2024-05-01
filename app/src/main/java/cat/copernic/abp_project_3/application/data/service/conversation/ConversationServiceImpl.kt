package cat.copernic.abp_project_3.application.data.service.conversation

import android.util.Log
import cat.copernic.abp_project_3.application.data.exceptions.services.ConversationException
import cat.copernic.abp_project_3.application.data.model.Conversation
import cat.copernic.abp_project_3.application.data.service.authentication.AuthenticationService
import cat.copernic.abp_project_3.application.data.service.profile.ProfileService
import com.google.firebase.firestore.CollectionReference
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.tasks.await
import javax.inject.Inject
import javax.inject.Named

/**
 * Service uncharged in managing all the actions referent to conversations
 *
 * @property conversationCollection
 * @property authenticationService
 * @property profileService
 */
class ConversationServiceImpl @Inject constructor(
    @Named("conversationCollection") private val conversationCollection: CollectionReference,
    private val authenticationService: AuthenticationService,
    private val profileService: ProfileService
): ConversationService {

    /**
     * Flow that returns a list with all the conversations from the database
     */
    override val actualAuthUserConversationsList: Flow<List<Conversation>>
        get() = callbackFlow {
            val listener = conversationCollection
                .whereArrayContains("usersRef", authenticationService.currentAuthUserUid!!)
                .addSnapshotListener { snapshot, error ->
                    if(error != null) {
                        return@addSnapshotListener
                    }

                    if(snapshot != null) {
                        Log.d(
                            "conversation:actual_auth_user_conversations",
                            "-----> Fetching actual auth user conversations <-----"
                        )

                        val conversations = snapshot.documents.map { document ->
                            document.toObject(Conversation::class.java) ?: Conversation()
                        }

                        trySend(conversations)
                    }
                }

            awaitClose { listener.remove() }
        }

    /**
     * Method uncharged on creating a new conversation
     *
     * @param newConversation
     */
    override suspend fun createConversation(newConversation: Conversation) {
        conversationCollection
            .document(newConversation.id)
            .set(newConversation)
            .addOnFailureListener {
                throw ConversationException("Error creating conversation")
            }.await()
    }

    /**
     * Method that checks if a user list exists on the conversation
     *
     * @param userRefs
     * @return
     */
    override suspend fun checkUsersChatExistence(userRefs: List<String>): String? {
        val conversations = conversationCollection
            .whereArrayContains("usersRef", authenticationService.currentAuthUserUid!!)
            .get()
            .addOnFailureListener {
                throw ConversationException("Error gathering conversations")
            }.await().toObjects(Conversation::class.java)

        for(conversation in conversations) {
            val userOne = conversation.usersRef.contains(userRefs[0])
            val userTwo = conversation.usersRef.contains(userRefs[1])

            if(userOne and userTwo) {
                return conversation.id
            }
        }

        return null
    }

    /**
     * Method that updates an existing conversation from the database
     *
     * @param conversation
     */
    override suspend fun updateConversation(conversation: Conversation) {
        conversationCollection
            .document(conversation.id)
            .set(conversation)
            .addOnFailureListener {
                throw ConversationException("Error deleting conversation")
            }.await()
    }

    /**
     * Method that deletes a conversation from the database
     *
     * @param id
     */
    override suspend fun deleteConversation(id: String) {
        conversationCollection
            .document(id)
            .delete()
            .addOnFailureListener {
                throw ConversationException("Error deleting conversation")
            }.await()
    }

}