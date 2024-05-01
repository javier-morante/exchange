package cat.copernic.abp_project_3.application.data.model

import cat.copernic.abp_project_3.application.data.utils.validators.CategoryFieldValidators
import com.google.firebase.firestore.DocumentId
import com.google.firebase.firestore.Exclude
import java.util.UUID

/**
 * Data class representing a category.
 *
 * @property id The ID of the category.
 * @property title The title of the category.
 * @property description The description of the category.
 * @property imageUrl The URL of the image associated with the category.
 */
data class Category(
    @DocumentId
    var id: String = UUID.randomUUID().toString(),
    var title: String = "",
    var description: String = "",
    var imageUrl: String = ""
){

    /**
     * Sets the title of the category.
     *
     * @param newTitle The new title for the category.
     * @return The updated category object.
     */
    fun setTitle(newTitle: String): Category {
        return this.copy(title = newTitle)
    }

    /**
     * Sets the description of the category.
     *
     * @param newDescription The new description for the category.
     * @return The updated category object.
     */
    fun setDescription(newDescription: String): Category {
        return this.copy(description = newDescription)
    }

    /**
     * Sets the image URL of the category.
     *
     * @param newImageUrl The new image URL for the category.
     * @return The updated category object.
     */
    fun setImageUrl(newImageUrl: String): Category {
        return this.copy(imageUrl = newImageUrl)
    }

    /**
     * Validates the category fields.
     */
    fun validate() {
        CategoryFieldValidators.validateTitle(title)
        CategoryFieldValidators.validateDescription(description)
    }

}