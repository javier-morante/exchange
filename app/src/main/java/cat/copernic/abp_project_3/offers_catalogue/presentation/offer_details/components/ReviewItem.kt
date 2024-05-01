package cat.copernic.abp_project_3.offers_catalogue.presentation.offer_details.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import cat.copernic.abp_project_3.R
import cat.copernic.abp_project_3.application.data.model.Profile
import cat.copernic.abp_project_3.application.data.model.Review
import cat.copernic.abp_project_3.offers_catalogue.domain.offer_details.CreateReviewViewModel
import java.text.SimpleDateFormat
import java.util.Locale

/**
 * Composable function representing an individual review item displayed within a list of reviews.
 *
 * This function displays information about a specific review, including the rating, reviewer's name,
 * review message, and review date. It also provides options for administrators to delete or censor
 * the review.
 *
 * @param review The review data object to display.
 * @param profile The profile of the user who submitted the review.
 * @param isAdmin A boolean indicating whether the current user is an administrator.
 * @param createReviewViewModel The view model providing data and logic for creating reviews.
 * @param offerId The unique identifier of the offer associated with the review.
 */
@Composable
fun ReviewItem(
    review: Review,
    profile: Profile,
    isAdmin: Boolean,
    createReviewViewModel: CreateReviewViewModel,
    offerId: String
) {

    val filledStar = painterResource(R.drawable.filled_star)
    val emptyStar = painterResource(R.drawable.empty_star)

    val dateFormat = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())
    val formattedReviewDate = dateFormat.format(review.date.toDate())

    Column(
        modifier = Modifier.padding(vertical = 8.dp)
    ) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            repeat(5) { index ->
                val starPainter = if (index < review.stars) filledStar else emptyStar

                Image(
                    painter = starPainter,
                    contentDescription = null,
                    modifier = Modifier.size(14.dp)
                )
                Spacer(modifier = Modifier.width(4.dp))
            }
            Spacer(modifier = Modifier.width(8.dp))
            Text(
                text = "${profile.name} ${profile.surname}",
                style = TextStyle(fontWeight = FontWeight.Bold, fontSize = 20.sp)
            )
            Spacer(modifier = Modifier.weight(1f))
            if (isAdmin) {
                DeleteReviewButton(
                    createReviewViewModel = createReviewViewModel,
                    offerId = offerId,
                    review = review
                )
            }
        }
        Row(
            verticalAlignment = Alignment.CenterVertically
        ) {
            if (review.message != "") {
                Text(
                    text = review.message,
                    style = TextStyle(color = Color.Gray),
                    modifier = Modifier.weight(1f)
                )
                if (isAdmin) {
                    CensorCommentButton(createReviewViewModel, review, "Censored", offerId)
                }
            }
        }
        Text(text = formattedReviewDate)
    }
}