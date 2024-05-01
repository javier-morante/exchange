package cat.copernic.abp_project_3.application.data.utils.validators

import cat.copernic.abp_project_3.application.data.exceptions.validators.ValidationException

/**
 * Utility class for validating review-related fields such as message.
 */
object ReviewFieldValidators {

    /**
     * Validates the message field of a review.
     *
     * @param message The message to be validated.
     * @throws ValidationException if the message is empty or exceeds the maximum allowed length.
     */
    fun validateMessage(message: String) {
        if(message.isEmpty()) {
            throw ValidationException("Message is empty")
        }

        if(message.length > 150) {
            throw ValidationException("Invalid Message Length")
        }
    }

}