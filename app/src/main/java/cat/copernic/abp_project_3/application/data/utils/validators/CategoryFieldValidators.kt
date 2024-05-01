package cat.copernic.abp_project_3.application.data.utils.validators

import cat.copernic.abp_project_3.application.data.exceptions.validators.ValidationException

/**
 * Utility class for validating category fields such as title and description.
 */
object CategoryFieldValidators {

    /**
     * Validates the title of a category.
     *
     * @param title The title to be validated.
     * @throws ValidationException if the title is empty or has an invalid length.
     */
    fun validateTitle(title: String) {
        if(title.isEmpty()) {
            throw ValidationException("Title is empty")
        }

        if(title.length <= 2 || title.length >= 100) {
            throw ValidationException("Invalid title length")
        }
    }

    /**
     * Validates the description of a category.
     *
     * @param description The description to be validated.
     * @throws ValidationException if the description is empty or exceeds the maximum allowed length.
     */
    fun validateDescription(description: String) {
        if(description.isEmpty()) {
            throw ValidationException("Description is empty")
        }

        if(description.length > 1000) {
            throw ValidationException("Description exceeded length")
        }
    }

}