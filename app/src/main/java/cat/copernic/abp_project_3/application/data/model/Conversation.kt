package cat.copernic.abp_project_3.application.data.model

import com.google.firebase.firestore.DocumentId
import com.google.firebase.firestore.Exclude
import java.util.UUID

/**
 * Data class representing a conversation.
 *
 * @property id The ID of the conversation.
 * @property usersRef The references to the users involved in the conversation.
 * @property userProfile The profile associated with the conversation.
 */
data class Conversation(
    @DocumentId
    var id: String = UUID.randomUUID().toString(),
    var usersRef: List<String> = emptyList(),
    @Exclude @get:Exclude @set:Exclude
    var userProfile: Profile = Profile()
) {

    /**
     * Sets the ID of the conversation.
     *
     * @param newId The new ID for the conversation.
     * @return The updated conversation object.
     */
    fun setId(newId: String): Conversation {
        return this.copy(id = newId)
    }

    /**
     * Sets the references to the users involved in the conversation.
     *
     * @param newUsersRef The new list of user references for the conversation.
     * @return The updated conversation object.
     */
    fun setUsersRef(newUsersRef: List<String>): Conversation {
        return this.copy(usersRef = newUsersRef)
    }

    /**
     * Sets the profile associated with the conversation.
     *
     * @param newUserProfile The new profile associated with the conversation.
     * @return The updated conversation object.
     */
    fun setUserProfile(newUserProfile: Profile): Conversation {
        return this.copy(userProfile = newUserProfile)
    }

}