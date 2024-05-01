package cat.copernic.abp_project_3.offers_catalogue.domain.offer_details

import cat.copernic.abp_project_3.application.domain.ApplicationViewModel
import cat.copernic.abp_project_3.application.data.model.Review
import cat.copernic.abp_project_3.application.data.service.authentication.AuthenticationService
import cat.copernic.abp_project_3.application.data.service.review.ReviewService
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import javax.inject.Inject

/**
 * ViewModel class responsible for managing the creation, deletion, and updates of reviews within the application.
 * This ViewModel interacts with the ReviewService and AuthenticationService to perform review-related operations
 * based on the current authenticated user.
 *
 * @param reviewService         Service responsible for managing review data.
 * @param authenticationService Service responsible for managing user authentication status.
 */
@HiltViewModel
class CreateReviewViewModel @Inject constructor(
    private val reviewService: ReviewService,
    private val authenticationService: AuthenticationService
) : ApplicationViewModel() {

    private var _review =
        MutableStateFlow(Review(ownerRef = authenticationService.currentAuthUserUid ?: ""))
    val review = _review.asStateFlow()

    /**
     * Creates a new review for the specified offer.
     * The review will be created if the review's star rating is greater than 0.
     *
     * @param offerId The ID of the offer for which the review is created.
     */
    fun createReview(offerId: String) {
        if (review.value.stars == 0) {
            return
        }

        launchCatching {
            reviewService.createReview(offerId, review.value)
            _review.value = Review(ownerRef = authenticationService.currentAuthUserUid ?: "")
        }
    }

    /**
     * Deletes a review associated with the specified offer and review ID.
     *
     * @param offerId  The ID of the offer to which the review belongs.
     * @param reviewId The ID of the review to delete.
     */
    fun deleteReview(offerId: String, reviewId: String) {
        launchCatching {
            reviewService.deleteReview(offerId, reviewId)
        }
    }

    /**
     * Updates the message content of the review.
     *
     * @param message The new message content for the review.
     */
    fun updateReviewMessage(message: String) {
        _review.update { it.setMessage(message) }
    }

    /**
     * Updates the star rating of the review.
     *
     * @param stars The new star rating for the review.
     */
    fun onUpdateStars(stars: Int) {
        _review.update { it.setStars(stars) }
    }

    /**
     * Censors the message content of a review by replacing it with the specified censored message.
     * This operation updates the review's message in the backend and locally within the ViewModel.
     *
     * @param censoredMessage The censored message to replace the original review message.
     * @param reviewId        The ID of the review to censor.
     * @param offerId         The ID of the offer to which the review belongs.
     */
    fun censorReviewMessage(censoredMessage: String, reviewId: String, offerId: String) {
        launchCatching {
            reviewService.updateReviewMessage(offerId, reviewId, censoredMessage)
            _review.update { it.copy(message = censoredMessage) }
            _review.value = Review(ownerRef = authenticationService.currentAuthUserUid ?: "")
        }
    }
}
