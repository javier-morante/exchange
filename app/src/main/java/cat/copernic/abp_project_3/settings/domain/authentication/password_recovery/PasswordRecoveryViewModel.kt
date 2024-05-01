package cat.copernic.abp_project_3.settings.domain.authentication.password_recovery

import cat.copernic.abp_project_3.application.domain.ApplicationViewModel
import cat.copernic.abp_project_3.application.data.exceptions.validators.ValidationException
import cat.copernic.abp_project_3.application.data.service.authentication.AuthenticationService
import cat.copernic.abp_project_3.application.data.utils.validators.ProfileFieldValidators
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject

/**
 * Hilt ViewModel that contains all the logic referent to the Password Recovery Screen,
 * also contains the specific validator method and the method for executing the password recovery
 *
 * @property authenticationService Injected authentication Service
 */
@HiltViewModel
class PasswordRecoveryViewModel @Inject constructor(
    private val authenticationService: AuthenticationService
): ApplicationViewModel() {

    // States ------------------------------

    private var _email = MutableStateFlow("")
    val email get() = _email.asStateFlow()
    fun setEmail(value: String) { _email.value = value }



    // Methods ------------------------------

    fun validateData(
        displayToast: (String) -> Unit
    ): Boolean {
        try {
            ProfileFieldValidators.validateEmail(_email.value)
            return true

        } catch(e: ValidationException) {
            displayToast(e.message.orEmpty())

        } catch (e: Exception) {
            displayToast("Unexpected Error")
        }

        return false
    }

    fun handlePasswordRecovery(displayToast: (String) -> Unit) {
        launchCatching(
            {displayToast("Password Recovery Error")}
        ) {
            authenticationService.recoverPassword(_email.value)
            displayToast("Successful Password Recovery")
        }
    }

}