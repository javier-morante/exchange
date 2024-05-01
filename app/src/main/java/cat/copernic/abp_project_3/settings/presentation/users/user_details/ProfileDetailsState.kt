package cat.copernic.abp_project_3.settings.presentation.users.user_details

import android.net.Uri
import cat.copernic.abp_project_3.application.data.model.Profile

/**
 * Data class that models the state attributes for the user details component
 * and also a method that validates the entire class data
 *
 * @property userProfile
 * @property profileImageUri
 */
data class ProfileDetailsState(
    var userProfile: Profile = Profile(),
    var profileImageUri: Uri? = null
) {

    fun setProfileData(newUserProfileData: Profile): ProfileDetailsState {
        return this.copy(userProfile = newUserProfileData)
    }

    fun setProfileImageUri(newProfileImageUri: Uri?): ProfileDetailsState {
        return this.copy(profileImageUri = newProfileImageUri)
    }

    fun validate() {
        userProfile.validate()
    }

}