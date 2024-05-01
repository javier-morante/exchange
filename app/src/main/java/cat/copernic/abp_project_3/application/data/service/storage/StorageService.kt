package cat.copernic.abp_project_3.application.data.service.storage

import android.net.Uri
import cat.copernic.abp_project_3.application.data.enums.StorageReferences

/**
 * Interface that contains all the methods related to storage
 */
interface StorageService {

    /**
     * @param storageReference Reference to the storage path
     * @param imageName Name of the new image
     * @param imageUri Uri of the image
     * @return Image Resource URL in firebase storage
     */
    suspend fun uploadImage(
        storageReference: StorageReferences,
        imageName: String,
        imageUri: Uri?
    ): String

    /**
     * @param storageReference Reference to the storage path
     * @param imageName Name of the image
     */
    suspend fun deleteImage(
        storageReference: StorageReferences,
        imageName: String
    )

}