package cat.copernic.abp_project_3.offers_catalogue.presentation.catalogue.components

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

/**
 * Composable function to display a rating bar with clickable rating icons.
 *
 * @param modifier Optional modifier for styling and layout customization.
 * @param maxStars The maximum number of rating icons to display.
 * @param onRatingChanged Callback invoked when a rating icon is clicked.
 * @param isIndicator Flag to indicate whether the rating bar is read-only (indicator mode).
 * @param ratingIcon The @Composable function used to render each rating icon based on its index.
 */
@Composable
fun RatingBar(
    modifier: Modifier = Modifier,
    maxStars: Int = 5,
    onRatingChanged: (Int) -> Unit,
    isIndicator: Boolean = false,
    ratingIcon: @Composable (Int) -> Unit
) {
    Row(
        modifier = modifier,
        verticalAlignment = Alignment.CenterVertically
    ) {
        repeat(maxStars) { index ->
            IconButton(
                onClick = { if (!isIndicator) onRatingChanged(index + 1) },
                modifier = Modifier.padding(end = 5.dp)
                    .size(40.dp)
            ) {
                ratingIcon(index)
            }
        }
    }
}
