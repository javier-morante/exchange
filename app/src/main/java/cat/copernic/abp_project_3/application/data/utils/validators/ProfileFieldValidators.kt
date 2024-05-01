package cat.copernic.abp_project_3.application.data.utils.validators

import android.net.Uri
import android.util.Patterns
import cat.copernic.abp_project_3.application.data.exceptions.validators.ValidationException

/**
 * Utility class for validating profile-related fields such as name, surname, phone, email, description, passwords, and image URI.
 */
object ProfileFieldValidators {

    /**
     * Validates the name field of a profile.
     *
     * @param name The name to be validated.
     * @throws ValidationException if the name is empty or has an invalid length.
     */
    fun validateName(name: String) {
        if(name.isEmpty()) {
            throw ValidationException("Name is empty")
        }

        if(name.length <= 2 || name.length >= 20) {
            throw ValidationException("Invalid Name Length")
        }
    }

    /**
     * Validates the surname field of a profile.
     *
     * @param surname The surname to be validated.
     * @throws ValidationException if the surname is empty or has an invalid length.
     */
    fun validateSurname(surname: String) {
        if(surname.isEmpty()) {
            throw ValidationException("Surname is empty")
        }

        if(surname.length <= 2 || surname.length >= 20) {
            throw ValidationException("Invalid Surname Length")
        }
    }

    /**
     * Validates the phone number field of a profile.
     *
     * @param phone The phone number to be validated.
     * @throws ValidationException if the phone number is empty or has an invalid format.
     */
    fun validatePhone(phone: String) {
        if(phone.isEmpty()) {
            throw ValidationException("Phone is empty")
        }

        val phonePattern = "[0-9]{9}"

        if(!Regex(phonePattern).matches(phone)) {
            throw ValidationException("Invalid Phone format")
        }
    }

    /**
     * Validates the email field of a profile.
     *
     * @param email The email address to be validated.
     * @throws ValidationException if the email address is empty or has an invalid format.
     */
    fun validateEmail(email: String) {
        if(email.isEmpty()) {
            throw ValidationException("Email is empty")
        }

        if(!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            throw ValidationException("Invalid Email format")
        }
    }

    /**
     * Validates the description field of a profile.
     *
     * @param description The description to be validated.
     * @throws ValidationException if the description length exceeds the maximum allowed length.
     */
    fun validateDescription(description: String) {
        if(description.length > 150) {
            throw ValidationException("Description exceeded length")
        }
    }

    /**
     * Validates the passwords field of a profile.
     *
     * @param password The password to be validated.
     * @param repeatPassword The repeated password to be validated.
     * @throws ValidationException if either password is empty or the passwords do not match.
     */
    fun validatePasswords(password: String, repeatPassword: String) {
        if(password.length < 6) {
            throw ValidationException("Password is to short")
        }

        if(password.isEmpty() || repeatPassword.isEmpty()) {
            throw ValidationException("One password is empty")
        }

        if(password != repeatPassword) {
            throw ValidationException("Passwords are not equal")
        }
    }

    /**
     * Validates the image URI field of a profile.
     *
     * @param imageUri The image URI to be validated.
     * @throws ValidationException if the image URI is null (no image selected).
     */
    fun validateImageUri(imageUri: Uri?) {
        if(imageUri == null) {
            throw ValidationException("No image selected")
        }
    }

}