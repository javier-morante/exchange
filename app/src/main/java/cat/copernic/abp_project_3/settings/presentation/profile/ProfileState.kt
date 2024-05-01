package cat.copernic.abp_project_3.settings.presentation.profile

import android.net.Uri
import cat.copernic.abp_project_3.application.data.model.Profile
import cat.copernic.abp_project_3.application.data.utils.validators.ProfileFieldValidators

/**
 * Data class that models the state attributes for the profile state screen
 *
 * @property email
 * @property imageUri
 * @property userProfile
 */
data class ProfileState(
    var email: String = "",
    var imageUri: Uri? = null,
    var userProfile: Profile = Profile()
) {

    fun setEmail(newEmail: String): ProfileState {
        return this.copy(email = newEmail)
    }

    fun setImageUri(newImageUri: Uri?): ProfileState {
        return this.copy(imageUri = newImageUri)
    }

    fun setUserProfile(newUserProfile: Profile): ProfileState {
        return this.copy(userProfile = newUserProfile)
    }

    fun validateEmail() {
        ProfileFieldValidators.validateEmail(email)
    }

    fun validateProfile() {
        userProfile.validate()
    }

}

