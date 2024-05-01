package cat.copernic.abp_project_3.settings.domain.authentication.sign_in

import cat.copernic.abp_project_3.application.domain.ApplicationViewModel
import cat.copernic.abp_project_3.application.data.exceptions.validators.ValidationException
import cat.copernic.abp_project_3.application.data.service.account.AccountService
import cat.copernic.abp_project_3.application.data.utils.validators.ProfileFieldValidators
import cat.copernic.abp_project_3.settings.presentation.authentication.sign_in.SignInState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import javax.inject.Inject

/**
 * Hilt ViewModel that contains all the logic referent to the Sign In Screen, it also contains
 * a state attribute and the corresponding step validator and  the method for signing
 * the user into the application
 *
 * @property accountService Injected account Service
 */
@HiltViewModel
class SignInViewModel @Inject constructor(
    private val accountService: AccountService
): ApplicationViewModel() {

    // States ------------------------------

    private var _signInState = MutableStateFlow(SignInState())
    val signInState get() = _signInState.asStateFlow()

    fun setEmail(newEmail: String) {
        _signInState.update { currentState ->
            currentState.setEmail(newEmail)
        }
    }

    fun setPassword(newPassword: String) {
        _signInState.update { currentState ->
            currentState.setPassword(newPassword)
        }
    }

    fun validateData(
        displayToast: (String) -> Unit
    ): Boolean {
        try {
            ProfileFieldValidators.validateEmail(_signInState.value.email)
            return true

        } catch(e: ValidationException) {
            displayToast(e.message.orEmpty())

        } catch (e: Exception) {
            displayToast("Unexpected Error")
        }

        return false
    }

    // Methods ------------------------------

    fun handleSignIn(
        displayToast: (String) -> Unit,
        onSuccessNavigation: () -> Unit
    ) {
        launchCatching(
            displayToast
        ) {
            accountService.authenticateAccount(
                _signInState.value.email,
                _signInState.value.password
            )
            onSuccessNavigation()
        }
    }

}