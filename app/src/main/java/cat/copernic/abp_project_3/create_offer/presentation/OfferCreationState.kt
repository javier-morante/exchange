package cat.copernic.abp_project_3.create_offer.presentation

import android.net.Uri
import cat.copernic.abp_project_3.application.data.exceptions.validators.ValidationException
import cat.copernic.abp_project_3.application.data.model.Category
import cat.copernic.abp_project_3.application.data.model.Offer

/**
 * Represents the state of the offer creation screen.
 *
 * @property offer The offer being created or edited.
 * @property offerImageUri The URI of the selected image for the offer.
 * @property selectedCategory The category selected for the offer.
 * @property categories The list of available categories to choose from.
 * @property authUserStatus The authentication user status indicating whether the user is signed in or not.
 */
data class OfferCreationState(
    var offer: Offer = Offer(),
    var offerImageUri: Uri? = null,
    var selectedCategory: Category = Category(),
    var categories: List<Category> = emptyList(),
    val authUserStatus: Boolean = false
) {

    /**
     * Updates the offer data in the current state.
     *
     * @param newOfferData The new offer data to set.
     * @return A new [OfferCreationState] instance with updated offer data.
     */
    fun setOfferData(newOfferData: Offer): OfferCreationState {
        return this.copy(offer = newOfferData)
    }

    /**
     * Updates the offer image URI in the current state.
     *
     * @param newOfferImageUri The new offer image URI to set.
     * @return A new [OfferCreationState] instance with updated offer image URI.
     */
    fun setOfferImageUri(newOfferImageUri: Uri?): OfferCreationState {
        return this.copy(offerImageUri = newOfferImageUri)
    }

    /**
     * Updates the selected category in the current state.
     *
     * @param newSelectedCategory The new selected category to set.
     * @return A new [OfferCreationState] instance with updated selected category.
     */
    fun setSelectedCategory(newSelectedCategory: Category): OfferCreationState {
        return this.copy(selectedCategory = newSelectedCategory)
    }

    /**
     * Updates the list of available categories in the current state.
     *
     * @param newCategories The new list of categories to set.
     * @return A new [OfferCreationState] instance with updated list of categories.
     */
    fun setCategories(newCategories: List<Category>): OfferCreationState {
        return this.copy(categories = newCategories)
    }

    /**
     * Updates the authentication user status in the current state.
     *
     * @param newAuthUserStatus The new authentication user status to set.
     * @return A new [OfferCreationState] instance with updated authentication user status.
     */
    fun setAuthUserStatus(newAuthUserStatus: Boolean): OfferCreationState {
        return this.copy(authUserStatus = newAuthUserStatus)
    }

    /**
     * Validates the offer creation state.
     *
     * This method checks if the necessary fields (e.g., image) are set before creating the offer.
     * If validation fails, it throws a [ValidationException] with an appropriate message.
     *
     * @throws ValidationException If validation fails due to missing required fields.
     */
    fun validate() {
        if (offerImageUri == null) {
            throw ValidationException("Select an image for the offer")
        }

        // Validate the offer details
        offer.validate()
    }
}
