package cat.copernic.abp_project_3.application.domain

import cat.copernic.abp_project_3.application.presentation.AppBaseNav
import cat.copernic.abp_project_3.application.data.service.authentication.AuthenticationService
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject

/**
 * ViewModel responsible for managing the main screen state and navigation within the application.
 * This ViewModel controls the currently displayed screen based on user interaction.
 *
 * @param authenticationService The service responsible for handling authentication.
 */
@HiltViewModel
class MainViewModel @Inject constructor(
    private val authenticationService: AuthenticationService,
): ApplicationViewModel() {

    private var _actualScreen = MutableStateFlow(AppBaseNav.CATALOGUE.name)
    val actualScreen get() = _actualScreen.asStateFlow()

    /**
     * Sets the current screen to be displayed in the application.
     *
     * @param newScreen The name of the new screen to be displayed.
     */
    fun setActualScreen(newScreen: String) {
        _actualScreen.value = newScreen
    }


    private var _authUserStatus = MutableStateFlow(false)
    val authUserStatus get() = _authUserStatus.asStateFlow()

    init {
        launchCatching {
            authenticationService.authStatus.collect { status ->
                _authUserStatus.value = status
            }
        }
    }

}