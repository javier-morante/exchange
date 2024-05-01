package cat.copernic.abp_project_3.application.data.model

import cat.copernic.abp_project_3.application.data.enums.OfferState
import cat.copernic.abp_project_3.application.data.utils.validators.OfferFieldValidators
import com.google.firebase.Timestamp
import com.google.firebase.firestore.DocumentId
import com.google.firebase.firestore.GeoPoint
import java.util.UUID

/**
 * Data class representing an offer.
 *
 * @property id The ID of the offer.
 * @property ownerRef The reference to the owner of the offer.
 * @property myItem The item offered by the owner.
 * @property desiredItem The item desired by the owner.
 * @property description The description of the offer.
 * @property date The timestamp when the offer was created.
 * @property imageUrl The URL of the image associated with the offer.
 * @property categoryRef The reference to the category associated with the offer.
 * @property offerState The state of the offer.
 * @property totalStars The total stars received by the offer.
 * @property totalReviews The total reviews received by the offer.
 * @property averageStars The average stars received by the offer.
 * @property location The location associated with the offer.
 */
data class Offer(
    @DocumentId
    var id: String = UUID.randomUUID().toString(),
    var ownerRef: String = "",
    var myItem: String = "",
    var desiredItem: String = "",
    var description: String = "",
    var date: Timestamp = Timestamp.now(),
    var imageUrl: String = "",
    var categoryRef: String = "",
    var offerState: OfferState = OfferState.AVAILABLE,
    var totalStars: Int = 0,
    var totalReviews: Int = 0,
    var averageStars: Double = 0.0,
    var location: GeoPoint = GeoPoint(0.0, 0.0)
) {

    /**
     * Sets the ID of the offer.
     *
     * @param newId The new ID for the offer.
     * @return The updated offer object.
     */
    fun setId(newId: String): Offer {
        return this.copy(id = newId)
    }

    /**
     * Sets the reference to the owner of the offer.
     *
     * @param newOwnerRef The new owner reference for the offer.
     * @return The updated offer object.
     */
    fun setOwnerRef(newOwnerRef: String): Offer {
        return this.copy(ownerRef = newOwnerRef)
    }

    /**
     * Sets the item offered by the owner.
     *
     * @param newMyItem The new item offered by the owner.
     * @return The updated offer object.
     */
    fun setMyItem(newMyItem: String): Offer {
        return this.copy(myItem = newMyItem)
    }

    /**
     * Sets the item desired by the owner.
     *
     * @param newDesiredItem The new item desired by the owner.
     * @return The updated offer object.
     */
    fun setDesiredItem(newDesiredItem: String): Offer {
        return this.copy(desiredItem = newDesiredItem)
    }

    /**
     * Sets the description of the offer.
     *
     * @param newDescription The new description for the offer.
     * @return The updated offer object.
     */
    fun setDescription(newDescription: String): Offer {
        return this.copy(description = newDescription)
    }

    /**
     * Sets the timestamp when the offer was created.
     *
     * @param newDate The new timestamp for the offer.
     * @return The updated offer object.
     */
    fun setDate(newDate: Timestamp): Offer {
        return this.copy(date = newDate)
    }

    /**
     * Sets the URL of the image associated with the offer.
     *
     * @param newImageUrl The new image URL for the offer.
     * @return The updated offer object.
     */
    fun setImageUrl(newImageUrl: String): Offer {
        return this.copy(imageUrl = newImageUrl)
    }

    /**
     * Sets the reference to the category associated with the offer.
     *
     * @param newCategoryRef The new category reference for the offer.
     * @return The updated offer object.
     */
    fun setCategoryRef(newCategoryRef: String): Offer {
        return this.copy(categoryRef = newCategoryRef)
    }

    /**
     * Sets the state of the offer.
     *
     * @param newOfferState The new state for the offer.
     * @return The updated offer object.
     */
    fun setOfferState(newOfferState: OfferState): Offer {
        return this.copy(offerState = newOfferState)
    }

    /**
     * Sets the total stars received by the offer.
     *
     * @param newTotalStars The new total stars for the offer.
     * @return The updated offer object.
     */
    fun setTotalStars(newTotalStars: Int): Offer {
        return this.copy(totalStars = newTotalStars)
    }

    /**
     * Sets the total reviews received by the offer.
     *
     * @param newTotalReviews The new total reviews for the offer.
     * @return The updated offer object.
     */
    fun setTotalReviews(newTotalReviews: Int): Offer {
        return this.copy(totalReviews = newTotalReviews)
    }

    /**
     * Sets the average stars received by the offer.
     *
     * @param newAverageStars The new average stars for the offer.
     * @return The updated offer object.
     */
    fun setAverageStars(newAverageStars: Double): Offer {
        return this.copy(averageStars = newAverageStars)
    }

    /**
     * Sets the location associated with the offer.
     *
     * @param newLocation The new location for the offer.
     * @return The updated offer object.
     */
    fun setLocation(newLocation: GeoPoint): Offer {
        return this.copy(location = newLocation)
    }

    /**
     * Validates the offer fields.
     */
    fun validate() {
        OfferFieldValidators.validateDesiredItem(myItem)
        OfferFieldValidators.validateProvidedItem(desiredItem)
        OfferFieldValidators.validateDescription(description)
    }

}