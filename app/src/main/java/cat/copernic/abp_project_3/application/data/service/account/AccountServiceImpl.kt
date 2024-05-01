package cat.copernic.abp_project_3.application.data.service.account

import android.accounts.AccountsException
import android.content.ContentValues.TAG
import android.net.Uri
import android.util.Log
import cat.copernic.abp_project_3.application.data.enums.StorageReferences
import cat.copernic.abp_project_3.application.data.exceptions.services.AuthenticationException
import cat.copernic.abp_project_3.application.data.model.Profile
import cat.copernic.abp_project_3.application.data.service.authentication.AuthenticationService
import cat.copernic.abp_project_3.application.data.service.offer.OfferService
import cat.copernic.abp_project_3.application.data.service.offer_management.OfferManagementService
import cat.copernic.abp_project_3.application.data.service.profile.ProfileService
import cat.copernic.abp_project_3.application.data.service.storage.StorageService
import javax.inject.Inject

/**
 * Service uncharged in managing all the services referent ot the user accounts
 *
 * @property authenticationService
 * @property profileService
 * @property storageService
 */
class AccountServiceImpl @Inject constructor(
    private val authenticationService: AuthenticationService,
    private val profileService: ProfileService,
    private val storageService: StorageService,
    private val offerService: OfferService,
    private val offerManagementService: OfferManagementService
): AccountService {

    /**
     * Method that authenticates an account into the system
     *
     * @param email
     * @param password
     */
    override suspend fun authenticateAccount(email: String, password: String) {
        try {
            authenticationService.signIn(email, password)

            val authUserUid = authenticationService.currentAuthUserUid ?: ""
            val userProfile = profileService.getProfile(authUserUid) ?: Profile()

            if(!userProfile.isEnabled) {
                authenticationService.signOut()
                throw AuthenticationException("User disabled")
            }

        } catch (e: Exception) {
            Log.d(TAG, "Error when authenticating account")
            throw AccountsException("Error when sign in")
        }
    }

    /**
     * Method that creates a new account with the profile, the auth account and the profile image
     *
     * @param email
     * @param password
     * @param profile
     * @param imageUri
     */
    override suspend fun createAccount(email: String, password: String, profile: Profile, imageUri: Uri?) {
        // Create the user account in the authentication service
        authenticationService.signUp(email, password)

        // Upload the profile image into the storage service
         profile.imageUrl = storageService.uploadImage(
            storageReference = StorageReferences.ACCOUNTS_IMAGE,
            imageName = authenticationService.currentAuthUserUid ?: "",
            imageUri = imageUri
        )

        // Create the new user profile
        profileService.createProfile(profile)
    }

    /**
     * Method that updates an account profile
     *
     * @param profile
     * @param imageUri
     */
    override suspend fun updateAccount(profile: Profile, imageUri: Uri?) {
        // Delete the previous image from the storage if there is a new image provided
        if(imageUri != null) {
            // Upload and grab the new image Url
            profile.imageUrl = storageService.uploadImage(
                storageReference = StorageReferences.ACCOUNTS_IMAGE,
                imageName = profile.id,
                imageUri = imageUri
            )
        }

        // Update all the profile data
        profileService.updateProfile(profile)
    }

    /**
     * Method that deletes all the data referent to the authenticated user
     *
     */
    override suspend fun deleteAccount() {
        val currentAuthUid = authenticationService.currentAuthUserUid ?: ""

        // Delete the account Image
        storageService.deleteImage(
            storageReference =  StorageReferences.ACCOUNTS_IMAGE,
            imageName = currentAuthUid
        )

        // Delete the user profile
        profileService.deleteProfile(currentAuthUid)

        for(offer in offerService.getAllOffers()) {
            if(offer.ownerRef == currentAuthUid) {
                offerManagementService.deleteOffer(offer)
            }
        }

        // Delete the authentication user
        authenticationService.deleteAccount()
    }

}