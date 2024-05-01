package cat.copernic.abp_project_3.settings.domain.menu

import cat.copernic.abp_project_3.application.domain.ApplicationViewModel
import cat.copernic.abp_project_3.application.data.model.Profile
import cat.copernic.abp_project_3.application.data.service.authentication.AuthenticationService
import cat.copernic.abp_project_3.application.data.service.profile.ProfileService
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject

/**
 * Hilt ViewModel that contains all the logic referent to the Settings Menu Screen also contains
 * methods for handling all the actions in the menu, like logOut, or retrieve the user profile data
 * from the database
 *
 * @property authenticationService Injected authentication Service
 * @property profileService Injected profile Service
 */
@HiltViewModel
class SettingsMenuViewModel @Inject constructor(
    private val authenticationService: AuthenticationService,
    private val profileService: ProfileService
): ApplicationViewModel() {

    // States ------------------------------
    private var _userProfile = MutableStateFlow<Profile?>(null)
    val userProfile get() = _userProfile.asStateFlow()


    private var _email = MutableStateFlow(authenticationService.currentAuthEmail)
    val email get() = _email.asStateFlow()


    private var _authStatus = MutableStateFlow(false)
    val authStatus get() = _authStatus.asStateFlow()


    init {
        getAuthenticationData()
        getUserProfile()
    }


    // Methods ------------------------------

    private fun getAuthenticationData() {
        launchCatching {
            authenticationService.authStatus.collect { status ->
                _authStatus.value = status
                _email.value = authenticationService.currentAuthEmail
            }
        }
    }

    private fun getUserProfile() {
        launchCatching {
            profileService.actualAuthProfile.collect { actualAuthProfile ->
                _userProfile.value = actualAuthProfile
            }
        }
    }

    fun handleSignOut(displayToast: (String) -> Unit) {
        launchCatching(
            displayToast
        ) {
            authenticationService.signOut()
            displayToast("Successfully Logged Out")
        }
    }

}