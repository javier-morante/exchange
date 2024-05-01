package cat.copernic.abp_project_3.application.data.service.authentication

import com.google.firebase.auth.FirebaseUser
import kotlinx.coroutines.flow.Flow

/**
 * Interface that contains all the methods related to authentication
 */
interface AuthenticationService {

    val authStatus: Flow<Boolean>

    val currentAuthUser: FirebaseUser?
    val currentAuthUserUid: String?
    val currentAuthEmail: String?

    /**
     * @param email Email value
     * @param password Password value
     */
    suspend fun signIn(email: String, password: String)

    /**
     * @param email New email value
     * @param password New password value
     */
    suspend fun signUp(email: String, password: String)

    /**
     * @param email Email Value
     */
    suspend fun recoverPassword(email: String)

    /**
     * @param newEmail New Authenticated account email
     */
    suspend fun resetEmail(newEmail: String)

    /**
     * Method for signing out the authenticated user account
     */
    suspend fun signOut()

    /**
     * Method for deleting the authenticated user account
     */
    suspend fun deleteAccount()

}