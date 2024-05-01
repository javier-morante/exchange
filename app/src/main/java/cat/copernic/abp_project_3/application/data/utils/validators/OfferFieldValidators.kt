package cat.copernic.abp_project_3.application.data.utils.validators

import cat.copernic.abp_project_3.application.data.exceptions.validators.ValidationException

/**
 * Utility class for validating offer-related fields such as desired item, provided item, and description.
 */
object OfferFieldValidators {

    /**
     * Validates the desired item field of an offer.
     *
     * @param desiredItem The desired item to be validated.
     * @throws ValidationException if the desired item is empty or exceeds the maximum allowed length.
     */
    fun validateDesiredItem(desiredItem: String) {
        if(desiredItem.isEmpty()) {
            throw ValidationException("Desired item is empty")
        }

        if(desiredItem.length > 100) {
            throw ValidationException("Invalid Desired Item length")
        }
    }

    /**
     * Validates the provided item field of an offer.
     *
     * @param providedItem The provided item to be validated.
     * @throws ValidationException if the provided item is empty or exceeds the maximum allowed length.
     */
    fun validateProvidedItem(providedItem: String) {
        if(providedItem.isEmpty()) {
            throw ValidationException("Provided item is empty")
        }

        if(providedItem.length > 100) {
            throw ValidationException("Invalid Provided Item length")
        }
    }

    /**
     * Validates the description field of an offer.
     *
     * @param description The description to be validated.
     * @throws ValidationException if the description is empty or exceeds the maximum allowed length.
     */
    fun validateDescription(description: String) {
        if(description.isEmpty()) {
            throw ValidationException("Description is empty")
        }

        if(description.length > 1000) {
            throw ValidationException("Invalid Description Length")
        }
    }


}