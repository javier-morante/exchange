package cat.copernic.abp_project_3.settings.domain.users

import androidx.compose.runtime.mutableStateListOf
import cat.copernic.abp_project_3.application.domain.ApplicationViewModel
import cat.copernic.abp_project_3.application.data.model.Profile
import cat.copernic.abp_project_3.application.data.service.profile.ProfileService
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

/**
 * Hilt ViewModel that contains all the logic referent to the users management screen
 * it contains a list with all the users that are collected when the viewModel is instantiated
 *
 * @property profileService Injected profile service
 */
@HiltViewModel
class UsersManagerViewModel @Inject constructor(
    private val profileService: ProfileService
): ApplicationViewModel() {

    private var _usersList = mutableStateListOf<Profile>()
    val usersList get() = _usersList.toList()

    init {
        reloadUsers()
    }

    private fun reloadUsers() {
        launchCatching {
            profileService.actualProfilesList.collect { actualProfilesList ->
                _usersList.clear()
                _usersList.addAll(actualProfilesList)
            }
        }
    }

}