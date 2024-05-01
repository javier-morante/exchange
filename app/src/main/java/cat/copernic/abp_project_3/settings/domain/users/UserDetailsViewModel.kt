package cat.copernic.abp_project_3.settings.domain.users

import android.net.Uri
import cat.copernic.abp_project_3.application.domain.ApplicationViewModel
import cat.copernic.abp_project_3.application.data.enums.UserRole
import cat.copernic.abp_project_3.application.data.model.Profile
import cat.copernic.abp_project_3.application.data.service.account.AccountService
import cat.copernic.abp_project_3.application.data.service.profile.ProfileService
import cat.copernic.abp_project_3.settings.presentation.users.user_details.ProfileDetailsState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import javax.inject.Inject

/**
 * Hilt ViewModel that contains all the logic referent to the user details
 * it contains a attribute with all the state referent to the user details screen
 * and the method that are uncharged of calling the correspondent services for saving
 * updating or gathering the user data
 *
 * @property profileService Injected profile Service
 * @property accountService Injected accounts Service
 */
@HiltViewModel
class UserDetailsViewModel @Inject constructor(
    private val profileService: ProfileService,
    private val accountService: AccountService
): ApplicationViewModel() {

    private var _userProfileDetailsState = MutableStateFlow(ProfileDetailsState())
    val userProfileCreationState get() = _userProfileDetailsState.asStateFlow()

    fun setName(newName: String) {
        _userProfileDetailsState.update { currentState ->
            val profileData = currentState.userProfile.setName(newName)
            currentState.setProfileData(profileData)
        }
    }

    fun setSurname(newSurname: String) {
        _userProfileDetailsState.update { currentState ->
            val profileData = currentState.userProfile.setSurname(newSurname)
            currentState.setProfileData(profileData)
        }
    }

    fun setPhone(newPhone: String) {
        _userProfileDetailsState.update { currentState ->
            val profileData = currentState.userProfile.setPhone(newPhone)
            currentState.setProfileData(profileData)
        }
    }

    fun setDescription(newDescription: String) {
        _userProfileDetailsState.update { currentState ->
            val profileData = currentState.userProfile.setDescription(newDescription)
            currentState.setProfileData(profileData)
        }
    }

    fun setRole(newRole: UserRole) {
        _userProfileDetailsState.update { currentState ->
            val profileData = currentState.userProfile.setRole(newRole)
            currentState.setProfileData(profileData)
        }
    }

    fun setProfileImageUri(newProfileImageUri: Uri?) {
        _userProfileDetailsState.update { currentState ->
            currentState.setProfileImageUri(newProfileImageUri)
        }
    }

    fun setIsEnabled(status: Boolean) {
        _userProfileDetailsState.update { currentState ->
            val profileData = currentState.userProfile.setIsEnable(status)
            currentState.setProfileData(profileData)
        }
    }

    fun getProfileData(userProfileId: String) {
        launchCatching {
            _userProfileDetailsState.update { currentState ->
                currentState.setProfileData(
                    profileService.getProfile(userProfileId) ?: Profile()
                )
            }
        }
    }

    fun handleUpdateProfile(
        displayToast: (String) -> Unit,
        handleBackNavigation: () -> Unit
    ) {
        launchCatching(
            displayToast
        ) {
            _userProfileDetailsState.value.validate()
            accountService.updateAccount(
                _userProfileDetailsState.value.userProfile,
                _userProfileDetailsState.value.profileImageUri
            )
            displayToast("Profile Updated successfully")
            handleBackNavigation()
        }
    }

}