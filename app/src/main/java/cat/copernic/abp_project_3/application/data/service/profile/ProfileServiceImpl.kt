package cat.copernic.abp_project_3.application.data.service.profile

import android.util.Log
import cat.copernic.abp_project_3.application.data.exceptions.services.ProfileException
import cat.copernic.abp_project_3.application.data.model.Profile
import cat.copernic.abp_project_3.application.data.service.authentication.AuthenticationService
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.FieldPath
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.tasks.await
import javax.inject.Inject
import javax.inject.Named

/**
 * Service uncharged in all the actions related to the user profiles
 *
 * @property firebaseAuth
 * @property profileCollection
 * @property authenticationService
 */
class ProfileServiceImpl @Inject constructor(
    private val firebaseAuth: FirebaseAuth,
    @Named("profileCollection") private val profileCollection: CollectionReference,
    private val authenticationService: AuthenticationService
): ProfileService {

    /**
     * Flow that returns all tha user profile of the user actually logged into the application
     */
    override val actualAuthProfile: Flow<Profile?>
        get() = callbackFlow {
            authenticationService.authStatus.collect { currentAuthStatus ->
                if(!currentAuthStatus) {
                    Log.d("profile:actual_auth_profile", "-----> User Not authenticated => Not Fetching actual auth profile <-----")
                    this.trySend(null)
                    return@collect
                }

                val currentAuthProfile = profileCollection
                    .document(authenticationService.currentAuthUserUid!!)
                    .get().await().toObject(Profile::class.java)

                Log.d("profile:actual_auth_profile", "-----> User Authenticated => Fetching actual auth profile <-----")
                this.trySend(currentAuthProfile)
            }

            awaitClose()
        }

    /**
     * Flow that returns a list that contains all the user profiles in the application
     */
    override val actualProfilesList: Flow<List<Profile>>
        get() = callbackFlow {
            val listener = profileCollection
                .whereNotEqualTo(FieldPath.documentId(), authenticationService.currentAuthUserUid)
                .orderBy("name")
                .addSnapshotListener { snapshot, error ->
                    if(error != null) {
                        return@addSnapshotListener
                    }

                    if(snapshot != null) {
                        Log.d("profile:actual_profiles", "-----> Fetching Profiles <-----")

                        val profiles = snapshot.documents.map { document ->
                            document.toObject(Profile::class.java) ?: Profile()
                        }

                        trySend(profiles)
                    }
                }

            awaitClose { listener.remove() }
        }

    /**
     * Method that creates a new profile
     *
     * @param newProfile
     */
    override suspend fun createProfile(newProfile: Profile) {
        profileCollection
            .document(authenticationService.currentAuthUserUid!!)
            .set(newProfile)
            .addOnFailureListener {
                throw ProfileException("Error creating profile")
            }.await()
    }

    /**
     * Method that returns a user profile based on the provided id
     *
     * @param id
     * @return
     */
    override suspend fun getProfile(id: String): Profile? {
        return profileCollection
            .document(id)
            .get()
            .addOnFailureListener {
                //throw ProfileException("Error getting profile")
            }.await().toObject(Profile::class.java)
    }

    /**
     * Method that returns a list that contains all the user profiles
     *
     * @return
     */
    override suspend fun getProfiles(): List<Profile> {
        return profileCollection
            .orderBy("name")
            .get()
            .addOnFailureListener {
                throw ProfileException("Error getting profiles")
            }.await().toObjects(Profile::class.java)
    }

    /**
     * Method that updates an existing user profile
     *
     * @param profile
     */
    override suspend fun updateProfile(profile: Profile) {
        profileCollection
            .document(profile.id)
            .set(profile)
            .addOnFailureListener {
                throw ProfileException("Error updating profile")
            }.await()
    }

    /**
     * Method that deletes a user profile based on the provided id
     *
     * @param id
     */
    override suspend fun deleteProfile(id: String) {
        profileCollection
            .document(id)
            .delete()
            .addOnFailureListener {
                throw ProfileException("Error deleting profile")
            }.await()
    }

}