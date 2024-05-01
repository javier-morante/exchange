package cat.copernic.abp_project_3.application.data.service.authentication

import android.util.Log
import cat.copernic.abp_project_3.application.data.exceptions.services.AuthenticationException
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

/**
 * Service uncharged in managing all the actions referent to the authentication
 *
 * @property auth Injected firebase authentication
 */
class AuthenticationServiceImpl @Inject constructor(
    private val auth: FirebaseAuth
): AuthenticationService {

    /**
     * Returns a flow that listens to the authentication status
     */
    override val authStatus: Flow<Boolean>
        get() = callbackFlow {
            val listener = FirebaseAuth.AuthStateListener { auth ->
                val authStatus = auth.currentUser != null
                this.trySend(authStatus)
                Log.d("authentication:auth_status", "Auth Status => $authStatus")
            }
            auth.addAuthStateListener(listener)
            awaitClose { auth.removeAuthStateListener(listener) }
        }

    /**
     * Returns the actual authenticated user
     */
    override val currentAuthUser: FirebaseUser?
        get() = auth.currentUser

    /**
     * Returns the actual current user UID
     */
    override val currentAuthUserUid: String?
        get() = auth.currentUser?.uid

    /**
     * Returns the actual current auth user email
     */
    override val currentAuthEmail: String?
        get() = auth.currentUser?.email

    /**
     * Method that signs in the user into the firebase authentication system
     *
     * @param email
     * @param password
     */
    override suspend fun signIn(email: String, password: String) {
        auth.signInWithEmailAndPassword(email, password).await()
    }

    /**
     * Method that signs up the user into the firebase authentication system
     *
     * @param email
     * @param password
     */
    override suspend fun signUp(email: String, password: String) {
        auth.createUserWithEmailAndPassword(email, password).await()
    }

    /**
     * Method that sends a recovery password email
     *
     * @param email
     */
    override suspend fun recoverPassword(email: String) {
        auth.sendPasswordResetEmail(email)
            .addOnFailureListener {
                throw AuthenticationException("Error in password recover")
            }.await()
    }

    /**
     * Method that sends a restore email email
     *
     * @param newEmail
     */
    override suspend fun resetEmail(newEmail: String) {
        auth.currentUser!!.verifyBeforeUpdateEmail(newEmail).await()
    }

    /**
     * Method that signs out the actual authenticated user
     *
     */
    override suspend fun signOut() {
        auth.signOut()
    }

    /**
     * Method that deletes the actual authenticated account
     *
     */
    override suspend fun deleteAccount() {
        auth.currentUser!!.delete().await()
    }

}