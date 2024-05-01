package cat.copernic.abp_project_3.application.data.service.review

import android.util.Log
import cat.copernic.abp_project_3.application.data.exceptions.services.ReviewException
import cat.copernic.abp_project_3.application.data.model.Review
import com.google.firebase.firestore.CollectionReference
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.tasks.await
import javax.inject.Inject
import javax.inject.Named

/**
 * Service uncharged in all the actions related to the reviews
 *
 * @property offerCollection
 */
class ReviewServiceImpl @Inject constructor(
    @Named("offerCollection") private val offerCollection: CollectionReference
): ReviewService {

    /**
     * Flow that returns all the actual offers reviews based on the provided offerId
     *
     * @param offerId
     * @return
     */
    override fun getActualOfferReviews(offerId: String): Flow<List<Review>> = callbackFlow {
        val listener = offerCollection
            .document(offerId)
            .collection(REVIEW_COLLECTION_NAME)
            .addSnapshotListener { snapshot, error ->
                if(error != null) {
                    return@addSnapshotListener
                }

                if(snapshot != null) {
                    Log.d("review:actual_reviews", "-----> Fetching Reviews <-----")

                    val reviews = snapshot.documents.map { document ->
                        document.toObject(Review::class.java) ?: Review()
                    }

                    trySend(reviews)
                }
            }

        awaitClose { listener.remove() }
    }

    /**
     * Method that creates a new review into the database
     *
     * @param offerId
     * @param newReview
     */
    override suspend fun createReview(offerId: String, newReview: Review) {
        offerCollection
            .document(offerId)
            .collection(REVIEW_COLLECTION_NAME)
            .document(newReview.id)
            .set(newReview)
            .addOnFailureListener {
                throw ReviewException("Error creating review")
            }.await()
    }

    /**
     * Method that returns all the reviews based on a offerId from the database
     *
     * @param offerId
     * @return
     */
    override suspend fun getOfferReviews(offerId: String): List<Review> {
        return offerCollection
            .document(offerId)
            .collection(REVIEW_COLLECTION_NAME)
            .get()
            .addOnFailureListener {
                throw ReviewException("Error getting offer reviews")
            }.await().toObjects(Review::class.java)
    }

    /**
     * Method that deletes a review from the database
     *
     * @param offerId
     * @param reviewId
     */
    override suspend fun deleteReview(offerId: String, reviewId: String) {
        offerCollection
            .document(offerId)
            .collection(REVIEW_COLLECTION_NAME)
            .document(reviewId)
            .delete()
            .addOnFailureListener {
                throw ReviewException("Error deleting review")
            }.await()
    }

    /**
     * Method that updates a review message from the database
     *
     * @param offerId
     * @param reviewId
     * @param newMessage
     */
    override suspend fun updateReviewMessage(offerId: String, reviewId: String, newMessage: String) {
        try {
            offerCollection
                .document(offerId)
                .collection(REVIEW_COLLECTION_NAME)
                .document(reviewId)
                .update("message", newMessage)
                .await()
        } catch (e: Exception) {
            throw ReviewException("Error updating review message")
        }
    }

    companion object {
        const val REVIEW_COLLECTION_NAME = "reviews"
    }

}