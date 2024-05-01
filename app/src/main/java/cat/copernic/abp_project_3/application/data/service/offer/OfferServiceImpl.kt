package cat.copernic.abp_project_3.application.data.service.offer

import android.util.Log
import cat.copernic.abp_project_3.application.data.enums.OfferState
import cat.copernic.abp_project_3.application.data.exceptions.services.OfferException
import cat.copernic.abp_project_3.application.data.model.Category
import cat.copernic.abp_project_3.application.data.model.Offer
import cat.copernic.abp_project_3.application.data.service.authentication.AuthenticationService
import com.google.firebase.firestore.CollectionReference
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.tasks.await
import javax.inject.Inject
import javax.inject.Named

/**
 * Service that manages all the actions related with offers management
 *
 * @property offerCollection
 * @property authenticationService
 */
class OfferServiceImpl @Inject constructor(
    @Named("offerCollection") private val offerCollection: CollectionReference,
    private val authenticationService: AuthenticationService
): OfferService {

    /**
     * Flow that returns a list that contains all the data referent to an existing enabled offer
     *
     * @param offerId
     * @return
     */
    override fun actualOffer(offerId: String): Flow<Offer> = callbackFlow {
        val listener = offerCollection
            .document(offerId)
            .addSnapshotListener { snapshot, error ->
                if(error != null) {
                    return@addSnapshotListener
                }

                if(snapshot != null) {
                    Log.d(
                        "offer:actual_auth_user_offers",
                        "-----> Fetching offers <-----"
                    )

                    val offer = snapshot.toObject(Offer::class.java) ?: Offer()
                    trySend(offer)
                }
            }

        awaitClose { listener.remove() }
    }

    /**
     * Flow that returns a list that contains all the offers from the database
     */
    override val allOffers: Flow<List<Offer>>
        get() = callbackFlow {
            val listener = offerCollection
                .addSnapshotListener { snapshot, error ->
                    if(error != null) {
                        return@addSnapshotListener
                    }

                    if(snapshot != null) {
                        Log.d(
                            "offer:all_offers",
                            "-----> Fetching offers <-----"
                        )

                        val offers = snapshot.documents.map { document ->
                            val offer = document.toObject(Offer::class.java) ?: Offer()
                            offer.id = document.id
                            offer
                        }

                        trySend(offers)
                    }
                }

            awaitClose { listener.remove() }
        }

    /**
     * Flow that returns all the enabled offers from the database
     */
    override val actualOffers: Flow<List<Offer>>
        get() = callbackFlow {
            val listener = offerCollection
                .whereNotEqualTo("offerState", OfferState.DISABLED.name)
                .addSnapshotListener { snapshot, error ->
                    if(error != null) {
                        return@addSnapshotListener
                    }

                    if(snapshot != null) {
                        Log.d(
                            "offer:actual_enabled_offers",
                            "-----> Fetching offers <-----"
                        )

                        val offers = snapshot.documents.map { document ->
                            val offer = document.toObject(Offer::class.java) ?: Offer()
                            offer.id = document.id
                            offer
                        }

                        trySend(offers)
                    }
                }

            awaitClose { listener.remove() }
        }

    /**
     * Flow that returns all the offers referent to the actual auth user
     */
    override val actualAuthUserOffers: Flow<List<Offer>>
        get() = callbackFlow {
            val listener = offerCollection
                .whereEqualTo("ownerRef", authenticationService.currentAuthUserUid ?: "")
                .addSnapshotListener { snapshot, error ->
                    if(error != null) {
                        return@addSnapshotListener
                    }

                    if(snapshot != null) {
                        Log.d(
                            "offer:actual_auth_user_offers",
                            "-----> Fetching offers <-----"
                        )

                        val offers = snapshot.documents.map { document ->
                            val offer = document.toObject(Offer::class.java) ?: Offer()
                            offer.id = document.id
                            offer
                        }

                        Log.d("offer:actual_auth_user_offers", "Offers => $offers")
                        trySend(offers)
                    }
                }

            awaitClose { listener.remove() }
        }

    /**
     * Method uncharged of creating a new offer
     *
     * @param newOffer
     */
    override suspend fun createOffer(newOffer: Offer) {
        offerCollection
            .document(newOffer.id)
            .set(newOffer)
            .addOnFailureListener {
                throw OfferException("Error creating offer")
            }.await()
    }

    /**
     * Method uncharged in getting an existing offer
     *
     * @param id
     * @return
     */
    override suspend fun getOffer(id: String): Offer? {
        return offerCollection
            .document(id)
            .get()
            .addOnFailureListener {
                throw OfferException("Error getting offer")
            }.await().toObject(Offer::class.java)
    }

    /**
     * Method uncharged in getting all the offers
     *
     * @return
     */
    override suspend fun getAllOffers(): List<Offer> {
        return offerCollection
            .get()
            .addOnFailureListener {
                throw OfferException("Error getting all offers")
            }.await().toObjects(Offer::class.java)
    }

    /**
     * Method incharged in getting all the actual enabled offers
     *
     * @return
     */
    override suspend fun getActualOffers(): List<Offer> {
        return offerCollection
            .whereNotEqualTo("offerState", OfferState.DISABLED.name)
            .get()
            .addOnFailureListener {
                throw OfferException("Error getting actual offers")
            }.await().toObjects(Offer::class.java)
    }

    /**
     * Method uncharged in getting all the offers by category from the database
     *
     * @param category
     * @return
     */
    override suspend fun getOffersByCategory(category: Category): List<Offer> {
        return offerCollection
            .whereEqualTo("categoryRef", category.id)
            .get()
            .addOnFailureListener {
                throw OfferException("Error getting offers")
            }.await().toObjects(Offer::class.java)
    }

    /**
     * Method uncharged in updating an existing offer from the database
     *
     * @param offer
     */
    override suspend fun updateOffer(offer: Offer) {
        offerCollection
            .document(offer.id)
            .set(offer)
            .addOnFailureListener {
                throw OfferException("Error updating offer")
            }.await()
    }

    /**
     * Method uncharged in deleting an existin offer
     *
     * @param id
     */
    override suspend fun deleteOffer(id: String) {
        offerCollection
            .document(id)
            .delete()
            .addOnFailureListener {
                throw OfferException("Error deleting offer")
            }.await()
    }

}