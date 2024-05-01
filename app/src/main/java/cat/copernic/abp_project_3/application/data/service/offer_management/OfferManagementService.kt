package cat.copernic.abp_project_3.application.data.service.offer_management

import android.net.Uri
import cat.copernic.abp_project_3.application.data.model.Offer

/**
 * Interface that contains all the methods related to offer management
 */
interface OfferManagementService {

    /**
     * @param offerData Offer instance
     * @param imageUri Offer image Uri
     */
    suspend fun createOffer(offerData: Offer, imageUri: Uri?)

    /**
     * @param offerData Offer instance
     * @param imageUri Offer image Uri
     */
    suspend fun updateOffer(offerData: Offer, imageUri: Uri?)

    /**
     * @param offerData Offer instance
     */
    suspend fun deleteOffer(offerData: Offer)
}