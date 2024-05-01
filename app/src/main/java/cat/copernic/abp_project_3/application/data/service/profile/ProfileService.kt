package cat.copernic.abp_project_3.application.data.service.profile

import cat.copernic.abp_project_3.application.data.model.Profile
import kotlinx.coroutines.flow.Flow

/**
 * Interface that contains all the methods related to profiles
 */
interface ProfileService {

    val actualAuthProfile: Flow<Profile?>
    val actualProfilesList: Flow<List<Profile>>

    /**
     * @param newProfile New profile instance
     */
    suspend fun createProfile(newProfile: Profile)

    /**
     * @param id Id of the profile
     * @return Profile Instance
     */
    suspend fun getProfile(id: String): Profile?

    /**
     * @return List containing all the profiles
     */
    suspend fun getProfiles(): List<Profile>

    /**
     * @param profile Profile instance
     */
    suspend fun updateProfile(profile: Profile)

    /**
     * @param id Id of the profile
     */
    suspend fun deleteProfile(id: String)

}