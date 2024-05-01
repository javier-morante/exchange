package cat.copernic.abp_project_3.application.data.model

import cat.copernic.abp_project_3.application.data.utils.validators.MessageFieldValidators
import com.google.firebase.Timestamp
import com.google.firebase.firestore.DocumentId
import java.util.UUID

/**
 * Data class representing a message.
 *
 * @property id The ID of the message.
 * @property orderId The order ID associated with the message.
 * @property ownerRef The reference to the owner of the message.
 * @property date The timestamp of when the message was sent.
 * @property content The content of the message.
 * @property isEnabled Indicates whether the message is enabled or not.
 */
data class Message(
    @DocumentId
    var id: String = UUID.randomUUID().toString(),
    var orderId: Int = 0,
    var ownerRef: String = "",
    var date: Timestamp = Timestamp.now(),
    var content: String = "",
    var isEnabled: Boolean = true
) {

    /**
     * Sets the ID of the message.
     *
     * @param newId The new ID for the message.
     * @return The updated message object.
     */
    fun setId(newId: String): Message {
        return this.copy(id = newId)
    }

    /**
     * Sets the order ID associated with the message.
     *
     * @param newOrderId The new order ID for the message.
     * @return The updated message object.
     */
    fun setOrderId(newOrderId: Int): Message {
        return this.copy(orderId = newOrderId)
    }

    /**
     * Sets the reference to the owner of the message.
     *
     * @param newOwnerRef The new owner reference for the message.
     * @return The updated message object.
     */
    fun setOwnerRef(newOwnerRef: String): Message {
        return this.copy(ownerRef = newOwnerRef)
    }

    /**
     * Sets the timestamp of when the message was sent.
     *
     * @param newDate The new timestamp for the message.
     * @return The updated message object.
     */
    fun setDate(newDate: Timestamp): Message {
        return this.copy(date = newDate)
    }

    /**
     * Sets the content of the message.
     *
     * @param newContent The new content for the message.
     * @return The updated message object.
     */
    fun setContent(newContent: String): Message {
        return this.copy(content = newContent)
    }

    /**
     * Sets whether the message is enabled or not.
     *
     * @param newIsEnabled The new value indicating whether the message is enabled or not.
     * @return The updated message object.
     */
    fun setIsEnabled(newIsEnabled: Boolean): Message {
        return this.copy(isEnabled = newIsEnabled)
    }

    /**
     * Validates the message content.
     */
    fun validate() {
        MessageFieldValidators.validateContent(content)
    }

}