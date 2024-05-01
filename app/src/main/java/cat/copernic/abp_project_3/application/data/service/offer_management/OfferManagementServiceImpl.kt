package cat.copernic.abp_project_3.application.data.service.offer_management

import android.net.Uri
import android.util.Log
import cat.copernic.abp_project_3.application.data.enums.StorageReferences
import cat.copernic.abp_project_3.application.data.model.Offer
import cat.copernic.abp_project_3.application.data.service.authentication.AuthenticationService
import cat.copernic.abp_project_3.application.data.service.offer.OfferService
import cat.copernic.abp_project_3.application.data.service.storage.StorageService
import javax.inject.Inject

/**
 * Service uncharged in managing all the data related to the offers management
 *
 * @property authenticationService
 * @property offerService
 * @property storageService
 */
class OfferManagementServiceImpl @Inject constructor(
    private val authenticationService: AuthenticationService,
    private val offerService: OfferService,
    private val storageService: StorageService
): OfferManagementService {

    /**
     * Method that creates a new offer and stores his offer image
     *
     * @param offerData
     * @param imageUri
     */
    override suspend fun createOffer(offerData: Offer, imageUri: Uri?) {
        if(imageUri != null) {
            try {
                offerData.imageUrl = storageService.uploadImage(
                    StorageReferences.OFFERS_IMAGE,
                    offerData.id,
                    imageUri
                )
            } catch (e: Exception) {
                Log.d("offer_management:create_offer", "Error creating offer image")
            }
        }

        offerData.ownerRef = authenticationService.currentAuthUserUid!!
        offerService.createOffer(offerData)
    }

    /**
     * Method that updates an offer data and image
     *
     * @param offerData
     * @param imageUri
     */
    override suspend fun updateOffer(offerData: Offer, imageUri: Uri?) {
        if(imageUri != null) {
            try {
                offerData.imageUrl = storageService.uploadImage(
                    StorageReferences.OFFERS_IMAGE,
                    offerData.id,
                    imageUri
                )
            } catch (e: Exception) {
                Log.d("offer_management:update_offer", "Error Updating offer image")
            }
        }

        offerService.createOffer(offerData)
    }

    /**
     * Method that deletes an offer and his image
     *
     * @param offerData
     */
    override suspend fun deleteOffer(offerData: Offer) {
        try {
            storageService.deleteImage(
                storageReference = StorageReferences.OFFERS_IMAGE,
                imageName = offerData.id
            )
        } catch (e: Exception) {
            Log.d("offer_management:delete_offer", "Error Deletting offer image")
        }

        offerService.deleteOffer(offerData.id)
    }
}