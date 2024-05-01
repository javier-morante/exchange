package cat.copernic.abp_project_3.application.data.model

import cat.copernic.abp_project_3.application.data.utils.validators.ReviewFieldValidators
import com.google.firebase.Timestamp
import com.google.firebase.firestore.DocumentId
import java.util.UUID

/**
 * Data class representing a review.
 *
 * @property id The ID of the review.
 * @property ownerRef The reference to the owner of the review.
 * @property stars The number of stars given in the review.
 * @property message The message of the review.
 * @property date The timestamp when the review was created.
 */
data class Review(
    @DocumentId
    var id: String = UUID.randomUUID().toString(),
    var ownerRef: String = "",
    var stars: Int = 0,
    var message: String = "",
    var date: Timestamp = Timestamp.now(),
) {

    /**
     * Sets the ID of the review.
     *
     * @param newId The new ID for the review.
     * @return The updated review object.
     */
    fun setId(newId: String): Review {
        return this.copy(id = newId)
    }

    /**
     * Sets the reference to the owner of the review.
     *
     * @param newOwnerRef The new owner reference for the review.
     * @return The updated review object.
     */
    fun setOwnerRef(newOwnerRef: String): Review {
        return this.copy(ownerRef = newOwnerRef)
    }

    /**
     * Sets the number of stars given in the review.
     *
     * @param newStars The new number of stars for the review.
     * @return The updated review object.
     */
    fun setStars(newStars: Int): Review {
        return this.copy(stars = newStars)
    }

    /**
     * Sets the message of the review.
     *
     * @param newMessage The new message for the review.
     * @return The updated review object.
     */
    fun setMessage(newMessage: String): Review {
        return this.copy(message = newMessage)
    }

    /**
     * Sets the timestamp when the review was created.
     *
     * @param newDate The new timestamp for the review.
     * @return The updated review object.
     */
    fun setDate(newDate: Timestamp): Review {
        return this.copy(date = newDate)
    }

    /**
     * Validates the review message.
     */
    fun validate() {
        ReviewFieldValidators.validateMessage(message)
    }

}