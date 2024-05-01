package cat.copernic.abp_project_3.application.data.utils.validators

import cat.copernic.abp_project_3.application.data.exceptions.validators.ValidationException

/**
 * Utility class for validating message content.
 */
object MessageFieldValidators {

    /**
     * Validates the content of a message.
     *
     * @param content The content to be validated.
     * @throws ValidationException if the content is empty or exceeds the maximum allowed length.
     */
    fun validateContent(content: String) {
        if(content.isEmpty()) {
            throw ValidationException("Message is empty")
        }

        if(content.length > 300) {
            throw ValidationException("Invalid Message length")
        }
    }

}