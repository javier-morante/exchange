package cat.copernic.abp_project_3.settings.presentation.offers.offer_details

import android.net.Uri
import cat.copernic.abp_project_3.application.data.model.Category
import cat.copernic.abp_project_3.application.data.model.Offer

/**
 * Data class that contains all the state attributes referent to the Offer Details Screen
 * and also a method that validates the entire class data
 *
 * @property offer
 * @property offerImageUri
 * @property selectedCategory
 * @property categories
 */
data class OfferDetailsState(
    var offer: Offer = Offer(),
    var offerImageUri: Uri? = null,
    var selectedCategory: Category = Category(),
    var categories: List<Category> = emptyList()
) {

    fun setOfferData(newOfferData: Offer): OfferDetailsState {
        return this.copy(offer = newOfferData)
    }

    fun setOfferImageUri(newOfferImageUri: Uri?): OfferDetailsState {
        return this.copy(offerImageUri = newOfferImageUri)
    }

    fun setSelectedCategory(newSelectedCategory: Category): OfferDetailsState {
        return this.copy(selectedCategory = newSelectedCategory)
    }

    fun setCategories(newCategories: List<Category>): OfferDetailsState {
        return this.copy(categories = newCategories)
    }

    fun validate() {
        offer.validate()
    }
}