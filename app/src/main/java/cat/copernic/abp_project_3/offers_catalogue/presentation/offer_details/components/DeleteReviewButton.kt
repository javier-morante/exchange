package cat.copernic.abp_project_3.offers_catalogue.presentation.offer_details.components

import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import cat.copernic.abp_project_3.R
import cat.copernic.abp_project_3.application.data.model.Review
import cat.copernic.abp_project_3.offers_catalogue.domain.offer_details.CreateReviewViewModel

/**
 * Composable function representing a button used to delete a review.
 *
 * This function displays an icon button that, when clicked, triggers the deletion of a review.
 *
 * @param createReviewViewModel The view model providing data and logic for creating reviews.
 * @param offerId The unique identifier of the offer associated with the review to be deleted.
 * @param review The review object to be deleted.
 */
@Composable
fun DeleteReviewButton(
    createReviewViewModel: CreateReviewViewModel,
    offerId: String,
    review: Review
) {
    IconButton(
        onClick = { createReviewViewModel.deleteReview(offerId, review.id) }
    ) {
        Icon(
            painter = painterResource(R.drawable.trash),
            contentDescription = null,
            modifier = Modifier.size(30.dp)
        )
    }
}