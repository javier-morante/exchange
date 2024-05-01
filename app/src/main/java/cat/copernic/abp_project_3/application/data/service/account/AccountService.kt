package cat.copernic.abp_project_3.application.data.service.account

import android.net.Uri
import cat.copernic.abp_project_3.application.data.model.Profile

/**
 * Interface that contains all the methods related to accounts
 */
interface AccountService {

    /**
     * @param email Account email
     * @param password Account password
     */
    suspend fun authenticateAccount(email: String, password: String)

    /**
     * @param email New Account email
     * @param password New Account password
     * @param profile Profile Instance
     * @param imageUri Profile Image Uri
     */
    suspend fun createAccount(email: String, password: String, profile: Profile, imageUri: Uri?)

    /**
     * @param profile Profile Instance
     * @param imageUri Profile Image Uri
     */
    suspend fun updateAccount(profile: Profile, imageUri: Uri?)

    /**
     * Deletes the actual auth user account
     */
    suspend fun deleteAccount()

}