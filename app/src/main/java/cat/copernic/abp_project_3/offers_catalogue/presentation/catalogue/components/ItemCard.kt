package cat.copernic.abp_project_3.offers_catalogue.presentation.catalogue.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import cat.copernic.abp_project_3.application.presentation.ui.theme.Abp_project_3Theme
import coil.compose.AsyncImage

/**
 * Displays a card component representing an item with an image, title, and subtitle.
 *
 * @param title The title text to display on the card.
 * @param subTitle The subtitle text to display on the card.
 * @param imagePath The image path or URL for the card's image.
 * @param handleOnClick Callback triggered when the card is clicked.
 */
@Composable
fun ItemCard(
    title:String = "title",
    subTitle: String = "subtitle",
    imagePath:String = "",
    averageStarsFormatted: String = "0.0",
    handleOnClick: () -> Unit = {}
){
    Card(
        elevation = CardDefaults.cardElevation(10.dp),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surfaceVariant,
        ),
        modifier = Modifier
            .fillMaxWidth()
            .padding(4.dp)
            .height(150.dp)
            .clickable { handleOnClick() }
    ) {
        AsyncImage(
            model = imagePath,
            contentDescription = "",
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .fillMaxWidth()
                .height(95.dp)
        )

        TextCard(
            title,
            subTitle,
            averageStarsFormatted = averageStarsFormatted,
            modifier = Modifier
                .padding(start = 5.dp)
                .padding(all = 1.dp))
    }
}

/**
 * Composable function to display a title and subtitle as Text components.
 *
 * @param title The title text to display.
 * @param subTitle The subtitle text to display.
 * @param modifier Optional modifier for styling and layout customization.
 */
@Composable
fun TextCard(
    title: String,
    subTitle: String,
    averageStarsFormatted: String,
    modifier: Modifier = Modifier
) {
    Column(modifier = modifier) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            Text(
                maxLines = 1,
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold,
                text = title,
                overflow = TextOverflow.Ellipsis,
                modifier = Modifier.weight(1f),
                textAlign = TextAlign.Start,
            )
            Text(
                fontSize = 14.sp,
                text = averageStarsFormatted,
                textAlign = TextAlign.End,
                modifier = Modifier.padding(end = 4.dp)
            )
        }
        Text(
            maxLines = 1,
            text = subTitle,
            modifier = Modifier.padding(top = 4.dp),
            overflow = TextOverflow.Ellipsis,
            textAlign = TextAlign.Center,
        )
    }
}


@Preview(showBackground = true)
@Composable
fun ItemCardPreview(){

    Abp_project_3Theme {
        LazyVerticalGrid(
            columns = GridCells.Fixed(2),
            modifier = Modifier.fillMaxSize(),
            contentPadding = PaddingValues(10.dp)
        ) {
            items(5){
                ItemCard()
            }
        }
    }
}
