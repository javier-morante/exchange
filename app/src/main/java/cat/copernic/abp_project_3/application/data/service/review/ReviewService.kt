package cat.copernic.abp_project_3.application.data.service.review

import cat.copernic.abp_project_3.application.data.model.Review
import kotlinx.coroutines.flow.Flow

/**
 * Interface that contains all the methods related to reviews
 */
interface ReviewService {

    /**
     * @param offerId Offer Id
     * @return Flow containing a list with all the actual offer reviews
     */
    fun getActualOfferReviews(offerId: String): Flow<List<Review>>

    /**
     * @param offerId Offer id
     * @param newReview New review instance
     */
    suspend fun createReview(offerId: String, newReview: Review)

    /**
     * @param offerId Offer id
     * @return List containing all the reviews based on the offer id
     */
    suspend fun getOfferReviews(offerId: String): List<Review>

    /**
     * @param offerId Offer id
     * @param reviewId Id of the review
     */
    suspend fun deleteReview(offerId: String, reviewId: String)

    /**
     * @param offerId Offer id
     * @param reviewId Id of the review
     * @param newMessage New message Instance
     */
    suspend fun updateReviewMessage(offerId: String, reviewId: String, newMessage: String)
}