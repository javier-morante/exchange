package cat.copernic.abp_project_3.application.data.service.offer

import cat.copernic.abp_project_3.application.data.model.Category
import cat.copernic.abp_project_3.application.data.model.Offer
import kotlinx.coroutines.flow.Flow

/**
 * Interface that contains all the methods related to offers
 */
interface OfferService {

    /**
     * @param offerId Id of the offer
     * @return Flow with the offer data
     */
    fun actualOffer(offerId: String): Flow<Offer>

    val allOffers: Flow<List<Offer>>
    val actualOffers: Flow<List<Offer>>
    val actualAuthUserOffers: Flow<List<Offer>>

    /**
     * @param newOffer New offer instance
     */
    suspend fun createOffer(newOffer: Offer)

    /**
     * @param id Id of the offer
     * @return Offer instance
     */
    suspend fun getOffer(id: String): Offer?

    /**
     * @return List containing all the offers
     */
    suspend fun getAllOffers(): List<Offer>

    /**
     * @return List containing all the actual enabled offers
     */
    suspend fun getActualOffers(): List<Offer>

    /**
     * @param category Category instance
     * @return List containing all the actual offers that belong to the provided category
     */
    suspend fun getOffersByCategory(category: Category): List<Offer>

    /**
     * @param offer Instance of the offer
     */
    suspend fun updateOffer(offer: Offer)

    /**
     * @param id Offer id string
     */
    suspend fun deleteOffer(id: String)

}