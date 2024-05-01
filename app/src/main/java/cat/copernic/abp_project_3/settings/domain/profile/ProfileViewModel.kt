package cat.copernic.abp_project_3.settings.domain.profile

import android.net.Uri
import cat.copernic.abp_project_3.application.domain.ApplicationViewModel
import cat.copernic.abp_project_3.application.data.model.Profile
import cat.copernic.abp_project_3.application.data.service.account.AccountService
import cat.copernic.abp_project_3.application.data.service.authentication.AuthenticationService
import cat.copernic.abp_project_3.application.data.service.profile.ProfileService
import cat.copernic.abp_project_3.application.data.service.storage.StorageService
import cat.copernic.abp_project_3.settings.presentation.profile.ProfileState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import javax.inject.Inject

/**
 * Hilt ViewModel that contains all the logic referent to the profile screen,
 * it contains an attribute that stores the authenticated user profile data and also
 * the corresponding methods for handling all the actions referent to the user profile
 *
 * @property profileService Injected profile Service
 * @property storageService Injected storage Service
 * @property authenticationService Injected authentication Service
 * @property accountService Injected accounts Service
 */
@HiltViewModel
class ProfileViewModel @Inject constructor(
    private val profileService: ProfileService,
    private val storageService: StorageService,
    private val authenticationService: AuthenticationService,
    private val accountService: AccountService
): ApplicationViewModel() {

    // States ------------------------------

    private var _profileState = MutableStateFlow(ProfileState())
    val profileState get() = _profileState.asStateFlow()


    fun setEmail(newEmail: String) {
        _profileState.update { currentState ->
            currentState.setEmail(newEmail)
        }
    }

    fun setImageUri(newImageUri: Uri?) {
        _profileState.update { currentState ->
            currentState.setImageUri(newImageUri)
        }
    }

    fun setName(newName: String) {
        _profileState.update { currentState ->
            val userProfile = currentState.userProfile.setName(newName)
            currentState.setUserProfile(userProfile)
        }
    }

    fun setSurname(newSurname: String) {
        _profileState.update { currentState ->
            val userProfile = currentState.userProfile.setSurname(newSurname)
            currentState.setUserProfile(userProfile)
        }
    }

    fun setPhone(newPhone: String) {
        _profileState.update { currentState ->
            val userProfile = currentState.userProfile.setPhone(newPhone)
            currentState.setUserProfile(userProfile)
        }
    }

    fun setDescription(newDescription: String) {
        _profileState.update { currentState ->
            val userProfile = currentState.userProfile.setDescription(newDescription)
            currentState.setUserProfile(userProfile)
        }
    }

    init {
        collectAuthEmail()
        collectAuthProfile()
    }

    // Methods --------------------------------------------------

    private fun collectAuthEmail() {
        _profileState.update { currentState ->
            currentState.setEmail(authenticationService.currentAuthEmail ?: "")
        }
    }

    private fun collectAuthProfile() {
        launchCatching {
            profileService.actualAuthProfile.collect { actualAuthProfile ->
                _profileState.update { currentState ->
                    currentState.setUserProfile(actualAuthProfile ?: Profile())
                }
            }
        }
    }

    fun handleEmailRestore(displayToast: (String) -> Unit) {
        launchCatching(
            displayToast
        ) {
            _profileState.value.validateEmail()
            authenticationService.resetEmail(_profileState.value.email)
            displayToast("Restore email sent")
        }
    }

    fun handleDeleteAccount(
        displayToast: (String) -> Unit,
        handleBackNavigation: () -> Unit
    ) {
        launchCatching(
            displayToast
        ) {
            accountService.deleteAccount()
            displayToast("Account deleted successfully")
            handleBackNavigation()
        }
    }

    fun handleProfileUpdate(displayToast: (String) -> Unit) {
        launchCatching(
            displayToast
        ) {
            _profileState.value.validateProfile()
            accountService.updateAccount(
                _profileState.value.userProfile,
                _profileState.value.imageUri
            )
            displayToast("Profile successfully updated")
        }
    }
}