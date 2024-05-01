package cat.copernic.abp_project_3.settings.presentation.authentication.sign_in

/**
 * Data class that contains all the state attributes referent to the Sign In Screen
 *
 * @property email
 * @property password
 */
data class SignInState(
    val email: String = "",
    val password: String = "",
){
    fun setEmail(newEmail: String): SignInState {
        return this.copy(email = newEmail)
    }

    fun setPassword(newPassword: String): SignInState {
        return this.copy(password = newPassword)
    }
}