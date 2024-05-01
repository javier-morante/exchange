package cat.copernic.abp_project_3.application.data.model

import cat.copernic.abp_project_3.application.data.enums.UserRole
import cat.copernic.abp_project_3.application.data.utils.validators.ProfileFieldValidators
import com.google.firebase.firestore.DocumentId

/**
 * Data class representing a user profile.
 *
 * @property id The ID of the profile.
 * @property name The name of the user.
 * @property surname The surname of the user.
 * @property phone The phone number of the user.
 * @property description The description of the user.
 * @property imageUrl The URL of the image associated with the user.
 * @property role The role of the user.
 * @property isEnabled Indicates whether the user profile is enabled or not.
 */
data class Profile(
    @DocumentId
    var id: String = "",
    var name: String = "",
    var surname: String = "",
    var phone: String = "",
    var description: String = "",
    var imageUrl: String = "",
    var role: UserRole = UserRole.USER,
    var isEnabled: Boolean = true
) {

    /**
     * Sets the ID of the profile.
     *
     * @param newId The new ID for the profile.
     * @return The updated profile object.
     */
    fun setId(newId: String): Profile {
        return this.copy(id = newId)
    }

    /**
     * Sets the name of the user.
     *
     * @param newName The new name for the user.
     * @return The updated profile object.
     */
    fun setName(newName: String): Profile {
        return this.copy(name = newName)
    }

    /**
     * Sets the surname of the user.
     *
     * @param newSurname The new surname for the user.
     * @return The updated profile object.
     */
    fun setSurname(newSurname: String): Profile {
        return this.copy(surname = newSurname)
    }

    /**
     * Sets the phone number of the user.
     *
     * @param newPhone The new phone number for the user.
     * @return The updated profile object.
     */
    fun setPhone(newPhone: String): Profile {
        return this.copy(phone = newPhone)
    }

    /**
     * Sets the description of the user.
     *
     * @param newDescription The new description for the user.
     * @return The updated profile object.
     */
    fun setDescription(newDescription: String): Profile {
        return this.copy(description = newDescription)
    }

    /**
     * Sets the URL of the image associated with the user.
     *
     * @param newImageUrl The new image URL for the user.
     * @return The updated profile object.
     */
    fun setImageUrl(newImageUrl: String): Profile {
        return this.copy(imageUrl = newImageUrl)
    }

    /**
     * Sets the role of the user.
     *
     * @param newRole The new role for the user.
     * @return The updated profile object.
     */
    fun setRole(newRole: UserRole): Profile {
        return this.copy(role = newRole)
    }

    /**
     * Sets whether the user profile is enabled or not.
     *
     * @param newIsEnabled The new value indicating whether the user profile is enabled or not.
     * @return The updated profile object.
     */
    fun setIsEnable(newIsEnabled: Boolean): Profile {
        return this.copy(isEnabled = newIsEnabled)
    }

    /**
     * Validates the profile fields.
     */
    fun validate() {
        ProfileFieldValidators.validateName(name)
        ProfileFieldValidators.validateSurname(surname)
        ProfileFieldValidators.validatePhone(phone)
        ProfileFieldValidators.validateDescription(description)
    }

}
