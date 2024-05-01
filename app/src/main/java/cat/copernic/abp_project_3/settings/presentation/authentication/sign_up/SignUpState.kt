package cat.copernic.abp_project_3.settings.presentation.authentication.sign_up

import android.net.Uri
import android.util.Patterns
import cat.copernic.abp_project_3.application.data.model.Profile
import cat.copernic.abp_project_3.application.data.utils.validators.ProfileFieldValidators

/**
 * Data class that contains all the state attributes referent to the Sign Up Screen
 * and also a method that validates the entire class data
 *
 * @property email
 * @property password
 * @property repeatPassword
 * @property accountImageUri
 * @property userProfile
 */
data class SignUpState(
    val email: String = "",
    val password: String = "",
    val repeatPassword: String = "",
    val accountImageUri: Uri? = null,
    val userProfile: Profile = Profile()
) {

    fun setEmail(newEmail: String): SignUpState {
        return this.copy(email = newEmail)
    }

    fun setPassword(newPassword: String): SignUpState {
        return this.copy(password = newPassword)
    }

    fun setRepeatPassword(newRepeatPassword: String): SignUpState {
        return this.copy(repeatPassword = newRepeatPassword)
    }

    fun setAccountImageUri(newAccountImageUri: Uri?): SignUpState {
        return this.copy(accountImageUri = newAccountImageUri)
    }

    fun setUserData(newUserData: Profile): SignUpState {
        return this.copy(userProfile = newUserData)
    }

    // Validators

    fun validateSignUpStepOne() {
        ProfileFieldValidators.validatePasswords(password, repeatPassword)
        ProfileFieldValidators.validateEmail(email)
    }

    fun validateSignUpStepTwo() {
        ProfileFieldValidators.validateImageUri(accountImageUri)
        userProfile.validate()
    }

    fun validateEmail(): Boolean {
        val pattern = Patterns.EMAIL_ADDRESS
        return pattern.matcher(this.email).matches()
    }

}
