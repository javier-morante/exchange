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
 * Composable function representing a button used to censor a review comment.
 *
 * This function displays an icon button that, when clicked, triggers the censoring of a review's message.
 *
 * @param createReviewViewModel The view model providing data and logic for creating reviews.
 * @param review The review object whose comment will be censored.
 * @param censoredMessage The message to replace the review's original message after censoring.
 * @param offerId The unique identifier of the offer associated with the review.
 */
@Composable
fun CensorCommentButton(
    createReviewViewModel: CreateReviewViewModel,
    review: Review,
    censoredMessage: String,
    offerId: String
) {

    IconButton(
        onClick = {
            createReviewViewModel.censorReviewMessage(censoredMessage, review.id, offerId)
        }
    ) {
        Icon(
            painter = painterResource(R.drawable.forbidden),
            contentDescription = null,
            modifier = Modifier.size(20.dp)
        )
    }
}