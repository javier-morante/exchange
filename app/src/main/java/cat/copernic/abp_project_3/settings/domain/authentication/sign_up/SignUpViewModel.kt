package cat.copernic.abp_project_3.settings.domain.authentication.sign_up

import android.net.Uri
import cat.copernic.abp_project_3.application.domain.ApplicationViewModel
import cat.copernic.abp_project_3.application.data.exceptions.validators.ValidationException
import cat.copernic.abp_project_3.application.data.service.account.AccountService
import cat.copernic.abp_project_3.settings.presentation.SettingsBaseNav
import cat.copernic.abp_project_3.settings.presentation.authentication.sign_up.SignUpBaseNav
import cat.copernic.abp_project_3.settings.presentation.authentication.sign_up.SignUpState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import javax.inject.Inject

/**
 * Hilt ViewModel that contains all the logic referent to the Sign Up Screen, it also contains
 * a state attribute and the corresponding step validators and the method to execute the sign Up
 *
 * @property accountService Injected account Service
 */
@HiltViewModel
class SignUpViewModel @Inject constructor(
    private val accountService: AccountService
): ApplicationViewModel() {

    // States ------------------------------

    private var _actualScreen = MutableStateFlow(SignUpBaseNav.CREDENTIALS.name)
    val actualScreen get() = _actualScreen.asStateFlow()
    fun setActualScreen(newScreen: String) { _actualScreen.value = newScreen }

    private var _signUpState = MutableStateFlow(SignUpState())
    val signUpState get() = _signUpState.asStateFlow()

    fun setEmail(newEmail: String) {
        _signUpState.update { currentState ->
            currentState.setEmail(newEmail)
        }
    }

    fun setPassword(newPassword: String) {
        _signUpState.update { currentState ->
            currentState.setPassword(newPassword)
        }
    }

    fun setRepeatPassword(newRepeatPassword: String) {
        _signUpState.update { currentState ->
            currentState.setRepeatPassword(newRepeatPassword)
        }
    }

    fun setAccountImageUri(newAccountImageUri: Uri?) {
        _signUpState.update { currentState ->
            currentState.setAccountImageUri(newAccountImageUri)
        }
    }

    fun setName(newName: String) {
        _signUpState.update { currentState ->
            val userData = currentState.userProfile.setName(newName)
            currentState.setUserData(userData)
        }
    }

    fun setSurname(newSurname: String) {
        _signUpState.update { currentState ->
            val userData = currentState.userProfile.setSurname(newSurname)
            currentState.setUserData(userData)
        }
    }

    fun setPhone(newPhone: String) {
        _signUpState.update { currentState ->
            val userData = currentState.userProfile.setPhone(newPhone)
            currentState.setUserData(userData)
        }
    }

    // Methods ------------------------------

    fun validateStepOne(
        displayToast: (String) -> Unit
    ): Boolean {
        try {
            _signUpState.value.validateSignUpStepOne()
            return true

        } catch(e: ValidationException) {
            displayToast(e.message.orEmpty())

        } catch (e: Exception) {
            displayToast("Unexpected Error")
        }

        return false
    }

    fun validateStepTwo(
        displayToast: (String) -> Unit
    ): Boolean {
        try {
            _signUpState.value.validateSignUpStepTwo()
            return true

        } catch (e: ValidationException) {
            displayToast(e.message.orEmpty())

        } catch (e: Exception) {
            displayToast("Unexpected Error")
        }

        return false
    }


    fun handleSignUp(
        displayToast: (String) -> Unit,
        onSuccessNavigation: (String) -> Unit
    ) {

        launchCatching(
            displayToast
        ) {
            accountService.createAccount(
                email = _signUpState.value.email,
                password = _signUpState.value.password,
                profile = _signUpState.value.userProfile,
                imageUri = _signUpState.value.accountImageUri
            )
            onSuccessNavigation(SettingsBaseNav.MENU.name)
        }
    }

}