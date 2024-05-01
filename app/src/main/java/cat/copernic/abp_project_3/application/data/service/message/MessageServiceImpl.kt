package cat.copernic.abp_project_3.application.data.service.message

import android.util.Log
import cat.copernic.abp_project_3.application.data.exceptions.services.MessageException
import cat.copernic.abp_project_3.application.data.model.Message
import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.Query
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.tasks.await
import javax.inject.Inject
import javax.inject.Named

/**
 * Service uncharged on managing all the actions related to the messages
 *
 * @property conversationCollection
 */
class MessageServiceImpl @Inject constructor(
    @Named("conversationCollection") private val conversationCollection: CollectionReference
): MessageService {

    /**
     * Method that returns all the conversation messages based on the provided conversation Id
     *
     * @param conversationId
     * @return
     */
    override fun getActualConversationMessages(
        conversationId: String
    ): Flow<List<Message>> = callbackFlow {
        val listener = conversationCollection
            .document(conversationId)
            .collection(MESSAGE_COLLECTION_NAME)
            .orderBy("orderId", Query.Direction.DESCENDING)
            .addSnapshotListener { snapshot, error ->
                if(error != null) {
                    return@addSnapshotListener
                }

                if(snapshot != null) {
                    Log.d(
                        "message:actual_conversation_messages",
                        "-----> Fetching conversation messages <-----"
                    )

                    val messages = snapshot.documents.map { document ->
                        document.toObject(Message::class.java) ?: Message()
                    }

                    trySend(messages)
                }
            }

        awaitClose { listener.remove() }
    }

    /**
     * Method that creates a new conversation message based in the provided conversationId
     *
     * @param conversationId
     * @param newMessage
     */
    override suspend fun createConversationMessage(conversationId: String, newMessage: Message) {
        conversationCollection
            .document(conversationId)
            .collection(MESSAGE_COLLECTION_NAME)
            .document(newMessage.id)
            .set(newMessage)
            .addOnFailureListener {
                throw MessageException("Error creating conversation message")
            }.await()
    }

    /**
     * Method that returns a list that contains all the conversation messages based
     * in the provided conversationId
     *
     * @param conversationId
     * @return
     */
    override suspend fun getConversationMessages(conversationId: String): List<Message> {
        return conversationCollection
            .document(conversationId)
            .collection(MESSAGE_COLLECTION_NAME)
            .get()
            .addOnFailureListener {
                throw MessageException("Error gatthering conversation messages")
            }.await().toObjects(Message::class.java)
    }

    /**
     * Method that updates an existing conversation message based in the provided conversationId
     *
     * @param conversationId
     * @param message
     */
    override suspend fun updateConversationMessage(conversationId: String, message: Message) {
        conversationCollection
            .document(conversationId)
            .collection(MESSAGE_COLLECTION_NAME)
            .document(message.id)
            .set(message)
            .addOnFailureListener {
                throw MessageException("Error updating conversation message")
            }.await()
    }

    /**
     * Method that deletes an existing conversation message based in the provided conversationId
     *
     * @param conversationId
     * @param message
     */
    override suspend fun deleteConversationMessage(conversationId: String, message: Message) {
        conversationCollection
            .document(conversationId)
            .collection(MESSAGE_COLLECTION_NAME)
            .document(message.id)
            .delete()
            .addOnFailureListener {
                throw MessageException("Error deleting conversation message")
            }.await()
    }

    companion object {
        const val MESSAGE_COLLECTION_NAME = "messages"
    }

}