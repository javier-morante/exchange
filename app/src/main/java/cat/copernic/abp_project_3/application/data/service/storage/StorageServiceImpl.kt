package cat.copernic.abp_project_3.application.data.service.storage

import android.net.Uri
import cat.copernic.abp_project_3.application.data.enums.StorageReferences
import cat.copernic.abp_project_3.application.data.exceptions.services.StorageException
import com.google.firebase.storage.FirebaseStorage
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

/**
 * Service uncharged in managing all the actions referent to firebase storage
 *
 * @property firebaseStorage
 */
class StorageServiceImpl @Inject constructor(
    private val firebaseStorage: FirebaseStorage
): StorageService {

    /**
     * Method that uploads a provided image based on the provided parameters
     *
     * @param storageReference A reference enum to the storage folder
     * @param imageName The name of the uploaded image
     * @param imageUri The uri that represents the image that is going to be uploaded
     * @return
     */
    override suspend fun uploadImage(
        storageReference: StorageReferences,
        imageName: String,
        imageUri: Uri?
    ): String {
        val reference = "${storageReference.path}/${imageName}.jpg"
        var downloadUrl = ""

        if(imageUri != null) {
            val ref = firebaseStorage.getReference(reference)
            ref.putFile(imageUri).addOnFailureListener {
                throw StorageException("Error uploading storage image")
            }.await()

            ref.downloadUrl
                .addOnSuccessListener {
                    downloadUrl = it.toString()
                }
                .addOnFailureListener {
                    throw StorageException("Error fetching uploaded image URL")
                }.await()
        }

        return downloadUrl
    }

    /**
     * Method that deletes a image based on the provided parameters
     *
     * @param storageReference A reference enum to the storage folder
     * @param imageName The name of the image that is going to be deleted
     */
    override suspend fun deleteImage(storageReference: StorageReferences, imageName: String) {
        val reference = "${storageReference.path}/${imageName}.jpg"
        val ref = firebaseStorage.getReference(reference)
        ref.delete().addOnFailureListener {
            throw StorageException("Error deleting storage image")
        }.await()
    }

}